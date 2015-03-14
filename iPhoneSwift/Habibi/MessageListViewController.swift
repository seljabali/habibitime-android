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

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.automaticallyAdjustsScrollViewInsets = false

        self.navigationController?.navigationBar.hidden = true
        
        appdel = UIApplication.sharedApplication().delegate as AppDelegate
        
        self.view .addSubview(appdel.createCustomNavView(false, doneVisible: false))
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
