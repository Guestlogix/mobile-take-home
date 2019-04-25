//
//  Airport.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-23.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import Foundation

struct Airport {
    var iata:String
    var name:String
    var city:String
    var country:String
    var latitude:Double
    var longitude:Double
}

extension Airport: Hashable {}
extension Airport: Equatable {
    static func == (lhs:Airport, rhs:Airport) -> Bool {
        return lhs.iata == rhs.iata &&
            lhs.name == rhs.name &&
        lhs.city == rhs.city &&
        lhs.country == rhs.country &&
        lhs.latitude == rhs.latitude  &&
        lhs.longitude == rhs.longitude 
    }
}
