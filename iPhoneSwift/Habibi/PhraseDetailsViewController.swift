//
//  PhraseDetailsViewController.swift
//  Habibi
//
//  Created by Baljeet Singh on 16/03/15.
//  Copyright (c) 2015 B2BConnect. All rights reserved.
//

import UIKit
import AVFoundation

class PhraseDetailsViewController: UIViewController {

    @IBOutlet weak var lblPhrase1: UILabel!
    
    @IBOutlet weak var btnPhraseTranslation1: UIButton!
    
    @IBOutlet weak var btnPhraseTranslation2: UIButton!
    
    @IBOutlet weak var btnPhraseTranslation3: UIButton!
    
    @IBOutlet weak var btnFemaleSound: UIButton!
    
    @IBOutlet weak var btnMaleSound: UIButton!
    
   
    @IBOutlet weak var toGenderLabel: UILabel!
    
    var habibiPhrase_id : NSString!
    
    var strHabibiPhrase : NSString!
    
    var catName : NSString!
    
    var appdel = AppDelegate()
    
    var player : AVAudioPlayer = AVAudioPlayer()
    

    @IBAction func copyBtnOnAction(sender: UIButton)
    {
        let btn = sender
        var pb : UIPasteboard = UIPasteboard.generalPasteboard()
        var alert : UIAlertView = UIAlertView(title: "Message", message: "", delegate: nil, cancelButtonTitle: "OK")

        switch (btn.tag)
        {
            case 1:
                    pb.string = btnPhraseTranslation1.titleLabel?.text
                alert.message = NSString(format:"Text has been copied to your clipboard")
                    
            case 2:
                pb.string = btnPhraseTranslation2.titleLabel?.text
                alert.message = NSString(format:"%@ has been copied to your clipboard", pb.string!)

            case 3:
                pb.string = btnPhraseTranslation3.titleLabel?.text
                alert.message = NSString(format:"%@ has been copied to your clipboard", pb.string!)
                
            default:
                pb.string = btnPhraseTranslation1.titleLabel?.text

            
        }
        
        alert.show()
        
    }
    @IBAction func PlaySoundToMale(sender: UIButton)
    {
        let btn = sender
        var toGender :Int
        
        if (btn.selected) {
            
        }
        else
        {
            
            btn.selected = true
            btn.backgroundColor = UIColor.blueColor()
            self.btnFemaleSound.selected = false
            self.btnFemaleSound.backgroundColor = UIColor.clearColor()
            
            toGender = 1
            var arr : NSMutableArray = ModelManager.instance .fetchPharseData(phraseID: self.habibiPhrase_id, toGender: NSString(format: "%d", toGender), fromGender: NSString(format: "%d", toGender))
            
            NSLog("final arr :- %@", arr.description)
            
            if arr.count > 0
            {
                lblPhrase1.text=self.strHabibiPhrase
                
                var dict : NSDictionary = arr.objectAtIndex(0) as NSDictionary
                var nativePhrase : NSString = dict .objectForKey("native_phrase") as NSString
                var phonetic_spelling : NSString = dict .objectForKey("phonetic_spelling") as NSString
                var proper_phonetic_spelling : NSString = dict.objectForKey("proper_phonetic_spelling") as NSString
                
                btnPhraseTranslation1 .setTitle(nativePhrase, forState: UIControlState.Normal)
                btnPhraseTranslation2 .setTitle(phonetic_spelling, forState: UIControlState.Normal)
                btnPhraseTranslation3 .setTitle(proper_phonetic_spelling, forState: UIControlState.Normal)
                
            }
            
        }
        
        
        self.fixBorders()
    }
    
    @IBAction func playSoundToFemale(sender: UIButton)
    {
        let btn = sender
        var toGender :Int
        
        if (btn.selected) {
            
        }
        else
        {

            
            btn.selected = true
            btn.backgroundColor = UIColor.blueColor()
            self.btnMaleSound.selected = false
            self.btnMaleSound.backgroundColor = UIColor.clearColor()
            toGender = 2
            
            
            var arr : NSMutableArray =  ModelManager.instance.fetchPharseData(phraseID: self.habibiPhrase_id, toGender: NSString(format: "%d", toGender), fromGender: NSString(format: "%d", toGender))
            NSLog("final arr :- %@", arr.description)

            if arr.count > 0
            {
                lblPhrase1.text = self.strHabibiPhrase
                
                var dict : NSDictionary = arr.objectAtIndex(0) as NSDictionary
                var nativePhrase : NSString = dict .objectForKey("native_phrase") as NSString
                var phonetic_spelling : NSString = dict .objectForKey("phonetic_spelling") as NSString
                var proper_phonetic_spelling : NSString = dict.objectForKey("proper_phonetic_spelling") as NSString
                
                btnPhraseTranslation1 .setTitle(nativePhrase, forState: UIControlState.Normal)
                btnPhraseTranslation2 .setTitle(phonetic_spelling, forState: UIControlState.Normal)
                btnPhraseTranslation3 .setTitle(proper_phonetic_spelling, forState: UIControlState.Normal)
                
            }
        }
        
        self.fixBorders()
    }
    
