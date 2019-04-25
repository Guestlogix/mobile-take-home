//
//  AirportManager.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-24.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import Foundation
import Mapbox


extension AirportSelectionViewController {
    
    
    func searchAirport(iata:String) -> Airport? {
        if let airport = airports[iata] {
            return airport
        } else {
            return nil
        }
    }
    
    func extractCoordinates(route:[Edge<Airport>])-> [CLLocationCoordinate2D] {
        var coordinates = [CLLocationCoordinate2D]()
        var airport: Airport
        for (index,edge) in route.enumerated() {
            if index == route.count - 1 {
                airport = edge.destination.data
            } else {
                airport = edge.source.data
            }
            let latitude = airport.latitude
            let longitude = airport.longitude
            let coordinate = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
            coordinates.append(coordinate)
        }
        return coordinates
    }
    
    
}
