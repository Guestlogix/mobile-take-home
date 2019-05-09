//
//  Vertex.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 08/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import Foundation

class Vertex<Element: Equatable> {
    var value: Element
    private(set) var adjacentEdges: [DirectedEdge<Element>] = []

    init(_ value: Element) {
        self.value = value
    }

    func addEdge(_ edge: DirectedEdge<Element>) {
        self.adjacentEdges.append(edge)
    }

    func edgeForDestination(_ destination: Vertex<Element>) -> DirectedEdge<Element>? {
        return self.adjacentEdges.filter { $0.destination == destination }.first
    }
}

extension Vertex: Equatable {
    static func ==(lhs: Vertex, rhs: Vertex) -> Bool {
        return lhs.value == rhs.value
    }
}

extension Vertex: CustomStringConvertible {
    var description: String {
        return "\n[Vertex]: \(self.value) | [Adjacent Edges]: [\(self.adjacentEdges)]"
    }
}