    @IBAction func playSoundOnClick(sender: UIButton)
    {
        var fileURL : NSURL
        var notAllowedChars : NSCharacterSet = NSCharacterSet (charactersInString: "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ").invertedSet
        var result : NSArray = self.strHabibiPhrase.componentsSeparatedByCharactersInSet(notAllowedChars)
        
        var resultString : NSString = result .componentsJoinedByString("")
        
        resultString = resultString .stringByReplacingOccurrencesOfString(" ", withString: "_")
        
        var fromString : NSString
        
        
        let my_Gender : NSString =   NSUserDefaults .standardUserDefaults() .objectForKey("my_Gender") as NSString
        
        
        if(my_Gender == "Male")
        {
            fromString = "_m";
        }
        else
        {
            fromString = "_f";
        }
        
        var toString : NSString
        
        if self.btnMaleSound.selected
        {
            toString = "_m";
        }
        else
        {
            toString = "_f";
            
        }
        
        
        var strGender : NSString = NSString(format: "%@%@", fromString,toString)
        
        
        resultString = resultString.stringByAppendingString(strGender)
        
        var resultStr : NSString = resultString.lowercaseString
        
        println(resultStr)
        
//        var filePath : NSString = NSString()
//        
//        filePath =  NSBundle.mainBundle().pathForResource(resultString, ofType: "m4a")!
        
        
         var filePath: String? = NSBundle.mainBundle().resourcePath?.stringByAppendingPathComponent(NSString(format: "%@.m4a", resultString))
        
        let fileManager =  NSFileManager.defaultManager()
        
        
         if fileManager .fileExistsAtPath(filePath!)
        {
            println("exists1")
            
        }
        else
        {

//            NSString *str= [resultStr stringByReplacingCharactersInRange:NSMakeRange(resultStr.length-4,2) withString:[resultStr substringFromIndex:resultString.length-2]];

            var str : NSString = resultStr .stringByReplacingCharactersInRange(NSMakeRange(resultStr.length-4,2), withString:resultStr.substringFromIndex(resultString.length-2))
            
            NSLog("Result %@", str)
            
            
           // filePath = NSBundle.mainBundle().pathForResource(str.lowercaseString, ofType: "m4a")!
            
          filePath =  NSBundle.mainBundle().resourcePath?.stringByAppendingPathComponent(NSString(format: "%@.m4a", str.lowercaseString))
            
            if fileManager .fileExistsAtPath(filePath!)
            {
                println("Exist2")
            }
            else
            {
                
//                NSString *str= [resultStr stringByReplacingCharactersInRange:NSMakeRange(resultStr.length-4,2) withString:@""];

                var str1 : NSString = resultStr.stringByReplacingCharactersInRange(NSMakeRange(resultStr.length-4,2), withString:"")
                NSLog("result :- %@", str1.lowercaseString)
                
                
               // filePath = NSBundle.mainBundle().pathForResource(str1, ofType: "m4a")!
               
                
                filePath =  NSBundle.mainBundle().resourcePath?.stringByAppendingPathComponent(NSString(format: "%@.m4a", str1.lowercaseString))

                
                if fileManager.fileExistsAtPath(filePath!)
                {
                    println("Exists3")
                }
                else
                {
                    var str2 : NSString = str1 .stringByReplacingCharactersInRange(NSMakeRange(str1.length-2, 2), withString: "")
                    
                    NSLog("result :- %@", str2.lowercaseString)
                    
                   // filePath = NSBundle .mainBundle().pathForResource(str1, ofType: "m4a")!
                    
                    filePath =  NSBundle.mainBundle().resourcePath?.stringByAppendingPathComponent(NSString(format: "%@.m4a", str2.lowercaseString))

                    
                    
                    if (fileManager.fileExistsAtPath(filePath!))
                    {
                             println("exists4")
                    }
                    else
                    {
                        
                        println("sound file not available");

                        var alert : UIAlertView = UIAlertView(title: "Message", message: "Sound file not available", delegate: nil, cancelButtonTitle: "OK")
                        alert.show()
                        
                        
                    }
                    
                }
                
                
            }
            
        }
        
        if (fileManager.fileExistsAtPath(filePath!))
        {
            fileURL = NSURL(fileURLWithPath: filePath!)!
            var error : NSError = NSError()
            self.player = AVAudioPlayer(contentsOfURL: fileURL, error: nil)
            self.player.play()
            
        }
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        appdel = UIApplication.sharedApplication().delegate as AppDelegate
        
        self.view .addSubview(appdel.createCustomNavView(false, doneVisible: false))

        // Do any additional setup after loading the view.
    }

    override func viewWillAppear(animated: Bool)
    {

         let cat_name = self.catName
        
        if cat_name == "Mood" || cat_name == "Responses"
        {
            self.btnFemaleSound.hidden = true
            self.btnMaleSound.hidden = true
            self.toGenderLabel.hidden = true
        }
        
        let habibiGender =  NSUserDefaults .standardUserDefaults() .objectForKey("habibi_Gender")as NSString
        
        
        
        if habibiGender == "Male"
        {
            self.PlaySoundToMale(btnMaleSound)
        }
        else
        {
            self.playSoundToFemale(btnFemaleSound)
        }
        

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

}
