//
//  SettingsViewController.m
//  Habibi
//
//  Created by B2BConnect on 20/07/14.
//
//

#import "SettingsViewController.h"

@interface SettingsViewController ()

@end

@implementation SettingsViewController

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
    [self.view addSubview:[APPDEL createCustomNavView:NO doneBtn:NO]];
    NSLog(@"%@",[[NSUserDefaults standardUserDefaults] valueForKey:@"habibi_Gender"]);
    NSLog(@"%@",[[NSUserDefaults standardUserDefaults] valueForKey:@"my_Gender"]);

    if([[NSUserDefaults standardUserDefaults] valueForKey:@"habibi_Gender"] || [[NSUserDefaults standardUserDefaults] valueForKey:@"my_Gender"])
    {
        
            if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"habibi_Gender"]isEqualToString:@"Male"])
            {

                [btnMale setSelected:YES];
                [btnMale setBackgroundColor:[UIColor blueColor]];
                [btnFemale setSelected:NO];
                [btnFemale setBackgroundColor:[UIColor clearColor]];

            }
            else
            {

                [btnFemale setSelected:YES];
                [btnFemale setBackgroundColor:[UIColor blueColor]];
                [btnMale setSelected:NO];
                [btnMale setBackgroundColor:[UIColor clearColor]];
            }
            
            
            if ([[[NSUserDefaults standardUserDefaults]objectForKey:@"my_Gender"]isEqualToString:@"Male"])
            {

                [btnMyGenderM setSelected:YES];
                [btnMyGenderM setBackgroundColor:[UIColor blueColor]];
                [btnMyGenderF setSelected:NO];
                [btnMyGenderF setBackgroundColor:[UIColor clearColor]];

            }
            else
            {
                
                [btnMyGenderF setSelected:YES];
                [btnMyGenderF setBackgroundColor:[UIColor blueColor]];
                [btnMyGenderM setSelected:NO];
                [btnMyGenderM setBackgroundColor:[UIColor clearColor]];
            }
    }
	// Do any additional setup after loading the view.
    
    
    
    [self fixBorders];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)maleRadioBtnAction:(id)sender
{
    UIButton *btn=sender;
//    if ([btn currentBackgroundImage]==[UIImage imageNamed:@"radio-checked.png"])
//    {
//        [btn setBackgroundImage:[UIImage imageNamed:@"radio-unchecked.png"] forState:UIControlStateNormal];
//        [btnFemale setBackgroundImage:[UIImage imageNamed:@"radio-checked.png"] forState:UIControlStateNormal];
//        
//        [[NSUserDefaults standardUserDefaults]setObject:@"Female" forKey:@"habibi_Gender"];
//
//    }
//    else
//    {
//        [btn setBackgroundImage:[UIImage imageNamed:@"radio-checked.png"] forState:UIControlStateNormal];
//        [btnFemale setBackgroundImage:[UIImage imageNamed:@"radio-unchecked.png"] forState:UIControlStateNormal];
//        [[NSUserDefaults standardUserDefaults]setObject:@"Male" forKey:@"habibi_Gender"];
//        
//    }
    
    
    if ([btn isSelected])
    {
        [btn setBackgroundColor:[UIColor clearColor]];
        [btnFemale setBackgroundColor:[UIColor blueColor]];
        [btn setSelected:NO];
        [btnFemale setSelected:YES];
        
        [[NSUserDefaults standardUserDefaults]setObject:@"Female" forKey:@"habibi_Gender"];
        
    }
    else
    {
        [btn setBackgroundColor:[UIColor blueColor]];
        [btnFemale setBackgroundColor:[UIColor clearColor]];
        [btn setSelected:YES];
        [btnFemale setSelected:NO];
        
        [[NSUserDefaults standardUserDefaults]setObject:@"Male" forKey:@"habibi_Gender"];
        
    }
    
    [[NSUserDefaults standardUserDefaults] synchronize];

    [self moveToNextScreen];
    
    [self fixBorders];


}

