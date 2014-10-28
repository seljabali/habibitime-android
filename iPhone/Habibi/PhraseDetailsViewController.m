//
//  PhraseDetailsViewController.m
//  Habibi
//
//  Created by B2BConnect on 19/07/14.
//
//

#import "PhraseDetailsViewController.h"

@interface PhraseDetailsViewController ()

@end

@implementation PhraseDetailsViewController

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
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    [self.view addSubview:[APPDEL createCustomNavView:NO doneBtn:NO]];

   

}

-(void)viewWillAppear:(BOOL)animated
{
    
    
    if([self.catName isEqualToString:@"Mood"] || [self.catName isEqualToString:@"Responses"])
    {
        self.btnFemaleSound.hidden = YES;
        self.btnMaleSound.hidden = YES;
        toGenderLabel.hidden = YES;
    }
    
    
    
    
   // NSLog(@"%s",__FUNCTION__);
    int toGender;
    
    if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"habibi_Gender"] isEqualToString:@"Male"])
    {
        toGender=1;
        [self PlaySoundToMale:self.btnMaleSound];
    }
    else
    {
        toGender=2;
        [self playSoundToFemale:self.btnFemaleSound];
    }
    
    return;

    NSMutableArray *arr= [self fetchConvertedPhraseData:self.habibiPhrase_id];
    if (arr.count>0)
    {
        lblPhrase1.text=self.strHabibiPhrase;
        lblPhraseTranslation1.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"native_phrase"]];
        lblPhraseTranslation2.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"phonetic_spelling"]];
        lblPhraseTranslation3.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"proper_phonetic_spelling"]];
    }
    else
    {
       NSMutableArray *arr= [self fetchPharseData:self.habibiPhrase_id toGender:toGender fromGender:toGender];
        if (arr.count>0)
        {
            lblPhrase1.text=self.strHabibiPhrase;
            lblPhraseTranslation1.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"native_phrase"]];
            lblPhraseTranslation2.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"phonetic_spelling"]];
            lblPhraseTranslation3.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"proper_phonetic_spelling"]];
        }

    }
    
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(NSMutableArray *)fetchConvertedPhraseData:(NSNumber *)phraseID
{
    int fromGender;
    int toGender;
    if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"habibi_Gender"] isEqualToString:@"Male"])
    {
        toGender=1;
    }
    else
    {
        toGender=2;
    }
    
    if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"my_Gender"] isEqualToString:@"Male"])
    {
        fromGender=1;
    }
    else
    {
        fromGender=2;
    }
    
    
            FMDatabase *database=[FMDatabase databaseWithPath:[APPDEL dbPath]];
        [database open];
        NSMutableArray *arrPhraseDetailsList=[[NSMutableArray alloc]init];
        FMResultSet *results = [database executeQuery:[NSString stringWithFormat:@"SELECT * FROM phrase where habibi_phrase_id  = %d and from_gender=%d and to_gender=%d",phraseID.intValue,fromGender,toGender]];
    NSLog(@"Qry :-- %@",[NSString stringWithFormat:@"SELECT * FROM phrase where habibi_phrase_id  = %d and from_gender=%d and to_gender=%d",phraseID.intValue,fromGender,toGender]);
        while([results next]) {
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
            
            [arrPhraseDetailsList addObject:dict];
            
        }
        [database close];
   
    return arrPhraseDetailsList;
}
- (IBAction)copyBtnOnAction:(id)sender
{
    UIButton *btn=sender;
    UIPasteboard *pb = [UIPasteboard generalPasteboard];
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Message" message:nil delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];

    switch (btn.tag) {
        case 1:
        {
            [pb setString:lblPhraseTranslation1.text];
            alert.message=[NSString stringWithFormat:@"Text has been copied to your clipboard"];
            break;
        }
        case 2:
        {
            [pb setString:lblPhraseTranslation2.text];
            alert.message=[NSString stringWithFormat:@"%@ has been copied to your clipboard",[pb string]];

            break;
        }
        case 3:
        {
            [pb setString:lblPhraseTranslation3.text];
            alert.message=[NSString stringWithFormat:@"%@ has been copied to your clipboard",[pb string]];

            break;
        }
    }
    
    [alert show];
}
/*
- (IBAction)playSoundOnClick:(id)sender
{
    NSURL *fileURL;
    NSCharacterSet *notAllowedChars = [[NSCharacterSet characterSetWithCharactersInString:@"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ "] invertedSet];
    NSString *resultString = [[self.strHabibiPhrase componentsSeparatedByCharactersInSet:notAllowedChars] componentsJoinedByString:@""];
    resultString=[resultString stringByReplacingOccurrencesOfString:@" " withString:@"_"];
    
    
    NSString *strGender;
    if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"habibi_Gender"] isEqualToString:@"Male"] && [[[NSUserDefaults standardUserDefaults]objectForKey:@"my_Gender"] isEqualToString:@"Male"])
    {
        strGender=@"_m_m";
    }
    else if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"habibi_Gender"] isEqualToString:@"Female"] && [[[NSUserDefaults standardUserDefaults]objectForKey:@"my_Gender"] isEqualToString:@"Female"])
    {
        strGender=@"_f_f";
    }
    
    else if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"habibi_Gender"] isEqualToString:@"Male"] && [[[NSUserDefaults standardUserDefaults]objectForKey:@"my_Gender"] isEqualToString:@"Female"])
    {
        strGender=@"_f_m";
        
    }
    else if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"habibi_Gender"] isEqualToString:@"Female"] && [[[NSUserDefaults standardUserDefaults]objectForKey:@"my_Gender"] isEqualToString:@"Male"])
    {
        strGender=@"_m_f";
        
    }
    
    resultString= [resultString stringByAppendingString:strGender];
    NSString *resultStr=[resultString lowercaseString];
    NSLog (@"Result: %@", resultStr);
    
    NSString* filePath = [[NSBundle mainBundle] pathForResource:resultString
                                                         ofType:@"m4a"];
    
    
    NSFileManager *fileManager = [NSFileManager defaultManager];
    
    
    if ([fileManager fileExistsAtPath:filePath])
    {
        NSLog(@"Exists");
    }
    else
    {
        NSString *str= [resultStr stringByReplacingCharactersInRange:NSMakeRange(resultStr.length-2,2) withString:[resultStr substringWithRange:NSMakeRange(resultStr.length-4,2)]];
        NSLog (@"Result: %@", str);
        
        filePath = [[NSBundle mainBundle] pathForResource:str ofType:@"m4a"];
        
        if ([fileManager fileExistsAtPath:filePath])
        {
            NSLog(@"Exists");
        }
        else
        {
            NSString *str= [resultStr stringByReplacingCharactersInRange:NSMakeRange(resultStr.length-2,2) withString:@""];
            NSLog (@"Result: %@", str);
            
            
            filePath = [[NSBundle mainBundle] pathForResource:[str lowercaseString] ofType:@"m4a"];
            
            if ([fileManager fileExistsAtPath:filePath])
            {
                NSLog(@"Exists");
            }
            else
            {
                NSString *str1= [str stringByReplacingCharactersInRange:NSMakeRange(str.length-2,2) withString:@""];
                NSLog (@"Result: %@", str1);
                
                filePath = [[NSBundle mainBundle] pathForResource:str1 ofType:@"m4a"];
                
                if ([fileManager fileExistsAtPath:filePath])
                {
                    NSLog(@"Exists");
                }
                else
                {
                    
                    NSLog (@"sound file not available");
                    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Message" message:@"Sound file not available" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
                    [alert show];
                    alert=nil;
                    
                }
                
            }
            
            
        }
        
    }
    
    // NSString *soundFilePath = [[NSBundle mainBundle] pathForResource:@"no" ofType: @"m4a"];
    if ([fileManager fileExistsAtPath:filePath])
    {
        fileURL = [[NSURL alloc] initFileURLWithPath:filePath];
        
        NSError *error;
        
        self.player = [[AVAudioPlayer alloc] initWithContentsOfURL:fileURL error:&error];
        [self.player play];
    }
}

*/

