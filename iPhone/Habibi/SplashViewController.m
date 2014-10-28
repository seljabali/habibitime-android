//
//  SplashViewController.m
//  Habibi
//
//  Created by B2BConnect on 19/07/14.
//
//

#import "SplashViewController.h"

@interface SplashViewController ()

@end

@implementation SplashViewController

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
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(void)addSettingsView{
    
    if(![[NSUserDefaults standardUserDefaults] valueForKey:@"habibi_Gender"] || ![[NSUserDefaults standardUserDefaults] valueForKey:@"my_Gender"])
    {
    
    [APPDEL.window setRootViewController:nil];
    SettingsViewController *settingVC=[[SettingsViewController alloc]initWithNibName:@"SettingsViewUI-iPhone5" bundle:nil];
    [APPDEL.window setRootViewController:settingVC];
    }
    else
    {
        [APPDEL addNavigationController:nil];
    }
    
}

- (IBAction)splashBtnOnClick:(id)sender
{
    [self addSettingsView];
 //   [APPDEL addNavigationController:sender];
}
@end
