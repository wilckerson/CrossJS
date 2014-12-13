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
    
    //NSTimeInterval start = [NSDate timeIntervalSinceReferenceDate];
    
    //JavaScript 22.033288
    //[[CrossJS getInstance] executeJS:@"(function(){var e=4,t=4,n=3,r=true;var i=1e9;for(var s=0;s<i;s++){e+=r==true?-(t/n):t/n;r=!r;n+=2}return e})()"];
    
    //Native 9.971699
//    int e = 4,
//    t = 4,
//    n = 3;
//    bool r = true;
//    int i = 1e9;
//    for (int s = 0; s < i; s++) {
//        e += r == true ? -(t / n) : t / n;
//        r = !r;
//        n += 2;
//    }
    
    //Native with cast 66.123213
//    NSMutableDictionary* globalVar = [[NSMutableDictionary alloc] init];
//    [globalVar setValue: @"123" forKey: @"123"];
//    [globalVar setValue: [NSNumber numberWithFloat:4] forKey:@"pi"];
//    [globalVar setValue: [NSNumber numberWithFloat:4] forKey:@"top"];
//    [globalVar setValue: [NSNumber numberWithFloat:3] forKey:@"bot"];
//    [globalVar setValue: @(true) forKey:@"minus"];
//    [globalVar setValue: [NSNumber numberWithFloat:10000000] forKey:@"num"];
//    [globalVar setValue: [NSNumber numberWithFloat:0] forKey:@"i"];
//
//    for(int i=0;i< [(NSNumber*)[globalVar valueForKey:@"num"] floatValue] ;i++){
//        [globalVar setValue: [NSNumber numberWithFloat: [(NSNumber*)[globalVar valueForKey:@"num"]floatValue] + (((bool)[globalVar valueForKey:@"num"] == true)?-([(NSNumber*)[globalVar valueForKey:@"top"] floatValue]/[(NSNumber*)[globalVar valueForKey:@"bot"] floatValue]):([(NSNumber*)[globalVar valueForKey:@"top"] floatValue]/ [(NSNumber*)[globalVar valueForKey:@"boot"] floatValue]))] forKey:@"num"];
//        [globalVar setValue: @( !(bool)[globalVar valueForKey:@"minus"]) forKey:@"minus"];
//        [globalVar setValue: @([(NSNumber*)[globalVar valueForKey:@"bot"] floatValue] + 2) forKey:@"bot"];
//        
//    }
//
//
    
    //WebView 21.397966
    
//    UIWebView* webView = [[UIWebView alloc] initWithFrame:self.view.bounds];
//    [webView stringByEvaluatingJavaScriptFromString: @"(function(){var e=4,t=4,n=3,r=true;var i=1e9;for(var s=0;s<i;s++){e+=r==true?-(t/n):t/n;r=!r;n+=2}return e})()"];
    
   //NSTimeInterval duration = [NSDate timeIntervalSinceReferenceDate] - start;
   
 // NSLog(@"%f",duration);
    
    //Lendo e executando o arquivo javascript
    [[CrossJS getInstance] loadExecuteJSFile:@"SumController.js" ];
    //[[CrossJS getInstance] getContext_JavaScriptCore]

    //Conectando o javascript com a interface nativa
    [[CrossJS getInstance] setJSVariable: @"SumController.view" nativeValue: self];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(NSNumber*) getNum1{
   
    NSNumberFormatter * numParse = [[NSNumberFormatter alloc] init];
    [numParse setNumberStyle:NSNumberFormatterDecimalStyle];

    return [numParse numberFromString:self.textEditor1.text];
    
    //return [self.textEditor1.text floatValue];
}

-(NSNumber*) getNum2{
    
    NSNumberFormatter * numParse = [[NSNumberFormatter alloc] init];
    [numParse setNumberStyle:NSNumberFormatterDecimalStyle];
    
    return [numParse numberFromString:self.textEditor2.text];
    
    //return @([self.textEditor2.text floatValue]);
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
    
    [[CrossJS getInstance] executeJS:@"SumController.calculate();" ];
}
@end
