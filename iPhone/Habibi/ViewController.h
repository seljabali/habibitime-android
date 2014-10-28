//
//  ViewController.h
//  Habibi
//
//  Created by B2BConnect on 18/07/14.
//
//

#import <UIKit/UIKit.h>

@class MessageListViewController;
@class SettingsViewController;
@interface ViewController : UIViewController
{
    
}
@property (strong, nonatomic) IBOutlet UITableView *tblCategoryList;
@property(nonatomic,strong)NSMutableArray *arrCategoryList;
@property(nonatomic,strong)NSMutableArray *arrColor;
@property(nonatomic,strong)UIColor *currentColor;
@property(nonatomic,strong)UIColor *PurpleColor;
@property(nonatomic,strong)UIColor *greenColor;
@property(nonatomic,strong)UIColor *lightblueColor;
@property(nonatomic,strong)UIColor *darkBlueColor;
@property(nonatomic,strong)UIColor *darkGrayColor;


@end
