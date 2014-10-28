//
//  MessageListViewController.h
//  Habibi
//
//  Created by B2BConnect on 18/07/14.
//
//

#import <UIKit/UIKit.h>
#import "PhraseDetailsViewController.h"

@interface MessageListViewController : UIViewController

@property(nonatomic,strong)NSNumber *catID;
@property(nonatomic,strong)NSString *catName;

@property(nonatomic,strong)NSMutableArray *arrPhraseList;
@property (strong, nonatomic) IBOutlet UITableView *tblMsgList;
@property(nonatomic,strong)NSMutableArray *arrColor;

@end
