//
//  AirportParser.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-24.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import Foundation

class AirportParser {
    
    //MARK: Loading data
    static func  parseData (filename:String) -> [String:Airport]? {
        
        if let data = DataLoader.readDataFromCSV(filename: filename) {
            let rows = data.components(separatedBy: "\n")
            //Get rid of first line
            let dataRows = Array(rows[1...])
            
            var airports = [String:Airport]()
            for row in dataRows  {
                let rowComponents = row.components(separatedBy: ",")
                
                //If record doesn't have IATA code, or coordinates are invalid then skip it.
                if let latitude = Double(rowComponents[4]), let longitude = Double(rowComponents[5]), rowComponents[3] != "\\N" {
                    //Name
                    let name = rowComponents[0]
                    //City
                    let city = rowComponents[1]
                    //Country
                    let country = rowComponents[2]
                    //IATA 3.
                    let iata = rowComponents[3]
                    
                    let airport = Airport(iata: iata, name: name, city: city, country: country, latitude: latitude, longitude: longitude)
                    
                    airports[iata] = airport
                    
                }
            }
            
            return airports
        
        } else {
            print("There was a problem loading the data")
            return nil
        }
        
        
    }
}
