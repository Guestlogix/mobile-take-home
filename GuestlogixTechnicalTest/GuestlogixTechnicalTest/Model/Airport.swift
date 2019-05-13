//
//  Airport.swift
//  GuestlogixTechnicalTest
//
//  Created by Camila Gaitan on 5/11/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import Foundation
import CoreLocation

class Airport: Node {
    let name: String
    let code: String
    let coordinate: CLLocation
    
    init(name: String, code: String, coordinate:CLLocation) {
        self.name = name
        self.code = code
        self.coordinate = coordinate
        super.init()
    }
    
    required init() {
        fatalError("init() has not been implemented")
    }
}
