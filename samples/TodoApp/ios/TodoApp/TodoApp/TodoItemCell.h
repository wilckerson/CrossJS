//
//  TodoItemCell.h
//  TodoApp
//
//  Created by Wilckerson Ganda on 26/12/14.
//  Copyright (c) 2014 CrossJS. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>

@interface TodoItemCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UISwitch *switchDone;

@property (weak, nonatomic) IBOutlet UILabel *lblDescription;

@end
