//
//  HomeViewController.swift
//  Habibi
//
//  Created by Baljeet Singh on 10/03/15.
//  Copyright (c) 2015 B2BConnect. All rights reserved.
//

import UIKit

class HomeViewController: UIViewController {

    @IBOutlet weak var tblCategoryList: UITableView!
    
    
    var arrColor = [UIColor(red: 252.0/255.0, green: 116.0/255.0, blue: 95.0/255.0, alpha: 1.0),
                       UIColor(red: 252.0/255.0, green: 217.0/255.0, blue: 108.0/255.0, alpha: 1.0),
                       UIColor(red: 143.0/255.0, green: 194.0/255.0, blue: 204.0/255.0, alpha: 1.0),
                       UIColor(red: 30.0/255.0, green: 163.0/255.0, blue: 173.0/255.0, alpha: 1.0),
                       UIColor(red: 61.0/255.0, green: 72.0/255.0, blue: 81.0/255.0, alpha: 1.0),]
    
    var arrCategoryList = NSMutableArray()
    var appdel = AppDelegate()
    
    var currentColor:UIColor!
    var purpleColor :UIColor!
    var greenColor  :UIColor!
    var lightblueColor :UIColor!
    var darkBlueColor :UIColor!
    var darkGrayColor :UIColor!
    
    
   
    override func viewDidLoad() {
        super.viewDidLoad()
        
      self.arrCategoryList =  ModelManager.instance.getAllCategoryList()
      self.automaticallyAdjustsScrollViewInsets = false
      self.tblCategoryList.tableFooterView  = UIView(frame: CGRectZero)

        println(self.arrCategoryList)

        self.purpleColor = UIColor(red: 252.0/255.0, green: 116.0/255.0, blue: 95.0/255.0, alpha: 1.0)
        self.greenColor  = UIColor(red: 252.0/255.0, green: 217.0/255.0, blue: 108.0/255.0, alpha: 1.0)
        self.lightblueColor = UIColor(red: 143.0/255.0, green: 194.0/255.0, blue: 204.0/255.0, alpha: 1.0)
        self.darkBlueColor = UIColor(red: 30.0/255.0, green: 163.0/255.0, blue: 173.0/255.0, alpha: 1.0)
        self.darkGrayColor = UIColor(red: 61.0/255.0, green: 72.0/255.0, blue: 81.0/255.0, alpha: 1.0)
        self.currentColor  = self.purpleColor!
        
        self.navigationController?.navigationBar.hidden = true
        
         appdel = UIApplication.sharedApplication().delegate as AppDelegate

        
        self.view .addSubview(appdel.createCustomNavView(true, doneVisible: false))
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        var countVal: Int = arrCategoryList.count
        countVal=countVal+1
        return countVal

    }
    func tableView(tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat
    {
       return 0
    }
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell
    {
        var cell : UITableViewCell? = UITableViewCell(style: UITableViewCellStyle.Default, reuseIdentifier: "cell")
        
        if indexPath.row == arrCategoryList.count
        {
           // cell.textLabel.text=@"SETTINGS";
            cell?.textLabel?.text="SETTINGS"
        }
        else
        {
            //cell.textLabel.text=[[[self.arrCategoryList objectAtIndex:indexPath.row]objectForKey:@"category_name"] uppercaseString];
            
            cell?.textLabel?.text = arrCategoryList [indexPath.row] .objectForKey("category_name")?.uppercaseString

        }
        cell?.textLabel?.textAlignment = NSTextAlignment.Center
        cell?.contentView.backgroundColor = self.arrColor [indexPath.row%5]
        cell?.selectionStyle = UITableViewCellSelectionStyle(rawValue: 0)!
        
//        [cell.textLabel setTextAlignment:NSTextAlignmentCenter];
//        cell.backgroundColor=[UIColor clearColor];
//        NSLog(@"cell:-- %@",[indexPath description]);
        
        
//        [cell.contentView setBackgroundColor:[self.arrColor objectAtIndex:indexPath.row%5]];
//        
//        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        
        
        
        return cell!
    }
    
     func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat
    {
        let SCREEN_HEIGHT = UIScreen.mainScreen().bounds.size.height
       if SCREEN_HEIGHT == 667.0
        {
           //iphone 6
            return 121

        }
        else if SCREEN_HEIGHT == 736.0
          {
             //iphone 6 plus
            return 135

          }
        else
        {
            // iphone 5
            return 101

        }
        //return 101
    }
    
     func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath)
     {
        tableView .deselectRowAtIndexPath(indexPath, animated: true)
        
        if indexPath.row == arrCategoryList.count
        {
            let settingsVC = SettingsViewController(nibName: "SettingsViewController", bundle: nil)
            self.navigationController?.pushViewController(settingsVC, animated: true)
            
        }
        else
        {
            let messageVC = MessageListViewController(nibName : "MessageListViewController" , bundle: nil)

            messageVC.catID = arrCategoryList [indexPath.row] .objectForKey("id") as String
            messageVC.catName = arrCategoryList [indexPath.row] .objectForKey("category_name") as String

            
            self.navigationController?.pushViewController(messageVC, animated: true)
        }
      

    }

}
