//
//  DirectedEdge.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 08/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import Foundation

class DirectedEdge<Element: Equatable> {
    var source: Vertex<Element>
    var destination: Vertex<Element>
    var weight: Double

    init(source: Vertex<Element>, destination: Vertex<Element>, weight: Double) {
        self.source = source
        self.destination = destination
        self.weight = weight
    }
}

extension DirectedEdge: Equatable {
    static func ==(lhs: DirectedEdge, rhs: DirectedEdge) -> Bool {
        return lhs.source == rhs.source &&
            lhs.destination == rhs.destination &&
            lhs.weight == rhs.weight
    }
}

extension DirectedEdge: CustomStringConvertible {
    var description: String {
        return "\n[Edge] [Destination]: \(self.destination.value) - [Weight]: \(self.weight)"
    }
}