// Old backup

- (IBAction)playSoundOnClick:(id)sender
{
    NSURL *fileURL;
    NSCharacterSet *notAllowedChars = [[NSCharacterSet characterSetWithCharactersInString:@"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ "] invertedSet];
    NSString *resultString = [[self.strHabibiPhrase componentsSeparatedByCharactersInSet:notAllowedChars] componentsJoinedByString:@""];
    resultString=[resultString stringByReplacingOccurrencesOfString:@" " withString:@"_"];
    
    
    NSString *fromString;
    
    if([[[NSUserDefaults standardUserDefaults]objectForKey:@"my_Gender"] isEqualToString:@"Male"])
    {
        fromString = @"_m";
    }
    else
    {
        fromString = @"_f";
    }
    
    NSString *toString;
    if(self.btnMaleSound.isSelected)
    {
        toString = @"_m";
    }
    else
    {
        toString = @"_f";

    }

    
    
    NSString *strGender = [NSString stringWithFormat:@"%@%@",fromString,toString];
    
    /*
    if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"habibi_Gender"] isEqualToString:@"Male"] && [[[NSUserDefaults standardUserDefaults]objectForKey:@"my_Gender"] isEqualToString:@"Male"])
    {
        strGender=@"_m_m";
    }
    else if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"habibi_Gender"] isEqualToString:@"Female"] && [[[NSUserDefaults standardUserDefaults]objectForKey:@"my_Gender"] isEqualToString:@"Female"])
    {
        strGender=@"_f_f";
    }
    
    else if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"habibi_Gender"] isEqualToString:@"Male"] && [[[NSUserDefaults standardUserDefaults]objectForKey:@"my_Gender"] isEqualToString:@"Female"])
    {
        strGender=@"_f_m";

    }
    else if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"habibi_Gender"] isEqualToString:@"Female"] && [[[NSUserDefaults standardUserDefaults]objectForKey:@"my_Gender"] isEqualToString:@"Male"])
    {
        strGender=@"_m_f";

    }
     */
    
   resultString= [resultString stringByAppendingString:strGender];
    NSString *resultStr=[resultString lowercaseString];
    NSLog (@"Result: %@", resultStr);

    NSString* filePath = [[NSBundle mainBundle] pathForResource:resultString
                                                         ofType:@"m4a"];
    
    
    NSFileManager *fileManager = [NSFileManager defaultManager];
    
    
    if ([fileManager fileExistsAtPath:filePath])
    {
        NSLog(@"Exists");
    }
    else
    {
       NSString *str= [resultStr stringByReplacingCharactersInRange:NSMakeRange(resultStr.length-4,2) withString:[resultStr substringFromIndex:resultString.length-2]];
        NSLog (@"Result: %@", str);

        filePath = [[NSBundle mainBundle] pathForResource:str ofType:@"m4a"];
    
        if ([fileManager fileExistsAtPath:filePath])
        {
            NSLog(@"Exists");
        }
        else
        {
            NSString *str= [resultStr stringByReplacingCharactersInRange:NSMakeRange(resultStr.length-4,2) withString:@""];
            NSLog (@"Result: %@", str);
            
            
            filePath = [[NSBundle mainBundle] pathForResource:[str lowercaseString] ofType:@"m4a"];
            
            if ([fileManager fileExistsAtPath:filePath])
            {
                NSLog(@"Exists");
            }
            else
            {
                NSString *str1= [str stringByReplacingCharactersInRange:NSMakeRange(str.length-2,2) withString:@""];
                NSLog (@"Result: %@", str1);
                
                filePath = [[NSBundle mainBundle] pathForResource:str1 ofType:@"m4a"];
                
                if ([fileManager fileExistsAtPath:filePath])
                {
                    NSLog(@"Exists");
                }
                else
                {
                    
                    NSLog (@"sound file not available");
                    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Message" message:@"Sound file not available" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
                    [alert show];
                    alert=nil;
                    
                }
                
            }
            
            
        }
    
    }
    
   // NSString *soundFilePath = [[NSBundle mainBundle] pathForResource:@"no" ofType: @"m4a"];
    if ([fileManager fileExistsAtPath:filePath])
    {
        fileURL = [[NSURL alloc] initFileURLWithPath:filePath];

        NSError *error;
        
         self.player = [[AVAudioPlayer alloc] initWithContentsOfURL:fileURL error:&error];
        [self.player play];
    }
}
 
 

