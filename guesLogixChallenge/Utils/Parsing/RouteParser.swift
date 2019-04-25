//
//  RouteParser.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-24.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import Foundation

class RouteParser {
    
    //MARK: Loading data
    static func parseData(airlines:[String:Airline], airports:[String:Airport], filename:String) -> AdjacencyList<Airport>? {
        
        let routes = AdjacencyList<Airport>()
        
        if let data = DataLoader.readDataFromCSV(filename: filename) {
            let rows = data.components(separatedBy: "\n")
            //Get rid of first line (headers)
            let dataRows = Array(rows[1...])
            
            //Add vertices
            for key in airports.keys {
                let airport = airports[key]!
                _ = routes.createVertex(data: airport)
            }
            
            
            //Traverse rows and parse
            for row in dataRows {
                let rowComponents = row.components(separatedBy: ",")
                //Airline 2 digit code
                let airline2Code = rowComponents[0]
                //Origin Airport IATA
                let originIATA = rowComponents[1]
                //Destination Airport IATA
                let destinationIATA = rowComponents[2]
                //Verify that an object for each element exists in their respective dictionary. Otherwise skip. This is to ensure data integrity in graph.
                if let airline = airlines[airline2Code],
                    let originAirport = airports[originIATA],
                    let destinationAirport = airports[destinationIATA] {
                    
                    let origin = Vertex(data: originAirport)
                    let destination = Vertex(data: destinationAirport)
                    
                    routes.add(.directed, from: origin, to: destination, weight: 1.0, label: airline.name)
                }
            }
            return routes
        } else {
            print("File not found")
            return nil
        }
    }
}
