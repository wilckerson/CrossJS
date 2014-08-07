//
//  CrossJS.m
//  JSBridge
//
//  Created by Wilckerson Ganda on 06/08/14.
//  Copyright (c) 2014 Wilckerson Ganda. All rights reserved.
//

#import "CrossJS.h"

@implementation CrossJS

//Singlethon Pattern
static CrossJS* singleInstance;

+(CrossJS*)Instance
{
    if(singleInstance == nil){
        singleInstance = [[super allocWithZone:NULL] init];
    }
    
    return singleInstance;
}

JSContext* context;

- (id)init {
    if ( (self = [super init]) ) {
        // your custom initialization
        
        //Iniciando o contexto do JavaScriptCore
        context = [[JSContext alloc] init];
        
        //Interceptando os erros de JavaScript e exibindo no console
        [context setExceptionHandler:^(JSContext *context, JSValue *value) {
            //NSLog(@"JS Exception: %@", value);
            [NSException raise: nil format:@"JS Exception: %@",value];

        }];
        
        //Mapeando a funcao console.log para o console Nativo
        [self setJSVariable:@"console.log" nativeValue: ^(NSString *message) {
            NSLog(@"JS LOG: %@", message);
        }];
//        [context evaluateScript:@"var console = {};"]; // make console an empty object
//        context[@"console"][@"log"] = ^(NSString *message) {
//            NSLog(@"%@", message);
//        };
    }
    return self;
}

-(JSContext*) getContext_JavaScriptCore
{
    return context;
}

-(NSString*)loadJSFile: (NSString*) fileName
{
    NSString *jsCode;
    
    //Separando o nome da extensao
    NSRange range = [fileName rangeOfString: @"." options:NSBackwardsSearch];
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

-(void) loadExecuteJSFile: (NSString*) fileName
{
    NSString* jsCode = [self loadJSFile: fileName];
    [context evaluateScript: jsCode];
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
    JSValue* result = [context evaluateScript:jsCode];
    return result;
}

@end;
