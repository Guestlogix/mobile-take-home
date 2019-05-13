//
//  MapViewModel.swift
//  GuestlogixTechnicalTest
//
//  Created by Camila Gaitan on 5/13/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import Foundation

class MapViewModel{
    
    var connections: [Airport] = []
    var airports: [Airport] = []
    var routes: [Route] = []
    
    func shortestPath(source: Node, destination: Node) -> Path? {
        
        
        var route: [Path] = [] {
            didSet { route.sort { return $0.cumulativeWeight < $1.cumulativeWeight } }
        }
        
        route.append(Path(to: source))
        
        while !route.isEmpty {
            let cheapestPathInRoute = route.removeFirst()
            guard !cheapestPathInRoute.node.visited else { continue }
            
            if cheapestPathInRoute.node === destination {
                return cheapestPathInRoute
            }
            
            cheapestPathInRoute.node.visited = true
            
            for connection in cheapestPathInRoute.node.connections where !connection.to.visited {
                route.append(Path(to: connection.to, via: connection, previousPath: cheapestPathInRoute))
            }
        }
        return nil
    }
    
    func createConnections() -> [Airport]{
        
        for node in airports{
            
            let possibleRoutes = routes.filter { $0.origin == node.code}
            for item in possibleRoutes{
                let genericNode = airports.first(where: {$0.code == item.destination})
                if genericNode != nil{
                    node.visited = false
                    node.connections.append(Connection(to: genericNode!, weight: Int((node.coordinate.distance(from: genericNode!.coordinate)))))
                    connections.append(node)
                }
                
            }
        }
        return connections
    }
    
}
