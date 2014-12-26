
#import "ItemModel.h"

@implementation ItemModel

//@synthesize description;
//@synthesize isDone;

+(NSMutableArray*) parseFromJson: (NSString*)json{
    
   NSMutableArray* items = [[NSMutableArray alloc] init];
    
    
    NSData* data = [json dataUsingEncoding:NSUTF8StringEncoding];
    NSError *localError = nil;
    NSDictionary *parsedObject = [NSJSONSerialization JSONObjectWithData:data options:0 error:&localError];
    
    if(parsedObject != nil){
        
        
        NSArray *results = [parsedObject valueForKey:@"data"];
        for(int i = 0; i < results.count; i++){
            
            ItemModel* item = [ItemModel alloc];
            NSDictionary* jsonItem = results[i];
            
            item.itemDescription = [jsonItem valueForKey:@"description"];
            item.isDone = [[jsonItem valueForKey:@"done"] boolValue];
            
            [items addObject:item];
        }
    }
    
    return items;
}

@end