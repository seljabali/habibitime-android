//
//  AppDelegate.swift
//  Habibi
//
//  Created by B2BConnect on 04/03/15.
//  Copyright (c) 2015 B2BConnect. All rights reserved.
//

import UIKit

enum UIUserInterfaceIdiom : Int
{
    case Unspecified
    case Phone
    case Pad
}

struct ScreenSize
{
    static let SCREEN_WIDTH = UIScreen.mainScreen().bounds.size.width
    static let SCREEN_HEIGHT = UIScreen.mainScreen().bounds.size.height
    static let SCREEN_MAX_LENGTH = max(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT)
    static let SCREEN_MIN_LENGTH = min(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT)
}

struct DeviceType
{
    static let IS_IPHONE_4_OR_LESS =  UIDevice.currentDevice().userInterfaceIdiom == .Phone && ScreenSize.SCREEN_MAX_LENGTH < 568.0
    static let IS_IPHONE_5 = UIDevice.currentDevice().userInterfaceIdiom == .Phone && ScreenSize.SCREEN_MAX_LENGTH == 568.0
    static let IS_IPHONE_6 = UIDevice.currentDevice().userInterfaceIdiom == .Phone && ScreenSize.SCREEN_MAX_LENGTH == 667.0
    static let IS_IPHONE_6P = UIDevice.currentDevice().userInterfaceIdiom == .Phone && ScreenSize.SCREEN_MAX_LENGTH == 736.0
}




@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    var nav :UINavigationController?
    var dbPath :NSString?
   
    
    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
         println("Hello World")
        UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.LightContent;
        
        self.window = UIWindow(frame: UIScreen.mainScreen().bounds)
        self.window!.backgroundColor = UIColor.whiteColor()
        self.window!.makeKeyAndVisible()
        Util.copyFile("habibi_phrases.db")

        self.createCustomSplash()
        return true
    }

    func createCustomSplash()
    {
        var viewController: ViewController? = ViewController(nibName: "ViewController", bundle: nil)
        self.window!.rootViewController = viewController
    }
    
    func addNavigationController()
    {
        self.window!.rootViewController=nil
        
        var homeVC: HomeViewController? = HomeViewController(nibName: "HomeViewController",bundle:nil)
    
        self.nav = UINavigationController(rootViewController: homeVC!)
        
        self.window!.rootViewController = self.nav

    }
    
    
    
    
//    func countMultiples(multipleOf:Int, numbers: [Int]) -> Int   // function with multiple parametes and one return type
    func createCustomNavView(logoVisible: Bool, doneVisible: Bool) -> UIView
    {
        var navBar : UIView? = UIView(frame: CGRectMake(0, 0, ScreenSize.SCREEN_WIDTH, 64))
        var img1 : UIImageView? = UIImageView(frame: CGRectMake(0, 0, ScreenSize.SCREEN_WIDTH, 64))
        img1?.backgroundColor = UIColor.blackColor()
        navBar?.addSubview(img1!)
        
        
        if logoVisible
        {
            var img :UIImageView? = UIImageView(frame: CGRectMake(10, 20, 40, 40))
            img?.backgroundColor = UIColor.blackColor()
//            var imagename : UIImage = UIImage(named:"logo.png")!
            img?.image = UIImage(named:"logo.png")
            navBar?.addSubview(img!)

            
            var lblName : UILabel? = UILabel(frame: CGRectMake((ScreenSize.SCREEN_WIDTH-100)/2, 20, 100, 40))
            lblName?.text = "Habibi Time!"
            lblName?.textColor = UIColor.whiteColor()
            navBar?.addSubview(lblName!)
            
            
        }
        
        else
        {
            
                if (self.window?.rootViewController!.isKindOfClass(SettingsViewController))!
                {
                    println("This is first time setting view")
                }
             else
                {
                    var backBtn : UIButton? = UIButton(frame: CGRectMake(10, 25, 50, 30))
                    backBtn?.backgroundColor = UIColor.clearColor()
                    backBtn?.setTitle("Back", forState: UIControlState.Normal)
                    backBtn?.addTarget(self, action: "backOnClick:", forControlEvents: UIControlEvents.TouchUpInside)
                    navBar?.addSubview(backBtn!)
                    
                }
        }
        
        
       

//        if (doneVisible)
//        {
//            var doneBtn : UIButton? = UIButton(frame: CGRectMake(270, 25, 50, 30))
//            doneBtn?.backgroundColor = UIColor.clearColor()
//            doneBtn?.setTitle("Done", forState: UIControlState.Normal)
//            doneBtn?.addTarget(self, action: "doneOnClick:", forControlEvents: UIControlEvents.TouchUpInside)
//            navBar?.addSubview(doneBtn!)
//
//        }
//        else
//        {
//            
//        }
   
      return navBar!
    }
    
    func backOnClick(sender: UIButton)
    {
    //[self.nav popViewControllerAnimated:YES];
        self.nav?.popViewControllerAnimated(true)
        
    }
    
    func doneOnClick(sender: UIButton)
    {
   // [self.nav dismissViewControllerAnimated:YES completion:nil];
        self.nav?.dismissViewControllerAnimated(true, completion: nil)
    }
    
    func applicationWillResignActive(application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(application: UIApplication) {
        // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
    
    
    
    


}

