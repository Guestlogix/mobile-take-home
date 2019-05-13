//
//  SplashViewModel.swift
//  GuestlogixTechnicalTest
//
//  Created by Camila Gaitan on 5/12/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import Foundation
import CoreLocation

class SplashViewModel {
    
    var airports: [Airport] = []
    var connections: [Airport] = []
    var routes: [Route] = []
    var csvReader: CSVReader?
    
    init() {}
    
    //MARK: Load Data
    
    func loadRoutes() {
        
        self.csvReader = CSVReader(fileName: "routes")
        var data = self.csvReader?.readDataFromCSV()
        data = self.csvReader?.cleanDataRows(file: data!)
        let csvRows = self.csvReader?.convertToDictionaryWithData(data: data!)
        
        for item in csvRows!{
            if(item.count == 3){
                let route = Route(airline: item[0], origin: item[1], destination: item[2])
                routes.append(route)
            }
        }
    }
    
    func loadAirports() {
        
        self.csvReader = CSVReader(fileName: "airports")
        var data = self.csvReader?.readDataFromCSV()
        data = self.csvReader?.cleanDataRows(file: data!)
        let csvRows = self.csvReader?.convertToDictionaryWithData(data: data!)
        
        for item in csvRows!{
            if (item.count == 6){
                let airport = Airport(name: item[0], code: item[3] , coordinate: CLLocation(latitude: (Double(item[4])) ?? 0.0, longitude: (Double(item[5])) ?? 0.0))
                airports.append(airport)
            }
            
        }
        
    }
    
    func createConnections(){
        
        for node in airports{
            
            let possibleRoutes = routes.filter { $0.origin == node.code}
            for item in possibleRoutes{
                let genericNode = airports.first(where: {$0.code == item.destination})
                if genericNode != nil{
                    node.connections.append(Connection(to: genericNode!, weight: Int((node.coordinate.distance(from: genericNode!.coordinate)))))
                    connections.append(node)
                }
                
            }
        }
        
    }
}
