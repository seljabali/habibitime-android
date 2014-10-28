//
//  ViewController.m
//  Habibi
//
//  Created by B2BConnect on 18/07/14.
//
//

#import "ViewController.h"
#import "MessageListViewController.h"
#import "SettingsViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.arrCategoryList=[NSMutableArray array];
    self.arrCategoryList=[self fetchCategories];
    self.arrColor=[NSMutableArray arrayWithObjects:
                   [UIColor colorWithRed:252.0f/255.0f green:116.0f/255.0f blue:95.0f/255.0f alpha:1],
                   [UIColor colorWithRed:252.0f/255.0f green:217.0f/255.0f blue:108.0f/255.0f alpha:1],
                   [UIColor colorWithRed:143.0f/255.0f green:194.0f/255.0f blue:204.0f/105.0f alpha:1],
                   [UIColor colorWithRed:30.0f/255.0f green:163.0f/255.0f blue:173.0f/255.0f alpha:1],
                   [UIColor colorWithRed:61.0f/255.0f green:72.0f/255.0f blue:81.0f/255.0f alpha:1],
                   nil];
    
    self.PurpleColor=[UIColor colorWithRed:252.0f/255.0f green:116.0f/255.0f blue:95.0f/255.0f alpha:1];
    self.greenColor=[UIColor colorWithRed:252.0f/255.0f green:217.0f/255.0f blue:108.0f/255.0f alpha:1];
    self.lightblueColor=[UIColor colorWithRed:143.0f/255.0f green:194.0f/255.0f blue:204.0f/105.0f alpha:1];
    self.darkBlueColor=[UIColor colorWithRed:30.0f/255.0f green:163.0f/255.0f blue:173.0f/255.0f alpha:1];
    
    self.darkGrayColor=[UIColor colorWithRed:61.0f/255.0f green:72.0f/255.0f blue:81.0f/255.0f alpha:1];

    self.currentColor = self.PurpleColor;
    
    [self.navigationController.navigationBar setHidden:YES];
    
    
    
    [self.view addSubview:[APPDEL createCustomNavView:YES doneBtn:NO]];
    

    [self.tblCategoryList setTableFooterView:[[UIView alloc]initWithFrame:CGRectZero]];
    
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.arrCategoryList count]+1;
    //return 20;

}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    UITableViewCell *cell;// = [tableView dequeueReusableCellWithIdentifier:SimpleTableIdentifier];
    
   // if (cell == nil)
    //{
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
   // }
    if (indexPath.row==[self.arrCategoryList count])
    {
        cell.textLabel.text=@"SETTINGS";
    }
    else
    {
    cell.textLabel.text=[[[self.arrCategoryList objectAtIndex:indexPath.row]objectForKey:@"category_name"] uppercaseString];
    }
        //cell.textLabel.text=@"cell";
    [cell.textLabel setTextAlignment:NSTextAlignmentCenter];
    cell.backgroundColor=[UIColor clearColor];
    NSLog(@"cell:-- %@",[indexPath description]);
    
    
    [cell.contentView setBackgroundColor:[self.arrColor objectAtIndex:indexPath.row%5]];

    cell.selectionStyle = UITableViewCellSelectionStyleNone;
        
       return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath;
{
    return 101;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    if (indexPath.row==[self.arrCategoryList count])
    {
        SettingsViewController *settingsVC;
        if (IS_IPHONE_5)
        {
            settingsVC=[[SettingsViewController alloc]initWithNibName:@"SettingsViewController-iPhone5" bundle:nil];
        }
        else
        {
            settingsVC=[[SettingsViewController alloc]initWithNibName:@"SettingsViewController-iPhone4" bundle:nil];
        }

        [self.navigationController pushViewController:settingsVC animated:YES];
    }
    else
    {
        MessageListViewController *msgVC;
        
        if (IS_IPHONE_5)
        {
            msgVC=[[MessageListViewController alloc]initWithNibName:@"MessageListViewController-iPhone5" bundle:nil];
        }
        else
        {
            msgVC=[[MessageListViewController alloc]initWithNibName:@"MessageListViewController-iPhone4" bundle:nil];
        }
        
        msgVC.catID=[[self.arrCategoryList objectAtIndex:indexPath.row]objectForKey:@"id"];
        msgVC.catName=[[self.arrCategoryList objectAtIndex:indexPath.row]objectForKey:@"category_name"];

        [self.navigationController pushViewController:msgVC animated:YES];
    }
    
}

-(NSMutableArray *)fetchCategories
{
    FMDatabase *database=[FMDatabase databaseWithPath:[APPDEL dbPath]];
    [database open];
    NSMutableArray *arrCatList=[[NSMutableArray alloc]init];
    FMResultSet *results = [database executeQuery:SELECT_QRY];
    while([results next]) {
        NSString *catName = [results stringForColumn:@"category_name"];
        NSInteger catID  = [results intForColumn:@"_id"];
        NSDictionary *dict=[NSDictionary dictionaryWithObjectsAndKeys:[NSNumber numberWithInt:catID],@"id",catName,@"category_name",nil];
        [arrCatList addObject:dict];
        
    }
    [database close];
    return arrCatList;
}


@end
