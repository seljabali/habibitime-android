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
                       UIColor(red: 143.0/255.0, green: 194/255.0, blue: 204/255.0, alpha: 1.0),
                       UIColor(red: 30.0/255.0, green: 163/255.0, blue: 173.0/255.0, alpha: 1.0),
                       UIColor(red: 61.0/255.0, green: 72.0/255.0, blue: 81.0/255.0, alpha: 1.0),]
    
    var arrCategoryList = NSMutableArray()
    var appdel = AppDelegate()
    
//    [NSMutableArray arrayWithObjects:
//    [UIColor colorWithRed:252.0f/255.0f green:116.0f/255.0f blue:95.0f/255.0f alpha:1],
//    [UIColor colorWithRed:252.0f/255.0f green:217.0f/255.0f blue:108.0f/255.0f alpha:1],
//    [UIColor colorWithRed:143.0f/255.0f green:194.0f/255.0f blue:204.0f/105.0f alpha:1],
//    [UIColor colorWithRed:30.0f/255.0f green:163.0f/255.0f blue:173.0f/255.0f alpha:1],
//    [UIColor colorWithRed:61.0f/255.0f green:72.0f/255.0f blue:81.0f/255.0f alpha:1],
//    nil];
    
    
//    @property(nonatomic,strong)UIColor *currentColor;
//    @property(nonatomic,strong)UIColor *PurpleColor;
//    @property(nonatomic,strong)UIColor *greenColor;
//    @property(nonatomic,strong)UIColor *lightblueColor;
//    @property(nonatomic,strong)UIColor *darkBlueColor;
//    @property(nonatomic,strong)UIColor *darkGrayColor;
   
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
        
        println(self.arrCategoryList)

        self.purpleColor = UIColor(red: 252.0/255.0, green: 116.0/255.0, blue: 95.0/255.0, alpha: 1.0)
        self.greenColor  = UIColor(red: 252.0/255.0, green: 217.0/255.0, blue: 108.0/255.0, alpha: 1.0)
        self.lightblueColor = UIColor(red: 143.0/255.0, green: 194.0/255.0, blue: 204.0/255.0, alpha: 1.0)
        self.darkBlueColor = UIColor(red: 30.0/255.0, green: 163.0/255.0, blue: 173.0/255.0, alpha: 1.0)
        self.darkGrayColor = UIColor(red: 61.0/255.0, green: 72.0/255.0, blue: 81.0/255.0, alpha: 1.0)
        self.currentColor  = self.purpleColor!
        
        self.navigationController?.navigationBar.hidden = true
     //   [self.view addSubview:[APPDEL createCustomNavView:YES doneBtn:NO]];
        
         appdel = UIApplication.sharedApplication().delegate as AppDelegate

        // Do any additional setup after loading the view.
        
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
        var cell : UITableViewCell? = UITableViewCell(style: UITableViewCellStyle.Value1, reuseIdentifier: "cell")
        
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
        cell?.textLabel?.textAlignment = NSTextAlignment(rawValue: 1)!
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
            self.navigationController?.pushViewController(messageVC, animated: true)
        }
       /* [tableView deselectRowAtIndexPath:indexPath animated:YES];
        
        if (indexPath.row==[self.arrCategoryList count])
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
            
            [self.navigationController pushViewController:settingsVC animated:YES];
        }
        else
        {
            MessageListViewController *msgVC;
            
            if (IS_IPHONE_5)
            {
                msgVC=[[MessageListViewController alloc]initWithNibName:@"MessageListViewController-iPhone5" bundle:nil];
            }
            else
            {
                msgVC=[[MessageListViewController alloc]initWithNibName:@"MessageListViewController-iPhone4" bundle:nil];
            }
            
            msgVC.catID=[[self.arrCategoryList objectAtIndex:indexPath.row]objectForKey:@"id"];
            msgVC.catName=[[self.arrCategoryList objectAtIndex:indexPath.row]objectForKey:@"category_name"];
            
            [self.navigationController pushViewController:msgVC animated:YES];
        }
        
    }*/

    }

}
