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
        cell?.selectionStyle = UITableViewCellSelectionStyle(rawValue: 0)!
        
        return cell!
    }
    
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath)
    {
        tableView .deselectRowAtIndexPath(indexPath, animated: true)
    }

}