- (IBAction)PlaySoundToMale:(id)sender
{
    UIButton *btn=sender;
    int toGender;
    
    if (btn.isSelected) {
        //[btn setSelected:NO];
       // [btn setBackgroundColor:[UIColor clearColor]];
       // [self.btnFemaleSound setSelected:YES];
       // [self.btnFemaleSound setBackgroundColor:[UIColor blueColor]];
         //toGender=2;
    }
    else
    {
        [btn setSelected:YES];
        [btn setBackgroundColor:[UIColor blueColor]];
        [self.btnFemaleSound setSelected:NO];
        [self.btnFemaleSound setBackgroundColor:[UIColor clearColor]];
        
        toGender=1;
    
    
    NSMutableArray *arr= [self fetchPharseData:self.habibiPhrase_id toGender:toGender fromGender:toGender];
    if (arr.count>0)
    {
        lblPhrase1.text=self.strHabibiPhrase;
        lblPhraseTranslation1.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"native_phrase"]];
        lblPhraseTranslation2.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"phonetic_spelling"]];
        lblPhraseTranslation3.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"proper_phonetic_spelling"]];
    }
    }
    
    [self fixBorders];

}

- (IBAction)playSoundToFemale:(id)sender
{
    UIButton *btn=sender;
    int toGender;
    if ([btn isSelected])
    {
//        [btn setSelected:NO];
//        [btn setBackgroundColor:[UIColor clearColor]];
//        [self.btnMaleSound setSelected:YES];
//        [self.btnMaleSound setBackgroundColor:[UIColor blueColor]];
//         toGender=1;
    }
    else
    {
        [btn setSelected:YES];
        [btn setBackgroundColor:[UIColor blueColor]];
        [self.btnMaleSound setSelected:NO];
        [self.btnMaleSound setBackgroundColor:[UIColor clearColor]];
           toGender=2;

  
    
    NSMutableArray *arr= [self fetchPharseData:self.habibiPhrase_id toGender:toGender fromGender:toGender];
    if (arr.count>0)
    {
        lblPhrase1.text=self.strHabibiPhrase;
        lblPhraseTranslation1.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"native_phrase"]];
        lblPhraseTranslation2.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"phonetic_spelling"]];
        lblPhraseTranslation3.text=[NSString stringWithFormat:@"   %@",[[arr objectAtIndex:0]objectForKey:@"proper_phonetic_spelling"]];
    }
    }
    
    [self fixBorders];
}

