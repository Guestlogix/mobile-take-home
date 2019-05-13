//
//  GuestlogixTechnicalTestTests.swift
//  GuestlogixTechnicalTestTests
//
//  Created by Camila Gaitan on 5/11/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import XCTest
import CoreLocation
@testable import GuestlogixTechnicalTest

class GuestlogixTechnicalTestTests: XCTestCase {

    var mapViewModel: MapViewModel?
    var nodeBogota: Airport?
    var nodeNewark: Airport?
    var nodeIAH: Airport?
    var nodeIAD: Airport?
    
    override func setUp() {
        mapViewModel = MapViewModel()
        
        nodeBogota = Airport(name: "BOG", code: "BOG", coordinate: CLLocation(latitude: 4.70159, longitude: -74.1469))
        nodeNewark = Airport(name: "EWR", code: "EWR", coordinate:  CLLocation(latitude: 40.69250107, longitude: -74.16870117))
        nodeIAH = Airport(name: "IAH", code: "IAH", coordinate: CLLocation(latitude: 29.9843998, longitude: -95.34140015))
        nodeIAD = Airport(name: "IAD", code: "IAD", coordinate: CLLocation(latitude: 38.94449997, longitude: -77.45580292))
        
        nodeBogota!.connections.append(Connection(to: nodeNewark!, weight: Int(nodeBogota!.coordinate.distance(from: nodeNewark!.coordinate ))))
        nodeBogota!.connections.append(Connection(to: nodeIAH!, weight: Int(nodeBogota!.coordinate.distance(from: nodeIAH!.coordinate))))
        nodeBogota!.connections.append(Connection(to: nodeIAD!, weight: Int(nodeBogota!.coordinate.distance(from: nodeIAD!.coordinate))))
    }

    override func tearDown() {
        mapViewModel = nil
        nodeBogota = nil
        nodeIAD = nil
        nodeIAH = nil
        nodeNewark = nil
    }
    
    func testShortestPathSuccessCase() {
        
        let path = mapViewModel?.shortestPath(source: nodeBogota!, destination: nodeNewark!)
        XCTAssertEqual(path?.cumulativeWeight, 3986539)
    }
    
    func testShortestPathFailureCase() {
        let path = mapViewModel?.shortestPath(source: nodeBogota!, destination: nodeIAH!)
        XCTAssertNotEqual(path?.cumulativeWeight, 3986539)
    }
    
    func testShortestPathRouteNotFoundCase() {
        let path = mapViewModel?.shortestPath(source: nodeBogota!, destination: nodeBogota!)
        XCTAssertEqual(path?.cumulativeWeight, 0)
    }


   

}
