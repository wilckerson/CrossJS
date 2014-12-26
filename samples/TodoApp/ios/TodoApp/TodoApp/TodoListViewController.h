//
//  ViewController.h
//  TodoApp
//
//  Created by Wilckerson Ganda on 23/12/14.
//  Copyright (c) 2014 CrossJS. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <JavaScriptCore/JSExport.h>

@protocol TodoListNativeView<JSExport>

-(void)setEmptyListMsgVisibility:(BOOL) isVisible;
-(void)populateList: (NSString*)jsonItems;
-(void)loadTodoItemPresenter;
-(void)updateItemStatus: (int)index :(BOOL)isDone;
    
@end

@interface TodoListViewController : UIViewController <TodoListNativeView,UITableViewDelegate, UITableViewDataSource>
@property (weak, nonatomic) IBOutlet UIView *dss;
@property (strong, nonatomic) IBOutlet UITableView *tableView;

@property (weak, nonatomic) IBOutlet UIView *emptyMsg;
@end

