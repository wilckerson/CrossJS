//
//  TodoItemViewController.h
//  TodoApp
//
//  Created by Wilckerson Ganda on 26/12/14.
//  Copyright (c) 2014 CrossJS. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <JavaScriptCore/JSExport.h>

@protocol TodoItemNativeView<JSExport>

-(void)setTitleText:(NSString*) value;
-(void)setDeleteButtonVisibility: (BOOL)isVisible;
-(void)setSaveButtonEnable: (BOOL)isEnable;
-(void)showConfirmDelete ;
-(NSString*)getDescriptionText;
-(void)setDescriptionText: (NSString*)value;
-(void)loadTodoListPresenter;

@end

@interface TodoItemViewController :UIViewController<TodoItemNativeView,UIAlertViewDelegate>
- (IBAction)onClickDelete:(id)sender;
@property (weak, nonatomic) IBOutlet UITextField *txtDescription;
@property (weak, nonatomic) IBOutlet UIButton *btnDelete;
- (IBAction)onChangeDescription:(id)sender;

@end
