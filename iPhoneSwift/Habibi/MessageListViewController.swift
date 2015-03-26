//
//  MessageListViewController.swift
//  Habibi
//
//  Created by Baljeet Singh on 14/03/15.
//  Copyright (c) 2015 B2BConnect. All rights reserved.
//

import UIKit

class MessageListViewController: UIViewController {

    var appdel = AppDelegate()
    var catID :String!
    var catName :String!
    var arrPhraseList = NSMutableArray()

    var arrColor = [UIColor(red: 252.0/255.0, green: 116.0/255.0, blue: 95.0/255.0, alpha: 1.0),
        UIColor(red: 252.0/255.0, green: 217.0/255.0, blue: 108.0/255.0, alpha: 1.0),
        UIColor(red: 143.0/255.0, green: 194.0/255.0, blue: 204.0/255.0, alpha: 1.0),
        UIColor(red: 30.0/255.0, green: 163.0/255.0, blue: 173.0/255.0, alpha: 1.0),
        UIColor(red: 61.0/255.0, green: 72.0/255.0, blue: 81.0/255.0, alpha: 1.0),]
    
    
    @IBOutlet weak var tblMsgList: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
       
        self.tblMsgList.tableFooterView  = UIView(frame: CGRectZero)
        
        self.automaticallyAdjustsScrollViewInsets = false

        self.navigationController?.navigationBar.hidden = true
        
        appdel = UIApplication.sharedApplication().delegate as AppDelegate
        
        self.view .addSubview(appdel.createCustomNavView(false, doneVisible: false))
        arrPhraseList = ModelManager.instance.fetchPhrases(catID: catID)
        println(arrPhraseList)
        tblMsgList .reloadData()
        
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return arrPhraseList.count
        
    }
    func tableView(tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat
    {
        return 0
    }
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell
    {
        var cell : UITableViewCell? = UITableViewCell(style: UITableViewCellStyle.Default, reuseIdentifier: "SimpleTableIdentifier")
        
        cell?.textLabel?.text = arrPhraseList [indexPath.row] .objectForKey("native_phrase") as NSString
        cell?.textLabel?.textAlignment = NSTextAlignment.Center

        var backgroundView : UIView = UIView(frame: CGRectMake(0, 0, self.tblMsgList.frame.size.width, 100))
        backgroundView.backgroundColor = self.arrColor [indexPath.row%5]
        cell?.selectedBackgroundView = backgroundView
        cell?.backgroundColor =  UIColor(red: 245.0/255.0, green: 245.0/255.0, blue: 245.0/255.0, alpha: 1.0)

        if DeviceType.IS_IPHONE_6P
        {
            cell?.textLabel?.font = UIFont.systemFontOfSize(20)
        }
        
        return cell!
    }
    
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath)
    {
        tableView .deselectRowAtIndexPath(indexPath, animated: true)
        
       
        
        if DeviceType.IS_IPHONE_6P
        {
            let phraseVC : PhraseDetailsViewController = PhraseDetailsViewController(nibName : "PhraseDetailsViewController-iPhone6P" , bundle: nil)
            
            phraseVC.habibiPhrase_id = self.arrPhraseList .objectAtIndex(indexPath.row) .objectForKey("habibi_phrase_id")
                as NSString
            
            phraseVC.strHabibiPhrase = self.arrPhraseList .objectAtIndex(indexPath.row).objectForKey("native_phrase") as NSString
            
            phraseVC.catName = self.catName!
          
            self.navigationController?.pushViewController(phraseVC, animated: true)
        }
        else
        {
            let phraseVC : PhraseDetailsViewController = PhraseDetailsViewController(nibName : "PhraseDetailsViewController" , bundle: nil)
            
            phraseVC.habibiPhrase_id = self.arrPhraseList .objectAtIndex(indexPath.row) .objectForKey("habibi_phrase_id")
                as NSString
            
            phraseVC.strHabibiPhrase = self.arrPhraseList .objectAtIndex(indexPath.row).objectForKey("native_phrase") as NSString
            
            phraseVC.catName = self.catName!
            
            self.navigationController?.pushViewController(phraseVC, animated: true)
        }
    }

}
