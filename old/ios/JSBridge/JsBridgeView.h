//
//  JsBridgeView.h
//  JSBridge
//
//  Created by Wilckerson Ganda on 04/08/14.
//  Copyright (c) 2014 Wilckerson Ganda. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <JavaScriptCore/JSExport.h>

@protocol JsBridgeView <JSExport>

-(NSNumber*) getNum1;
-(NSNumber*) getNum2;
-(void) showMessage: (NSString*) msg;

@end
