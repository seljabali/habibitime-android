//
//  ModelManager.swift
//  DataBaseDemo
//
//  Created by Krupa-iMac on 05/08/14.
//  Copyright (c) 2014 TheAppGuruz. All rights reserved.
//

import UIKit

let sharedInstance = ModelManager()

class ModelManager: NSObject {
    
    var database: FMDatabase? = nil
    
    class var instance: ModelManager {
        sharedInstance.database = FMDatabase(path: Util.getPath("habibi_phrases.db"))
        var path = Util.getPath("habibi_phrases.db")
            //54D70B97-F386-4746-9A69-692E339668B8
        println("path : \(path)")
        return sharedInstance
    }
    
   /* func addStudentData(studentInfo: StudentInfo) -> Bool {
        sharedInstance.database!.open()
        let isInserted = sharedInstance.database!.executeUpdate("INSERT INTO StudentInfo (student_rollno, student_name) VALUES (?, ?)", withArgumentsInArray: [studentInfo.studentRollNo, studentInfo.studentName])
        sharedInstance.database!.close()
        return isInserted
    }
   
    func updateStudentData(studentInfo: StudentInfo) -> Bool {
        sharedInstance.database!.open()
        let isUpdated = sharedInstance.database!.executeUpdate("UPDATE StudentInfo SET student_name=? WHERE student_rollno=?", withArgumentsInArray: [studentInfo.studentName, studentInfo.studentRollNo])
        sharedInstance.database!.close()
        return isUpdated
    }
    
    func deleteStudentData(studentInfo: StudentInfo) -> Bool {
        sharedInstance.database!.open()
        let isDeleted = sharedInstance.database!.executeUpdate("DELETE FROM StudentInfo WHERE student_rollno=?", withArgumentsInArray: [studentInfo.studentRollNo])
        sharedInstance.database!.close()
        return isDeleted
    }*/

    func getAllCategoryList()-> NSMutableArray {
        sharedInstance.database!.open()
        var resultSet: FMResultSet! = sharedInstance.database!.executeQuery("SELECT * FROM category", withArgumentsInArray: nil)
        var rollNoColumn: String = "category_name"
        var nameColumn: String = "_id"
        var arrCatList : NSMutableArray? = NSMutableArray()

        if (resultSet != nil)
        {
            
            while resultSet.next()
            {
//                println("category_name : \(resultSet.stringForColumn(rollNoColumn))")
//                println("_id : \(resultSet.stringForColumn(nameColumn))")
                var myDictOfDict:NSDictionary = ["category_name" : (resultSet.stringForColumn(rollNoColumn)),
                    "id" : (resultSet.stringForColumn(nameColumn)),
                ]
                arrCatList!.addObject(myDictOfDict)
            }
        }
        sharedInstance.database!.close()
        return arrCatList!
    }
    
}
