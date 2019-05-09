//
//  AirportMarker.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 09/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import MapKit

class AirportMarker {
    let airport: Airport!
    var coodinates: CLLocationCoordinate2D!
    var title: String!
    var subTitle: String!

    public init(airport: Airport) {
        self.airport = airport
        self.coodinates = CLLocation(latitude: airport.latitude, longitude: airport.longitude).coordinate
        self.title = airport.name
        self.subTitle = airport.country
    }
}
