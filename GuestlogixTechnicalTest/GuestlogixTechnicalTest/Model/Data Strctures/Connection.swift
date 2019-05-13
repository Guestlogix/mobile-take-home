//
//  Connection.swift
//  GuestlogixTechnicalTest
//
//  Created by Camila Gaitan on 5/11/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import Foundation

class Connection {
    public let to: Node
    public let weight: Int
    
    public init(to node: Node, weight: Int) {
        assert(weight >= 0, "weight has to be >= than zero")
        self.to = node
        self.weight = weight
    }
}
