//
//  Route.swift
//  GuestlogixTechnicalTest
//
//  Created by Camila Gaitan on 5/11/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import Foundation

class Route {
    let airline: String
    let origin: String
    let destination: String
    
    init(airline: String, origin: String, destination:String) {
        self.airline = airline
        self.origin = origin
        self.destination = destination
    }
}
