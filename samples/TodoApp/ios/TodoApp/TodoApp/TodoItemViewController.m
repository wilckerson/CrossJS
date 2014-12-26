//
//  TodoItemViewController.m
//  TodoApp
//
//  Created by Wilckerson Ganda on 26/12/14.
//  Copyright (c) 2014 CrossJS. All rights reserved.
//

#import "TodoItemViewController.h"
#import <CrossJSLib/CrossJS.h>

@implementation TodoItemViewController

//NativeView Implementations

-(void)setTitleText:(NSString*) value{
    self.title = value;
}
-(void)setDeleteButtonVisibility: (BOOL)isVisible{
    [self.btnDelete setHidden:!isVisible];
}
-(void)setSaveButtonEnable: (BOOL)isEnable{
    [saveItemButton setEnabled:isEnable];
}
-(void)showConfirmDelete {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Are you sure?"
                                                    message:@"Do you want to delete this item?"
                                                   delegate:self
                                          cancelButtonTitle:@"No"
                                          otherButtonTitles:@"Yes", nil];
    [alert show];
}
-(NSString*)getDescriptionText{
    return [self.txtDescription text];
}

-(void)setDescriptionText: (NSString*)value{
    [self.txtDescription setText:value];
}

-(void)loadTodoListPresenter{
    [self.navigationController popViewControllerAnimated:YES];
    [[CrossJS getInstance] executeJS:@"todoListPresenter.fromNative_onLoad();"];
}

//ViewController Implementations

UIBarButtonItem* saveItemButton;

- (void)viewDidLoad {
    [super viewDidLoad];
    
     saveItemButton= [[UIBarButtonItem alloc] initWithTitle:@"Save" style:UIBarButtonItemStylePlain target:self action:@selector(onClickSaveItemButton)];
    
    self.navigationItem.rightBarButtonItem = saveItemButton;
    
    [[CrossJS getInstance] setJSVariable:@"todoItemNativeView" nativeValue: self];
    [[CrossJS getInstance] executeJS:@"todoItemPresenter.fromNative_onLoad();"];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    switch(buttonIndex) {
        case 0: //"No" pressed
            //do something?
            break;
        case 1: //"Yes" pressed
             [[CrossJS getInstance] executeJS:@"todoItemPresenter.fromNative_onConfirmDelete();"];
        break;
    }
}

-(void)onClickSaveItemButton{
     [[CrossJS getInstance] executeJS:@"todoItemPresenter.fromNative_onClickSave();"];
}

- (IBAction)onClickDelete:(id)sender {
    [[CrossJS getInstance] executeJS:@"todoItemPresenter.fromNative_onClickDeleteButton();"];
}

- (IBAction)onChangeDescription:(id)sender {
    
     [[CrossJS getInstance] executeJS:@"todoItemPresenter.fromNative_onChangeDescriptionText();"];
}
@end