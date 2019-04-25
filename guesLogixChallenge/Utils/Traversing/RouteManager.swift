//
//  RouteManager.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-23.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import Foundation


class RouteManager{
    
    static func calculateRoute (routes: AdjacencyList<Airport>, origin: Vertex<Airport>, destination: Vertex<Airport>) -> [Edge<Airport>]  {
        let dijsktra = Dijsktra(graph: routes)
        let pathsFromOrigin = dijsktra.shortestPath(from: origin)
        return dijsktra.shortestPath(to: destination, paths: pathsFromOrigin)
    }
    
}
