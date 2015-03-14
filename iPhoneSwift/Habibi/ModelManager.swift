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
    
  //  -(NSMutableArray *)fetchPhrases:(NSNumber *)catID
    
   /* func fetchPhrases(#catID : NSNumber) -> NSMutableArray
    {
        sharedInstance.database!.open()
       let qry = String(format: "SELECT * FROM phrase where habibi_phrase_id in (select _id from habibi_phrase where category =%02d) and language =2", catID.intValue)
        
        var resultSet: FMResultSet! = sharedInstance.database!.executeQuery(qry, withArgumentsInArray: nil)
        
   /* NSMutableArray *arrPharseList=[[NSMutableArray alloc]init];
    FMResultSet *results = [database executeQuery:[NSString stringWithFormat:@"SELECT * FROM phrase where habibi_phrase_id in (select _id from habibi_phrase where category =%d) and language =2",catID.intValue]];
    
    while([results next])
    {
    int catID  = [results intForColumn:@"_id"];
    int habibi_phrase_id  = [results intForColumn:@"habibi_phrase_id"];
    int language  = [results intForColumn:@"language"];
    int dialect  = [results intForColumn:@"dialect"];
    int from_gender  = [results intForColumn:@"from_gender"];
    int to_gender  = [results intForColumn:@"to_gender"];
    NSString *native_phrase = [results stringForColumn:@"native_phrase"];
    NSString *phonetic_spelling = [results stringForColumn:@"phonetic_spelling"];
    NSString *proper_phonetic_spelling = [results stringForColumn:@"proper_phonetic_spelling"];
    
    
    
    NSDictionary *dict=[NSDictionary dictionaryWithObjectsAndKeys:
    [NSNumber numberWithInt:catID],@"id",
    [NSNumber numberWithInt:habibi_phrase_id],@"habibi_phrase_id",
    [NSNumber numberWithInt:language],@"language",
    [NSNumber numberWithInt:dialect],@"dialect",
    [NSNumber numberWithInt:from_gender],@"from_gender",
    [NSNumber numberWithInt:to_gender],@"to_gender",
    native_phrase,@"native_phrase",
    phonetic_spelling,@"phonetic_spelling",
    proper_phonetic_spelling,@"proper_phonetic_spelling",nil];
    
    [arrPharseList addObject:dict];
    
    }
    [database close];
    return arrPharseList;*/
    }*/
    
}
