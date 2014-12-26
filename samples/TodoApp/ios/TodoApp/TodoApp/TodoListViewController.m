//
//  ViewController.m
//  TodoApp
//
//  Created by Wilckerson Ganda on 23/12/14.
//  Copyright (c) 2014 CrossJS. All rights reserved.
//

#import "ItemModel.h"
#import "TodoItemCell.h"
#import "TodoListViewController.h"
#import <CrossJSLib/CrossJS.h>

@implementation TodoListViewController


//NativeView Implementations


-(void)setEmptyListMsgVisibility:(BOOL) isVisible{
    [self.emptyMsg setHidden:!isVisible];
    
}

-(void)populateList: (NSString*)jsonItems{
    
    items = [ItemModel parseFromJson:jsonItems];
}

-(void)loadTodoItemPresenter{
    [self performSegueWithIdentifier:@"TodoItemViewController" sender:self];
}

-(void)updateItemStatus: (int)index :(BOOL)isDone{
    
}

//ViewController Implementations

NSMutableArray* items;

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
   
    UIBarButtonItem *addItemButton = [[UIBarButtonItem alloc] initWithTitle:@"New" style:UIBarButtonItemStylePlain target:self action:@selector(onClickAddItemButton)];
    
    self.navigationItem.rightBarButtonItem = addItemButton;
    
    items = [[NSMutableArray alloc] init];
    
    [[CrossJS getInstance] setJSVariable:@"todoListNativeView" nativeValue: self];
    [[CrossJS getInstance] executeJS:@"todoListPresenter.fromNative_onLoad();"];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)onClickAddItemButton{
    [[CrossJS getInstance] executeJS:@"todoListPresenter.fromNative_onClickAdd();"];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [items count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSString *simpleTableIdentifier = @"TodoItemCell";
    
    TodoItemCell *cell = [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
    
//    if (cell == nil) {
//        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:simpleTableIdentifier];
//    }
    
    ItemModel* item= [items objectAtIndex:indexPath.row];
    NSString* desc =item.itemDescription;
    BOOL isDone =item.isDone;
    [cell.lblDescription setText: desc];
    [cell.switchDone setOn: isDone];
    return cell;
}

@end
