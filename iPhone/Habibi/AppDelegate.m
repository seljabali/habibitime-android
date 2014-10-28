//
//  AppDelegate.m
//  Habibi
//
//  Created by B2BConnect on 18/07/14.
//
//

#import "AppDelegate.h"
#import "ViewController.h"
#import "SplashViewController.h"
#import "SettingsViewController.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // Override point for customization after application launch.
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
   [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];
    
    
   /* if(![[NSUserDefaults standardUserDefaults] valueForKey:@"habibi_Gender"])
    {
        [[NSUserDefaults standardUserDefaults]setObject:@"Female" forKey:@"habibi_Gender"];

    }

    if(![[NSUserDefaults standardUserDefaults] valueForKey:@"my_Gender"])
    {
        [[NSUserDefaults standardUserDefaults]setObject:@"Female" forKey:@"my_Gender"];
        
    }
    
    [[NSUserDefaults standardUserDefaults] synchronize];*/
    
    [self copyDatabaseIfNeeded];
     [self addCustomSplash];
   
    [self.window makeKeyAndVisible];

    return YES;
}
-(void)addCustomSplash
{
    if (IS_IPHONE_5) {
        self.splashviewController=[[SplashViewController alloc]initWithNibName:@"SplashViewController-iPhone5" bundle:nil];
    }
    else
    {
        self.splashviewController=[[SplashViewController alloc]initWithNibName:@"SplashViewController-iPhone4" bundle:nil];

    }
    [self.window setRootViewController:self.splashviewController];
   // [self performSelector:@selector(addNavigationController:) withObject:nil afterDelay:5.0];
    
}
-(void)addNavigationController:(id)sender
{
    [self.window setRootViewController:nil];

    if (IS_IPHONE_5)
    {
        self.viewController =[[ViewController alloc]initWithNibName:@"ViewController-iPhone5" bundle:nil];
        
    }
    else
    {
        self.viewController =[[ViewController alloc]initWithNibName:@"ViewController-iPhone4" bundle:nil];
        
    }
    self.nav=[[UINavigationController alloc]initWithRootViewController:self.viewController];
    
    [self.window setRootViewController:self.nav];
}


-(UIView *)createCustomNavView :(BOOL)logoVisible doneBtn:(BOOL)doneVisible
{
    UIView *navBar=[[UIView alloc]initWithFrame:CGRectMake(0, 0, 320, 64)];
    UIImageView *img1=[[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 320, 64)];
    [img1 setBackgroundColor:[UIColor blackColor]];
    
    [navBar addSubview:img1];
    if (logoVisible)
    {
    
    UIImageView *img=[[UIImageView alloc]initWithFrame:CGRectMake(10, 20, 40, 40)];
    [img setBackgroundColor:[UIColor blackColor]];
    [img setImage:[UIImage imageNamed:@"logo.png"]];
    [navBar addSubview:img];
        
        UILabel *lblName=[[UILabel alloc]initWithFrame:CGRectMake(60, 20, 100, 40)];
        lblName.text=@"Habibi Time!";
        lblName.textColor=[UIColor whiteColor];
        [navBar addSubview:lblName];
    }
    else
    {
        if([self.window.rootViewController isKindOfClass:[SettingsViewController class]])
        {
            
        }
        else
        {
        
        //show back button
        UIButton *backbtn=[UIButton buttonWithType:UIButtonTypeCustom];
        [backbtn setFrame:CGRectMake(10, 25, 50, 30)];
        [backbtn setBackgroundColor:[UIColor clearColor]];
        [backbtn setTitle:@"Back" forState:UIControlStateNormal];
        [backbtn addTarget:self action:@selector(backOnClick:) forControlEvents:UIControlEventTouchUpInside];
        [navBar addSubview:backbtn];
        }
    }
    /*UIButton *btn=[UIButton buttonWithType:UIButtonTypeCustom];
    [btn setFrame:CGRectMake(250, 20, 30, 30)];
    [btn setBackgroundColor:[UIColor clearColor]];
    [navBar addSubview:btn];

    
    if (doneVisible)
    {
        [btn setFrame:CGRectMake(270, 25, 50, 30)];

        [btn setTitle:@"Done" forState:UIControlStateNormal];
        [btn addTarget:self action:@selector(doneOnClick:) forControlEvents:UIControlEventTouchUpInside];

    }
    else
    {
        [btn setFrame:CGRectMake(280, 25, 30, 30)];

        [btn setBackgroundImage:[UIImage imageNamed:@"Settings.png"] forState:UIControlStateNormal];
       [btn addTarget:self action:@selector(settingOnClick:) forControlEvents:UIControlEventTouchUpInside];
    }*/
    return navBar;
}

-(void)settingOnClick:(id)sender
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
    
    [self.nav presentViewController:settingsVC animated:YES completion:nil];
}

-(void)backOnClick:(id)sender
{
    [self.nav popViewControllerAnimated:YES];
}

-(void)doneOnClick:(id)sender
{
    [self.nav dismissViewControllerAnimated:YES completion:nil];
}
- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}


- (void) copyDatabaseIfNeeded
{
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSError *error;
    self.dbPath = [self getDBPath];
    
    BOOL success = [fileManager fileExistsAtPath:self.dbPath];
    
    if(!success)
    {
        NSString *defaultDBPath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:@"habibi_phrases.db"];
        success = [fileManager copyItemAtPath:defaultDBPath toPath:self.dbPath error:&error];
        if (!success)
            NSAssert1(0, @"Failed to create writable database file with message '%@'.", [error localizedDescription]);
    }
}

/********* Database Path *********/
- (NSString *) getDBPath
{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,NSUserDomainMask,YES);
    NSString *documentsDir = [paths objectAtIndex:0];
    
    return [documentsDir stringByAppendingPathComponent:@"habibi_phrases.db"];
}


@end
