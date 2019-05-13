//
//  CSVReader.swift
//  GuestlogixTechnicalTest
//
//  Created by Camila Gaitan on 5/12/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import Foundation

class CSVReader {
    
    var fileName: String = ""
    
    init(fileName: String) {
        self.fileName = fileName
    }
    
    func readDataFromCSV()-> String!{
        guard let filepath = Bundle.main.path(forResource: fileName, ofType: "csv")
            else {
                return nil
        }
        do {
            var contents = try String(contentsOfFile: filepath, encoding: .utf8)
            contents = cleanDataRows(file: contents)
            return contents
        } catch {
            print("File Read Error for file \(filepath)")
            return nil
        }
    }
    
    
    func cleanDataRows(file:String)->String{
        var cleanFile = file
        cleanFile = cleanFile.replacingOccurrences(of: "\r", with: "\n")
        cleanFile = cleanFile.replacingOccurrences(of: "\n\n", with: "\n")
        return cleanFile
    }
    
    
    func convertToDictionaryWithData(data: String) -> [[String]] {
        var result: [[String]] = []
        let rows = data.components(separatedBy: "\n")
        for row in rows {
            let columns = row.components(separatedBy: ",")
            result.append(columns)
        }
        return result
    }
    
}
