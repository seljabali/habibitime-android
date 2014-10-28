//
//  MessageListViewController.m
//  Habibi
//
//  Created by B2BConnect on 18/07/14.
//
//

#import "MessageListViewController.h"

@interface MessageListViewController ()

@end

@implementation MessageListViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    
    self.view.backgroundColor = [UIColor colorWithRed:245.0/255.0 green:245.0/255.0 blue:245.0/255.0 alpha:1.0];

    
    [super viewDidLoad];
    self.arrPhraseList=[NSMutableArray array];
    self.arrPhraseList=[self fetchPhrases:self.catID];
	// Do any additional setup after loading the view.
     [self.view addSubview:[APPDEL createCustomNavView:NO doneBtn:NO]];
    
    [self.tblMsgList setTableFooterView:[[UIView alloc]initWithFrame:CGRectZero]];
    
    self.arrColor=[NSMutableArray arrayWithObjects:
                   [UIColor colorWithRed:252.0f/255.0f green:116.0f/255.0f blue:95.0f/255.0f alpha:1],
                   [UIColor colorWithRed:252.0f/255.0f green:217.0f/255.0f blue:108.0f/255.0f alpha:1],
                   [UIColor colorWithRed:143.0f/255.0f green:194.0f/255.0f blue:204.0f/105.0f alpha:1],
                   [UIColor colorWithRed:30.0f/255.0f green:163.0f/255.0f blue:173.0f/255.0f alpha:1],
                   [UIColor colorWithRed:61.0f/255.0f green:72.0f/255.0f blue:81.0f/255.0f alpha:1],
                   nil];

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.arrPhraseList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *SimpleTableIdentifier = @"SimpleTableIdentifier";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:SimpleTableIdentifier];
    
   // if (cell == nil)
   // {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:SimpleTableIdentifier];
   // }
    cell.textLabel.text=[[self.arrPhraseList objectAtIndex:indexPath.row]objectForKey:@"native_phrase"];
    [cell.textLabel setTextAlignment:NSTextAlignmentCenter];
    [cell.textLabel setNumberOfLines:3];

    cell.backgroundColor = [UIColor colorWithRed:245.0/255.0 green:245.0/255.0 blue:245.0/255.0 alpha:1.0];
    
    UIView *backgroundView = [[UIView alloc] initWithFrame:CGRectMake(0,0,320,100)];
    [backgroundView setBackgroundColor:[self.arrColor objectAtIndex:indexPath.row%5]];
    cell.selectedBackgroundView = backgroundView;
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath;
{
    return 65;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    PhraseDetailsViewController *phraseDetailsVC;
    
    if (IS_IPHONE_5)
    {
        phraseDetailsVC=[[PhraseDetailsViewController alloc]initWithNibName:@"PhraseDetailsViewController-iPhone5" bundle:nil];
    }
    else
    {
        phraseDetailsVC=[[PhraseDetailsViewController alloc]initWithNibName:@"PhraseDetailsViewController-iPhone4" bundle:nil];
    }
    phraseDetailsVC.habibiPhrase_id=[[self.arrPhraseList objectAtIndex:indexPath.row]objectForKey:@"habibi_phrase_id"];
    phraseDetailsVC.strHabibiPhrase=[[self.arrPhraseList objectAtIndex:indexPath.row]objectForKey:@"native_phrase"];
    phraseDetailsVC.catName = self.catName;
    [self.navigationController pushViewController:phraseDetailsVC animated:YES];
    phraseDetailsVC=nil;
    
}

-(NSMutableArray *)fetchPhrases:(NSNumber *)catID
{
    FMDatabase *database=[FMDatabase databaseWithPath:[APPDEL dbPath]];
    [database open];
    NSMutableArray *arrPharseList=[[NSMutableArray alloc]init];
    FMResultSet *results = [database executeQuery:[NSString stringWithFormat:@"SELECT * FROM phrase where habibi_phrase_id in (select _id from habibi_phrase where category =%d) and language =2",catID.intValue]];
    
    while([results next])
    {
        int catID  = [results intForColumn:@"_id"];
        int habibi_phrase_id  = [results intForColumn:@"habibi_phrase_id"];
        int language  = [results intForColumn:@"language"];
        int dialect  = [results intForColumn:@"dialect"];
        int from_gender  = [results intForColumn:@"from_gender"];
        int to_gender  = [results intForColumn:@"to_gender"];
        NSString *native_phrase = [results stringForColumn:@"native_phrase"];
        NSString *phonetic_spelling = [results stringForColumn:@"phonetic_spelling"];
        NSString *proper_phonetic_spelling = [results stringForColumn:@"proper_phonetic_spelling"];

       

        NSDictionary *dict=[NSDictionary dictionaryWithObjectsAndKeys:
                            [NSNumber numberWithInt:catID],@"id",
                            [NSNumber numberWithInt:habibi_phrase_id],@"habibi_phrase_id",
                            [NSNumber numberWithInt:language],@"language",
                            [NSNumber numberWithInt:dialect],@"dialect",
                            [NSNumber numberWithInt:from_gender],@"from_gender",
                            [NSNumber numberWithInt:to_gender],@"to_gender",
                            native_phrase,@"native_phrase",
                            phonetic_spelling,@"phonetic_spelling",
                            proper_phonetic_spelling,@"proper_phonetic_spelling",nil];
        
        [arrPharseList addObject:dict];
        
    }
    [database close];
    return arrPharseList;
}

@end
