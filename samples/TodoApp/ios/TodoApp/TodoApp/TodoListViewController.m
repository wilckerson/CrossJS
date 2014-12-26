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
    [self.tableView reloadData];
}

-(void)loadTodoItemPresenter{
    [self performSegueWithIdentifier:@"TodoItemViewController" sender:self];
}

-(void)updateItemStatus: (int)index :(BOOL)isDone{
    
    ItemModel* item = [items objectAtIndex:index];
    item.isDone = isDone;
    
    items[index] = item;
    
    NSIndexPath* indexPath1 = [NSIndexPath indexPathForRow:index inSection:0];
    
    NSArray* indexArray = [NSArray arrayWithObjects:indexPath1, nil];
   
    [self.tableView reloadRowsAtIndexPaths:indexArray withRowAnimation:UITableViewRowAnimationFade];
    
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
    
    cell.lblDescription.text = desc;
    
    cell.lblDescription.alpha = (isDone) ? 0.3 : 1;
   
    
    if(isDone){
    NSMutableAttributedString *attributeString = [[NSMutableAttributedString alloc] initWithString:desc];    [attributeString addAttribute:NSStrikethroughStyleAttributeName
                            value:@2
                            range:NSMakeRange(0, [attributeString length])];
    
    cell.lblDescription.attributedText = attributeString ;
    }
    
    [cell.switchDone setOn: isDone];
    cell.switchDone.tag = indexPath.row;
    [cell.switchDone addTarget:self action:@selector(switchClicked:) forControlEvents:UIControlEventTouchUpInside];
    
    return cell;
}

-(void)switchClicked:(UISwitch*)sender
{
    int index = (int)sender.tag;
    NSString* jsCode = [NSString stringWithFormat:@"todoListPresenter.fromNative_onChangeItemStatus(%d);",index];
    
    [[CrossJS getInstance] executeJS:jsCode];

}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    int index =(int)indexPath.row;
    
    NSString* jsCode = [NSString stringWithFormat:@"todoListPresenter.fromNative_onEditItem(%d);",index];
    
    [[CrossJS getInstance] executeJS:jsCode];

   
}

@end
