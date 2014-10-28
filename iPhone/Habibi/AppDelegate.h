//
//  AppDelegate.h
//  Habibi
//
//  Created by B2BConnect on 18/07/14.
//
//

#import <UIKit/UIKit.h>

@class ViewController;
@class SplashViewController;
@class SettingsViewController;
@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) UINavigationController *nav;
@property (strong, nonatomic) ViewController *viewController;
@property (strong, nonatomic) SplashViewController *splashviewController;
@property(nonatomic, retain) NSString *dbPath;
-(UIView *)createCustomNavView :(BOOL)logoVisible doneBtn:(BOOL)doneVisible;
-(void)addNavigationController:(id)sender;


@end
