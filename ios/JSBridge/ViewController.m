//
//  ViewController.m
//  JSBridge
//
//  Created by Wilckerson Ganda on 04/08/14.
//  Copyright (c) 2014 ___FULLUSERNAME___. All rights reserved.
//

#import "ViewController.h"
#import "CrossJS.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    
    //Lendo e executando o arquivo javascript
    [[CrossJS Instance] loadExecuteJSFile:@"Controller.js" ];
    

    //Conectando o javascript com a interface nativa
    [[CrossJS Instance] setJSVariable: @"Controller.view" nativeValue: self];
    
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
    
    [[CrossJS Instance] executeJS:@"Controller.onClickSoma();" ];
}
@end
