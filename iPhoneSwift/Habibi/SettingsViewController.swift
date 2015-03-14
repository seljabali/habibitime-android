//
//  SettingsViewController.swift
//  Habibi
//
//  Created by B2BConnect on 05/03/15.
//  Copyright (c) 2015 B2BConnect. All rights reserved.
//

import UIKit

class SettingsViewController: UIViewController {


    
    @IBOutlet weak var btnMale: UIButton!
    @IBOutlet weak var btnFemale: UIButton!
    @IBOutlet weak var btnMyGenderM: UIButton!
    @IBOutlet weak var btnMyGenderF: UIButton!
    
    @IBAction func maleRadioBtnAction(sender: UIButton)
    {
        var btn: UIButton? = sender
        if (btn!.selected)
        {
            btn!.backgroundColor = UIColor.whiteColor()
            btnFemale!.backgroundColor = UIColor.blueColor()
            btn!.selected = false
            btnFemale!.selected = true
            
            NSUserDefaults .standardUserDefaults() .setObject("Female", forKey: "habibi_Gender")
        }
        else
        
        {
            
            
            btn!.backgroundColor = UIColor.blueColor()
            btnFemale!.backgroundColor = UIColor.clearColor()
            btn!.selected = true
            btnFemale!.selected = false
            
            NSUserDefaults .standardUserDefaults() .setObject("Male", forKey: "habibi_Gender")
            
        }
        
        NSUserDefaults .standardUserDefaults() .synchronize()

        self .moveToNextScreen()
        self.fixBorders()
        
        
    }
    @IBAction func femaleRadioBtnAction(sender: UIButton)
    {
       
        var btn : UIButton? = sender
        
        
        if btn!.selected
        {
            
            btn!.backgroundColor = UIColor.clearColor()
            btnMale!.backgroundColor = UIColor.blueColor()
            btn!.selected=false
            btnMale!.selected=true
            NSUserDefaults .standardUserDefaults() .setObject("Male", forKey: "habibi_Gender")
            
      }
       else
       {
        
            btn!.backgroundColor=UIColor.blueColor()
            btnMale!.backgroundColor=UIColor.clearColor()
            btn!.selected=true
            btnMale!.selected=false
            NSUserDefaults .standardUserDefaults() .setObject("Female", forKey: "habibi_Gender")
        
        }
        NSUserDefaults .standardUserDefaults() .synchronize()
        self .moveToNextScreen()

        self.fixBorders()
        
  
        
    }
    
    @IBAction func myGenderMaleOnClick(sender: UIButton)
    {
        var btn :UIButton? = sender
        
        if(btn!.selected)
        {
 
            btn!.backgroundColor = UIColor.clearColor()
            btnMyGenderF!.backgroundColor = UIColor .blueColor()
            btn!.selected = false
            btnMyGenderF!.selected = true
            NSUserDefaults .standardUserDefaults() .setObject("Female", forKey: "my_Gender")
            
        }
        else
        {
  
            btn!.backgroundColor = UIColor.blueColor()
            btnMyGenderF!.backgroundColor = UIColor.clearColor()
            btn!.selected = true
            btnMyGenderF!.selected = false
            NSUserDefaults .standardUserDefaults() .setObject("Male", forKey: "my_Gender")

        }
        
        
             NSUserDefaults .standardUserDefaults() .synchronize()
        self .moveToNextScreen()
        
        self.fixBorders()
    }
    
    @IBAction func myGenderFemaleOnClick(sender: UIButton)
    {
        
        var btn: UIButton?=sender
        
        if (btn!.selected)
        {
            
            btn!.backgroundColor = UIColor .clearColor()
            btnMyGenderM!.backgroundColor = UIColor.blueColor()
            btn!.selected = false
            btnMyGenderM!.selected = true
            NSUserDefaults .standardUserDefaults() .setObject("Male", forKey: "my_Gender")
            
            
        }
        else
        {
            
            btn!.backgroundColor = UIColor.blueColor()
            btnMyGenderM!.backgroundColor = UIColor.clearColor()
            btn!.selected = true
            btnMyGenderM!.selected = false
            NSUserDefaults .standardUserDefaults() .setObject("Female", forKey: "my_Gender")
            
            
        }
        
        
        NSUserDefaults .standardUserDefaults() .synchronize()
        self .moveToNextScreen()
        
        self.fixBorders()
    }
    
    
     func moveToNextScreen()
    {

        
        var appdel: AppDelegate = UIApplication.sharedApplication().delegate as AppDelegate
        
        if appdel.window!.rootViewController==self
        {
             if((btnMale!.selected || btnFemale!.selected) && (btnMyGenderF!.selected || btnMyGenderM!.selected))
             {
                appdel .addNavigationController()
            }
        }
        
        
    }
    
    func fixBorders()
    {

      for o : AnyObject in self.view.subviews
     {
           // println("Hello World")

            if let v = o as? UIButton
            {
                //println("Print btn")
              //  println(v)
                if (v.backgroundColor == UIColor.blueColor())
                {
                    v.layer.cornerRadius = 2.0
                    v.layer.borderColor = UIColor.whiteColor().CGColor
                    v.layer.borderWidth = 2.0
                    v.clipsToBounds = true
                    
                }
                else
                {
                    v.layer.cornerRadius = 2.0
                    v.layer.borderColor = UIColor.whiteColor().CGColor
                    v.layer.borderWidth = 0.0
                    v.clipsToBounds = true
                }

            }
           
        }
    
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        var appdel = UIApplication.sharedApplication().delegate as AppDelegate
        
        self.view .addSubview(appdel .createCustomNavView(false, doneVisible: false))

        let defaults = NSUserDefaults.standardUserDefaults()
        let habibiGender: NSString?=defaults.valueForKey("habibi_Gender") as? NSString
        let myGender: NSString?=defaults.valueForKey("my_Gender") as? NSString
        
        if (habibiGender != nil || myGender != nil)
        {
           // quotation == sameQuotation
           if (habibiGender == "Male")
            {
                self.btnMale.selected = true
                self.btnMale.backgroundColor = UIColor.blueColor()
                self.btnFemale.selected = false
                self.btnFemale.backgroundColor = UIColor.clearColor()
            }
           else
            {
                self.btnFemale.selected = true
                self.btnFemale.backgroundColor = UIColor.blueColor()
                self.btnMale.selected = false
                self.btnMale.backgroundColor = UIColor.clearColor()
                
            }
           if(myGender == "Male")
            {
                self.btnMyGenderM.selected = true
                self.btnMyGenderM.backgroundColor = UIColor.blueColor()
                self.btnMyGenderF.selected = false
                self.btnMyGenderF.backgroundColor = UIColor.clearColor()
                
            }
            else
           {
               self.btnMyGenderF.selected = true
               self.btnMyGenderF.backgroundColor = UIColor.blueColor()
               self.btnMyGenderM.selected = false
               self.btnMyGenderM.backgroundColor = UIColor.clearColor()
            
            }
            
            
        }

             self.fixBorders()

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
