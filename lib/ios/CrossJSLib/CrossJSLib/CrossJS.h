//
//  CrossJSLib.h
//  CrossJSLib
//
//  Created by Wilckerson Ganda on 23/12/14.
//  Copyright (c) 2014 CrossJS. All rights reserved.
//

//#import <UIKit/UIKit.h>

//! Project version number for CrossJSLib.
FOUNDATION_EXPORT double CrossJSLibVersionNumber;

//! Project version string for CrossJSLib.
FOUNDATION_EXPORT const unsigned char CrossJSLibVersionString[];

// In this header, you should import all the public headers of your framework using statements like #import <CrossJSLib/PublicHeader.h>


#import <JavaScriptCore/JavaScriptCore.h>

@interface CrossJS : NSObject

+(CrossJS*)getInstance;
-(void) loadJSFile: (NSString*) fileName;
-(void) setJSVariable: (NSString*)variablePath nativeValue: (id)value;
-(JSContext*) getContext_JavaScriptCore;
-(JSValue*) executeJS: (NSString*) jsCode;

@end