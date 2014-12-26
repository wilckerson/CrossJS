//
//  CrossJSLib.m
//  CrossJSLib
//
//  Created by Wilckerson Ganda on 23/12/14.
//  Copyright (c) 2014 CrossJS. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CrossJS.h"

@implementation CrossJS

//Singlethon Pattern
static CrossJS* singleInstance;

+(CrossJS*)getInstance
{
    if(singleInstance == nil){
        singleInstance = [[super allocWithZone:NULL] init];
    }
    
    return singleInstance;
}

JSContext* context;
NSString* lastExecutedCode;

- (id)init {
    if ( (self = [super init]) ) {
        // your custom initialization
        
        //Iniciando o contexto do JavaScriptCore
        context = [[JSContext alloc] init];
        
        //Interceptando os erros de JavaScript e exibindo no console
        [context setExceptionHandler:^(JSContext *context, JSValue *value) {
            NSLog(@"JS Exception: %@ %@",lastExecutedCode,value);
            //[NSException raise: nil format:@"JS Exception: %@",value];
            
        }];
        
        //Mapeando a funcao console.log para o console Nativo
        [self setJSVariable:@"console.log" nativeValue: ^(NSString *message) {
            NSLog(@"LogWrapper: %@", message);
        }];
    }
    return self;
}


-(NSString*)loadJSFileContent: (NSString*) fileName
{
    NSString *jsCode;
    
    //Separando o nome da extensao
    NSRange range = [fileName rangeOfString: @"." options:NSBackwardsSearch];
    if(range.length == 0){
        [NSException raise:@"Informe um arquivo válido" format:@"O arquivo %@ deve ter a extensão .js", fileName];
    }
    NSString* extension = [fileName substringFromIndex: range.location];
    NSString* name = [fileName substringToIndex: range.location];
    
    //Verificando se a extensao é .js
    if(![extension  isEqual: @".js"]){
        [NSException raise: @"Nao é um arquivo JS" format:nil];
    }
    
    //Lendo o arquivo JS
    NSString *filePath = [[NSBundle mainBundle] pathForResource:name ofType:@"js"];
    if (filePath) {
        
        jsCode = [NSString stringWithContentsOfFile:filePath encoding:NSUTF8StringEncoding error:nil];
    }
    else
    {
        [NSException raise: @"Erro ao ler arquivo JS" format:@"Nao foi possivel ler o arquivo %@", fileName ];
    }
    
    return jsCode;
}

-(void) loadJSFile: (NSString*) fileName
{
    NSString* jsCode = [self loadJSFileContent: fileName];
    [context evaluateScript: jsCode withSourceURL:[NSURL URLWithString:fileName]];
}

-(void) setJSVariable: (NSString*)variablePath nativeValue: (id)value
{
    //Verificando se esta chamando variaveis encadeadas. Ex: obj.prop1.prop2
    NSArray* arrayVariables = [variablePath componentsSeparatedByString: @"."];
    
    if(arrayVariables.count <= 1){
        
        //Chamada simples
        context[variablePath] = value;
    }
    else
    {
        //Chamada encadeada
        int i = 1;
        JSValue* currentVariable;
        
        for (NSString* variable in arrayVariables) {
            
            currentVariable = context[variable];
            
            if( [currentVariable isUndefined])
            {
                context[variable] = [[NSObject alloc] init];
                currentVariable = context[variable] ;
            }
            
            //Se chegou na ultima variavel, executa
            if(i == arrayVariables.count-1)
            {
                NSString* finalVariable = [arrayVariables objectAtIndex: i];
                [currentVariable setValue:value forProperty: finalVariable];
                break;
            }
            
            i++;
        }
    }
    
    
}

-(JSValue*) executeJS: (NSString*) jsCode
{
    //@try {
    lastExecutedCode = jsCode;
    JSValue* result = [context evaluateScript:jsCode];
    return result;
    
    //}
    //@catch (NSException * e) {
    //  NSLog(@"Exception: %@", e);
    //}
    
}


-(JSContext*) getContext_JavaScriptCore
{
    return context;
}

@end;