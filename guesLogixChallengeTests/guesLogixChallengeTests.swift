//
//  guesLogixChallengeTests.swift
//  guesLogixChallengeTests
//
//  Created by Omar Padierna on 2019-04-23.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import XCTest
@testable import guesLogixChallenge

class guesLogixChallengeTests: XCTestCase {
    var viewControllerInstance: AirportSelectionViewController!
    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        viewControllerInstance = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "AirportSelection") as! AirportSelectionViewController
        
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    //MARK: Loading tests
    
    func testLoadingRoutes() {
        let stringData = DataLoader.readDataFromCSV(filename:"routes")
        let rows = stringData?.components(separatedBy: "\n") ?? []
        let header = rows[0]
        XCTAssertEqual(header, "Airline Id,Origin,Destination")
    }
    
    func testLoadingAirports() {
        let stringData = DataLoader.readDataFromCSV(filename:"airports")
        let rows = stringData?.components(separatedBy: "\n") ?? []
        let header = rows[0]
        XCTAssertEqual(header, "Name,City,Country,IATA 3,Latitute ,Longitude")
    }
    
    func testLoadingAirlines() {
        let stringData = DataLoader.readDataFromCSV(filename:"airlines")
        let rows = stringData?.components(separatedBy: "\n") ?? []
        let header = rows[0]
        XCTAssertEqual(header, "Name,2 Digit Code,3 Digit Code,Country")
    }
    
    //MARK: Parsing tests
    
    func testParsingAirlines(){
        let firstAirline = Airline(name: "Air Canada", twoDigitCode: "AC", threeDigitCode: "ACA", country: "Canada")
        let airlines = AirlineParser.parseData(filename: "airlines")!
        let airCanada = airlines["AC"]!
        XCTAssertEqual(firstAirline, airCanada)
    }
    
    func testParsingAirport(){
        let firstAirport = Airport(iata: "GKA", name: "Goroka Airport", city: "Goroka", country: "Papua New Guinea", latitude: -6.081689835, longitude: 145.3919983)
        let airports = AirportParser.parseData(filename: "airports")!
        let goroka = airports["GKA"]!
        XCTAssertEqual(firstAirport, goroka)
    }
    
    
    
    
    //MARK: Routing test
    func testRouting() {
        
        //Declare two airport origins
        let originTest = Airport(iata: "KTM", name: "Tribhuvan International Airport", city: "Kathmandu", country: "Nepal", latitude: 27.69659996, longitude: 85.35910034)
        let destinationTest = Airport(iata: "DAC", name: "Dhaka / Hazrat Shahjalal International Airport", city: "Dhaka", country: "Bangladesh", latitude: 23.843347, longitude: 90.397783)
        let origin = Vertex(data: originTest)
        let destination = Vertex(data: destinationTest)
        
        //Instantiate view controller and load data to get routes
        viewControllerInstance.loadData()
        //Get route
        let path = RouteManager.calculateRoute(routes: viewControllerInstance.routes, origin: origin, destination: destination)
        //Ensure that number of jumps is correct
        XCTAssertEqual(path.count,2)
    }
}
