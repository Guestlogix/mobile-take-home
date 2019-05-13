//
//  Node.swift
//  GuestlogixTechnicalTest
//
//  Created by Camila Gaitan on 5/11/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import Foundation

class Node {
    
    var visited = false
    var connections: [Connection] = []
    
    required init() {}
    
    init(visited:Bool, connections: [Connection]) {
        self.visited = visited
        self.connections = connections
    }
}