-(NSMutableArray *)fetchPharseData:(NSNumber *)phraseID toGender:(int)toGender fromGender:(int)fromGender
{

    FMDatabase *database=[FMDatabase databaseWithPath:[APPDEL dbPath]];
    [database open];
    NSMutableArray *arrPhraseDetailsList=[[NSMutableArray alloc]init];
    FMResultSet *results = [database executeQuery:[NSString stringWithFormat:@"SELECT * FROM phrase where habibi_phrase_id  = %d and from_gender=%d and to_gender=%d",phraseID.intValue,fromGender,toGender]];
    NSLog(@"Qry :-- %@",[NSString stringWithFormat:@"SELECT * FROM phrase where habibi_phrase_id  = %d and from_gender=%d and to_gender=%d",phraseID.intValue,fromGender,toGender]);
    while([results next]) {
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
        
        [arrPhraseDetailsList addObject:dict];
        
    }
    [database close];
    
    return arrPhraseDetailsList;
}

-(void)fixBorders
{
    for (UIView *vw in self.view.subviews) {
        if([vw isKindOfClass:[UIButton class]])
        {
            if(vw.backgroundColor == [UIColor blueColor])
            {
                vw.layer.cornerRadius = 2.0;
                vw.layer.borderColor = [UIColor whiteColor].CGColor;
                vw.layer.borderWidth = 2.0;
                vw.clipsToBounds = YES;
            }
            else
            {
                vw.layer.cornerRadius = 2.0;
                vw.layer.borderColor = [UIColor whiteColor].CGColor;
                vw.layer.borderWidth = 0.0;
                vw.clipsToBounds = YES;
            }
            
        }
    }
    
}


@end
