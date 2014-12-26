//
//  ViewController.h
//  JSBridge
//
//  Created by Wilckerson Ganda on 04/08/14.
//  Copyright (c) 2014 ___FULLUSERNAME___. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JsBridgeView.h"

@interface ViewController : UIViewController<JsBridgeView>
@property (weak, nonatomic) IBOutlet UITextField *textEditor1;
@property (weak, nonatomic) IBOutlet UITextField *textEditor2;
- (IBAction)btnOnClick:(id)sender;


@end
