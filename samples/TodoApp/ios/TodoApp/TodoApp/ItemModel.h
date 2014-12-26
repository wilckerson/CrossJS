//
//  Item.h
//  TodoApp
//
//  Created by Wilckerson Ganda on 26/12/14.
//  Copyright (c) 2014 CrossJS. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ItemModel : NSObject

@property NSString* itemDescription;
@property BOOL isDone;

+(NSMutableArray*) parseFromJson: (NSString*)json;

@end