- (IBAction)femaleRadioBtnAction:(id)sender
{
    UIButton *btn=sender;
//    if ([btn currentBackgroundImage]==[UIImage imageNamed:@"radio-checked.png"])
//    {
//        [btn setBackgroundImage:[UIImage imageNamed:@"radio-unchecked.png"] forState:UIControlStateNormal];
//        [btnMale setBackgroundImage:[UIImage imageNamed:@"radio-checked.png"] forState:UIControlStateNormal];
//        [[NSUserDefaults standardUserDefaults]setObject:@"Male" forKey:@"habibi_Gender"];
//
//    }
//    else
//    {
//        [btn setBackgroundImage:[UIImage imageNamed:@"radio-checked.png"] forState:UIControlStateNormal];
//        [btnMale setBackgroundImage:[UIImage imageNamed:@"radio-unchecked.png"] forState:UIControlStateNormal];
//        [[NSUserDefaults standardUserDefaults]setObject:@"Female" forKey:@"habibi_Gender"];
//
//    }
    
    
    if ([btn isSelected])
    {
        
        [btn setBackgroundColor:[UIColor clearColor]];
        [btnMale setBackgroundColor:[UIColor blueColor]];
        [btn setSelected:NO];
        [btnMale setSelected:YES];
        
        [[NSUserDefaults standardUserDefaults]setObject:@"Male" forKey:@"habibi_Gender"];
        
    }
    else
    {
        
        [btn setBackgroundColor:[UIColor blueColor]];
        [btnMale setBackgroundColor:[UIColor clearColor]];
        [btn setSelected:YES];
        [btnMale setSelected:NO];
        
        [[NSUserDefaults standardUserDefaults]setObject:@"Female" forKey:@"habibi_Gender"];
        
    }
    
    [[NSUserDefaults standardUserDefaults] synchronize];
    [self moveToNextScreen];
    
    [self fixBorders];


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

- (IBAction)myGenderMaleOnClick:(id)sender
{
    UIButton *btn=sender;
//    if ([btn currentBackgroundImage]==[UIImage imageNamed:@"radio-checked.png"])
//    {
//        [btn setBackgroundImage:[UIImage imageNamed:@"radio-unchecked.png"] forState:UIControlStateNormal];
//        [btnMyGenderF setBackgroundImage:[UIImage imageNamed:@"radio-checked.png"] forState:UIControlStateNormal];
//        [[NSUserDefaults standardUserDefaults]setObject:@"Female" forKey:@"my_Gender"];
//        
//    }
//    else
//    {
//        [btn setBackgroundImage:[UIImage imageNamed:@"radio-checked.png"] forState:UIControlStateNormal];
//        [btnMyGenderF setBackgroundImage:[UIImage imageNamed:@"radio-unchecked.png"] forState:UIControlStateNormal];
//        [[NSUserDefaults standardUserDefaults]setObject:@"Male" forKey:@"my_Gender"];
//        
//    }
    
    
    if ([btn isSelected])
    {

        
        [btn setBackgroundColor:[UIColor clearColor]];
        [btnMyGenderF setBackgroundColor:[UIColor blueColor]];
        [btn setSelected:NO];
        [btnMyGenderF setSelected:YES];
        [[NSUserDefaults standardUserDefaults]setObject:@"Female" forKey:@"my_Gender"];

    }
    else
    {

        
        
        [btn setBackgroundColor:[UIColor blueColor]];
        [btnMyGenderF setBackgroundColor:[UIColor clearColor]];
        [btn setSelected:YES];
        [btnMyGenderF setSelected:NO];
        [[NSUserDefaults standardUserDefaults]setObject:@"Male" forKey:@"my_Gender"];
        
    }
    
    
    [[NSUserDefaults standardUserDefaults] synchronize];
    [self moveToNextScreen];
    
    [self fixBorders];


}

- (IBAction)myGenderFemaleOnClick:(id)sender
{
    UIButton *btn=sender;

//    if ([btn currentBackgroundImage]==[UIImage imageNamed:@"radio-checked.png"])
//    {
//        [btn setBackgroundImage:[UIImage imageNamed:@"radio-unchecked.png"] forState:UIControlStateNormal];
//        [btnMyGenderM setBackgroundImage:[UIImage imageNamed:@"radio-checked.png"] forState:UIControlStateNormal];
//        [btnMyGenderM setBackgroundColor:[UIColor greenColor]];
//        [[NSUserDefaults standardUserDefaults]setObject:@"Male" forKey:@"my_Gender"];
//        
//    }
//    else
//    {
//        [btn setBackgroundImage:[UIImage imageNamed:@"radio-checked.png"] forState:UIControlStateNormal];
//        [btnMyGenderM setBackgroundImage:[UIImage imageNamed:@"radio-unchecked.png"] forState:UIControlStateNormal];
//        [[NSUserDefaults standardUserDefaults]setObject:@"Female" forKey:@"my_Gender"];
//        
//    }
    
    
    
    if ([btn isSelected])
    {

        [btn setBackgroundColor:[UIColor clearColor]];
        [btnMyGenderM setBackgroundColor:[UIColor blueColor]];
        [[NSUserDefaults standardUserDefaults]setObject:@"Male" forKey:@"my_Gender"];
        [btn setSelected:NO];
        [btnMyGenderM setSelected:YES];
        
        
    }
    else
    {
        [btn setBackgroundColor:[UIColor blueColor]];
        [btnMyGenderM setBackgroundColor:[UIColor clearColor]];
        [btn setSelected:YES];
        [btnMyGenderM setSelected:NO];
        [[NSUserDefaults standardUserDefaults]setObject:@"Female" forKey:@"my_Gender"];
        
    }
    
    [[NSUserDefaults standardUserDefaults] synchronize];
    [self moveToNextScreen];
    [self fixBorders];

}

-(void)moveToNextScreen
{
    if ([[APPDEL.window rootViewController] isKindOfClass:[self class]])
    {
        if (([btnMale isSelected]||[btnFemale isSelected])&&([btnMyGenderF isSelected]||[btnMyGenderM isSelected]))
        {
            [APPDEL addNavigationController:nil];
        }
    }
    else
    {
        
    }
    
}
@end
