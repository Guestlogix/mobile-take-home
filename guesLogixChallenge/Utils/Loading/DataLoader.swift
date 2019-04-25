//
//  DataLoader.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-23.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import Foundation


class DataLoader {
    
    static func readDataFromCSV(filename:String)->String! {
        guard let filepath = Bundle.main.path(forResource: filename, ofType: "csv") else {
            print("File not found!")
            return nil
        }
        
        do {
            var contents = try String(contentsOfFile: filepath, encoding: .utf8)
            contents = cleanRows(file:contents)
            return contents
        } catch {
            print ("File read error for file\(filepath)")
            return nil
        }
        
    }
    
    static func cleanRows (file:String) -> String {
        var cleanFile = file
        cleanFile = cleanFile.replacingOccurrences(of: "\r", with: "\n")
        cleanFile = cleanFile.replacingOccurrences(of: "\n\n", with: "\n")
        return cleanFile
    }
    
}
