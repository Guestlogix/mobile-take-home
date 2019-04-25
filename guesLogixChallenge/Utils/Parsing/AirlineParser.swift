//
//  AirlineParser.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-24.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import Foundation

class AirlineParser {
    
    //MARK: Loading data
    static func  parseData (filename:String) -> [String:Airline]? {
        var airlines = [String:Airline]()
        if let data = DataLoader.readDataFromCSV(filename: filename) {
            let rows = data.components(separatedBy: "\n")
            //Get rid of first line
            let dataRows = Array(rows[1...])
            //Traverse rows and parse
            for row in dataRows {
                let rowComponents = row.components(separatedBy: ",")
                //Name
                let name = rowComponents[0]
                //2 Digit Code
                let twoDigitCode = rowComponents[1]
                //3 Digit Code
                let threeDigitCode = rowComponents[2]
                //Country
                let country = rowComponents[3]
                
                let airline = Airline(name: name, twoDigitCode: twoDigitCode, threeDigitCode: threeDigitCode, country: country)
                
                airlines[twoDigitCode]=airline
            }
            return airlines
            
        } else {
            print("File not found!")
            return nil
        }
        
    }
}

