//
//  MapServiceSpec.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 08/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

@testable import Guestlogix_App
import Nimble
import Quick
import RxCocoa
import RxNimble
import RxSwift

class MapServiceSpec: QuickSpec {
    override func spec() {
        var subject: MapService!
        
        beforeEach {
            subject = .shared
        }
        
        afterEach {
            subject = nil
        }
        
        describe("getAllRoutes()") {
            var routes: BehaviorRelay<[Route]>!
            
            beforeEach {
                routes = subject.getAllRoutes()
            }
            
            it("should contain at least one flight route") {
                expect(routes.value.count).to(beGreaterThan(0))
            }
        }
        
        describe("getAllAirlines()") {
            var airlines: BehaviorRelay<[Airline]>!
            
            beforeEach {
                airlines = subject.getAllAirlines()
            }
            
            it("should contain at least one airline") {
                expect(airlines.value.count).to(beGreaterThan(0))
            }
        }
        
        describe("getAllAirports()") {
            var airports: BehaviorRelay<[Airport]>!
            
            beforeEach {
                airports = subject.getAllAirports()
            }
            
            it("should contain at least one airport") {
                expect(airports.value.count).to(beGreaterThan(0))
            }
        }
        
        describe("getAirports(matchingIATA code:)") {
            var airports: BehaviorRelay<[Airport]>!
            let code = "LOS"
            let expectation = [Airport(name: "Murtala Muhammed International Airport", city: "Lagos", country: "Nigeria", iata3: "LOS", latitude: 6.577370167, longitude: 3.321160078)]
            
            beforeEach {
                airports = subject.getAirports(matchingIATA: code)
            }
            
            it("should return at least one airport") {
                expect(airports.value.count).to(beGreaterThan(0))
            }
            
            it("should return Murtala Muhammed International Airport as the first airport") {
                expect(airports).first.to(equal(expectation))
            }
        }
        
        describe("getAirport(forIATA code:)") {
            var airport: BehaviorRelay<Airport>!
            let code = "LOS"
            let expectation = Airport(name: "Murtala Muhammed International Airport", city: "Lagos", country: "Nigeria", iata3: "LOS", latitude: 6.577370167, longitude: 3.321160078)
            
            beforeEach {
                airport = subject.getAirport(forIATA: code)
            }
            
            it("should return Murtala Muhammed International Airport as the only airport") {
                expect(airport).first.to(equal(expectation))
            }
        }
        
        describe("getJourneyRoutes(from originIATA:, to destinationIATA:)") {
            var journeyRoutes: BehaviorRelay<[Vertex<String>]>!
            
            context("when a direct flight is possible") {
                let start = "ABJ"
                let end = "BRU"
                beforeEach {
                    journeyRoutes = subject.getJourneyRoutes(from: start, to: end)
                }
                
                it("should contain only one flight route") {
                    expect(journeyRoutes.value.count).to(equal(2))
                    expect(journeyRoutes.value[0].value).to(equal(start))
                    expect(journeyRoutes.value[1].value).to(equal(end))
                }
            }
            
            context("when there are stop overs") {
                let start = "LOS"
                let end = "DXB"
                beforeEach {
                    journeyRoutes = subject.getJourneyRoutes(from: start, to: end)
                }
                
                it("should contain at least one stop over") {
                    expect(journeyRoutes.value.count).to(beGreaterThan(2))
                    expect(journeyRoutes.value[0].value).to(equal(start))
                    expect(journeyRoutes.value[journeyRoutes.value.count - 1].value).to(equal(end))
                }
            }
            
            context("when there is no possible flight") {
                let start = "LOS"
                let end = "222"
                beforeEach {
                    journeyRoutes = subject.getJourneyRoutes(from: start, to: end)
                }
                
                it("should contain no flight route") {
                    expect(journeyRoutes.value.count).to(equal(0))
                }
            }
            
        }
    }
}
