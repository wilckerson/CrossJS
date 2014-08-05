//
//  ViewController.m
//  JSBridge
//
//  Created by Wilckerson Ganda on 04/08/14.
//  Copyright (c) 2014 ___FULLUSERNAME___. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

NSString* jsCode; /* =
@"var Controller = {"
    "view: {"
        "getNum1: function(){},"
        "getNum2: function(){},"
        "showMessage: function(msg){}"
    "},"
    "onClickSoma: function(){"
        "var n1 = this.view.getNum1();"
        "var n2 = this.view.getNum2();"
        "if(!n1 || !n2){"
            "this.view.showMessage('Informe os valores');"
            "return;"
        "}"
        "var result = n1 * n2;"
        "this.view.showMessage(result);"
    "},"
"};";
                   */

JSContext* context;


- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    //Lendo o arquivo JS
    NSString *filePath = [[NSBundle mainBundle] pathForResource:@"Controller" ofType:@"js"];
    if (filePath) {
        jsCode = [NSString stringWithContentsOfFile:filePath encoding:NSUTF8StringEncoding error:nil];
        NSLog(@"%@",jsCode);
    }
    
    context = [[JSContext alloc] init];
    [context evaluateScript:jsCode];
    [context[@"Controller"] setValue: self forProperty: @"view"];
    //context[@"Controller.view"] = self;
    
    [context setExceptionHandler:^(JSContext *context, JSValue *value) {
        NSLog(@"%@", value);
    }];
    
    //JSValue* value = [context evaluateScript:@"Controller.test();"];
    //JSValue* value = [[context[@"Controller"] valueForProperty: @"view" ]valueForProperty:@"getNum1"] ;//[[[context[@"Controller"] valueForProperty: @"view" ] valueForProperty:@"getNum1"] callWithArguments:@[]];
    //NSLog(@"%@",value);
    
    /*
     JSContext* context = [[JSContext alloc] init];
    [context evaluateScript : @"var g1 = '12';"];
    NSLog(@"%@",context[@"g1"]);
    
    [context evaluateScript:@"function alterarValor(valor){ g1 = valor;}"];
    
    JSValue* func = context[@"alterarValor"];
    [func callWithArguments:@[@"23"]];
    
    NSLog(@"%@",context[@"g1"]);
    
    context[@"JsBridgeView"] = self;
    [context evaluateScript: @"g1 = JsBridgeView.getText();"];
    NSLog(@"%@",context[@"g1"]);
     */
    
    

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(float) getNum1{
    return [self.textEditor1.text floatValue];
}

-(float) getNum2{
    return [self.textEditor2.text floatValue];
}

-(void) showMessage: (NSString*) msg{
    
    UIAlertView* alert = [[UIAlertView alloc] initWithTitle: @"Resultado"
                                                    message:msg
                                                   delegate:nil
                                          cancelButtonTitle: @"OK"
                                          otherButtonTitles:nil];
    [alert show];
}

- (IBAction)btnOnClick:(id)sender {
    
    [context evaluateScript:@"Controller.onClickSoma();"];
    
    /*
     float num1 = [self.textEditor1.text floatValue];
    float num2 = [self.textEditor2.text floatValue];
    
    float result = num1 + num2;
    NSString* msg = [NSString stringWithFormat:@"%f",result];
    
    UIAlertView* alert = [[UIAlertView alloc] initWithTitle: @"Resultado"
                                              message:msg
                                              delegate:nil
                                              cancelButtonTitle: @"OK"
                                              otherButtonTitles:nil];
    [alert show];
     */
    
}
@end
