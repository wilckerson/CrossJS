//
//  CrossJS.h
//  JSBridge
//
//  Created by Wilckerson Ganda on 06/08/14.
//  Copyright (c) 2014 Wilckerson Ganda. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <JavaScriptCore/JavaScriptCore.h>

@interface CrossJS : NSObject

+(CrossJS*)getInstance;
-(NSString*)loadJSFile: (NSString*) fileName;
-(void) loadExecuteJSFile: (NSString*) fileName;
-(void) setJSVariable: (NSString*)variablePath nativeValue: (id)value;
-(JSContext*) getContext_JavaScriptCore;
-(JSValue*) executeJS: (NSString*) jsCode;

@end
