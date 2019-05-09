//
//  MapService.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import Moya
import RxCocoa
import RxSwift

protocol HasMapService {
    var mapService: MapService { get }
}

class MapService {
    static let shared = MapService()
    private let disposeBag = DisposeBag()
    
    init() {
    }
    
    func getAllRoutes() -> BehaviorRelay<[Route]> {
        var routes = [Route]()
        if let path = Bundle.main.path(forResource: "routes", ofType: "csv") {
            let url = URL(fileURLWithPath: path)
            do {
                let data = try Data(contentsOf: url)
                let dataString = String(data: data, encoding: .utf8)
                let lines = dataString!.components(separatedBy: NSCharacterSet.newlines) as [String]
                
                for line in lines {
                    var values: [String] = []
                    if line != "" {
                        if line.range(of: "\"") != nil {
                            var textToScan: String = line
                            var value: NSString?
                            var textScanner: Scanner = Scanner(string: textToScan)
                            while textScanner.string != "" {
                                if (textScanner.string as NSString).substring(to: 1) == "\"" {
                                    textScanner.scanLocation += 1
                                    textScanner.scanUpTo("\"", into: &value)
                                    textScanner.scanLocation += 1
                                } else {
                                    textScanner.scanUpTo(",", into: &value)
                                }
                                
                                values.append(value! as String)
                                
                                if textScanner.scanLocation < textScanner.string.count {
                                    textToScan = (textScanner.string as NSString).substring(from: textScanner.scanLocation + 1)
                                } else {
                                    textToScan = ""
                                }
                                textScanner = Scanner(string: textToScan)
                            }
                            
                        } else {
                            values = line.components(separatedBy: ",")
                        }
                        routes.append(Route(airlineId: values[0], origin: values[1], destination: values[2]))
                    }
                }
                if routes.count > 0 {
                    routes.removeFirst()
                }
            } catch let error {
                print("Error in reading CSV file: \(error)")
            }
        }
        return BehaviorRelay<[Route]>(value: routes)
    }
    
    func getAllAirlines() -> BehaviorRelay<[Airline]> {
        var airlines = [Airline]()
        if let path = Bundle.main.path(forResource: "airlines", ofType: "csv") {
            let url = URL(fileURLWithPath: path)
            do {
                let data = try Data(contentsOf: url)
                let dataString = String(data: data, encoding: .utf8)
                let lines = dataString!.components(separatedBy: NSCharacterSet.newlines) as [String]
                
                for line in lines {
                    var values: [String] = []
                    if line != "" {
                        if line.range(of: "\"") != nil {
                            var textToScan: String = line
                            var value: NSString?
                            var textScanner: Scanner = Scanner(string: textToScan)
                            while textScanner.string != "" {
                                if (textScanner.string as NSString).substring(to: 1) == "\"" {
                                    textScanner.scanLocation += 1
                                    textScanner.scanUpTo("\"", into: &value)
                                    textScanner.scanLocation += 1
                                } else {
                                    textScanner.scanUpTo(",", into: &value)
                                }
                                
                                values.append(value! as String)
                                
                                if textScanner.scanLocation < textScanner.string.count {
                                    textToScan = (textScanner.string as NSString).substring(from: textScanner.scanLocation + 1)
                                } else {
                                    textToScan = ""
                                }
                                textScanner = Scanner(string: textToScan)
                            }
                            
                        } else {
                            values = line.components(separatedBy: ",")
                        }
                        airlines.append(Airline(name: values[0], twoDigitCode: values[1], threeDigitCode: values[2], country: values[3]))
                    }
                }
                if airlines.count > 0 {
                    airlines.removeFirst()
                }
            } catch let error {
                print("Error in reading CSV file: \(error)")
            }
        }
        return BehaviorRelay<[Airline]>(value: airlines)
    }
    
