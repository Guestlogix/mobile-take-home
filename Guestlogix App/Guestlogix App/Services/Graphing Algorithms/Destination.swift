//
//  Destination.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 08/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import Foundation

class Destination<Element: Equatable> {
    let vertex: Vertex<Element>
    var previousVertex: Vertex<Element>?
    var totalWeight: Double = Double.greatestFiniteMagnitude
    var isReachable: Bool {
        return totalWeight < Double.greatestFiniteMagnitude
    }

    init(_ vertex: Vertex<Element>) {
        self.vertex = vertex
    }
}
