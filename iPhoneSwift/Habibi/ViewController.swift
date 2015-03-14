//
//  ViewController.swift
//  Habibi
//
//  Created by B2BConnect on 04/03/15.
//  Copyright (c) 2015 B2BConnect. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBAction func splashBtnOnClick(sender: UIButton) {
        self.addSettingsView()
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


    
    func addSettingsView()
    {
        var appdel = UIApplication.sharedApplication().delegate as AppDelegate

        let defaults = NSUserDefaults.standardUserDefaults()
        var habibiGender: NSString?=defaults.valueForKey("habibi_Gender") as? NSString
        var myGender: NSString?=defaults.valueForKey("my_Gender") as? NSString

      
//    if(![[NSUserDefaults standardUserDefaults] valueForKey:@"habibi_Gender"] || ![[NSUserDefaults standardUserDefaults] valueForKey:@"my_Gender"])
//    {
     if !(habibiGender != nil || myGender != nil)
        {
     
//   SettingsViewController *settingVC=[[SettingsViewController alloc]initWithNibName:@"SettingsViewUI-iPhone5" bundle:nil];
        var settingVC :SettingsViewController? = SettingsViewController(nibName:"SettingsViewController", bundle: nil)
        appdel.window!.rootViewController=settingVC
        
    }
    else
    {
       appdel.addNavigationController()
    }
    
    }
    
}