    func getAllAirports() -> BehaviorRelay<[Airport]> {
        var airports = [Airport]()
        if let path = Bundle.main.path(forResource: "airports", ofType: "csv") {
            let url = URL(fileURLWithPath: path)
            do {
                let data = try Data(contentsOf: url)
                let dataString = String(data: data, encoding: .utf8)
                let lines = dataString!.components(separatedBy: NSCharacterSet.newlines) as [String]
                
                for line in lines {
                    var values: [String] = []
                    if line != "" {
                        if line.range(of: "\"") != nil {
                            var textToScan: String = line
                            var value: NSString?
                            var textScanner: Scanner = Scanner(string: textToScan)
                            while textScanner.string != "" {
                                if (textScanner.string as NSString).substring(to: 1) == "\"" {
                                    textScanner.scanLocation += 1
                                    textScanner.scanUpTo("\"", into: &value)
                                    textScanner.scanLocation += 1
                                } else {
                                    textScanner.scanUpTo(",", into: &value)
                                }
                                
                                values.append(value! as String)
                                
                                if textScanner.scanLocation < textScanner.string.count {
                                    textToScan = (textScanner.string as NSString).substring(from: textScanner.scanLocation + 1)
                                } else {
                                    textToScan = ""
                                }
                                textScanner = Scanner(string: textToScan)
                            }
                            
                        } else {
                            values = line.components(separatedBy: ",")
                        }
                        airports.append(Airport(name: values[0], city: values[1], country: values[2], iata3: values[3], latitude: Double(values[4]) ?? 0, longitude: Double(values[5]) ?? 0))
                    }
                }
                if airports.count > 0 {
                    airports.removeFirst()
                    airports.removeAll { $0.iata3 == "\\N" }
                }
            } catch let error {
                print("Error in reading CSV file: \(error)")
            }
        }
        return BehaviorRelay<[Airport]>(value: airports)
    }
    
    func getAirports(matchingIATA code: String) -> BehaviorRelay<[Airport]> {
        let result = BehaviorRelay<[Airport]>(value: [])
        getAllAirports()
            .flatMap({ (airports) -> Observable<[Airport]> in
                let filteredAirports = airports.filter { $0.iata3.contains(code.uppercased()) }
                return Observable.from(optional: filteredAirports)
            })
            .bind(to: result)
            .disposed(by: disposeBag)
        return result
    }
    
    func getAirport(forIATA code: String) -> BehaviorRelay<Airport> {
        let result = BehaviorRelay<Airport>(value: Airport(name: "", city: "", country: "", iata3: "", latitude: 0, longitude: 0))
        getAllAirports()
            .flatMap({ (airports) -> Observable<Airport> in
                let airport = airports.filter { $0.iata3 == code.uppercased() }
                return Observable.from(optional: airport.first)
            })
            .bind(to: result)
            .disposed(by: disposeBag)
        return result
    }
    
    func getJourneyRoutes(from originIATA: String, to destinationIATA: String) -> BehaviorRelay<[Vertex<String>]> {
        let result = BehaviorRelay<[Vertex<String>]>(value: [])
        
        Observable.combineLatest(
            getAllAirports().asObservable(),
            getAllRoutes().asObservable()
        ) { airports, routes in
            
            var vertexDitionary: [String: Vertex<String>] = [String: Vertex<String>]()
            let edgeWeightedDigraph = EdgeWeightedDigraph<String>()
            let weight = 1.0
            
            for airport in airports {
                let iataCode = airport.iata3
                let vertex = Vertex<String>(iataCode)
                vertexDitionary[iataCode] = vertex
                edgeWeightedDigraph.addVertex(vertex)
            }
            
            for route in routes {
                let source = vertexDitionary[route.origin]
                let destination = vertexDitionary[route.destination]
                
                if source != nil && destination != nil {
                    edgeWeightedDigraph.addEdge(source: source!, destination: destination!, weight: weight)
                }
            }
            
            let source = vertexDitionary[originIATA]
            let destination = vertexDitionary[destinationIATA]
            
            if source != nil && destination != nil {
                let dijkstra = DijkstraShortestPath(edgeWeightedDigraph, source: source!)
                return dijkstra.pathTo(destination!)!
            }
            return [Vertex<String>]()
        }.bind(to: result)
            .disposed(by: disposeBag)
        
        return result
    }
}
