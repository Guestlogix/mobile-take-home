//
//  Vertex.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-24.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import Foundation

public struct Vertex<T:Hashable> {
    public let data: T
}

extension Vertex: Hashable {
    
    public func hash(into hasher: inout Hasher) {
        hasher.combine(data)
    }
    
    public static func ==(lhs: Vertex, rhs: Vertex)-> Bool {
        return lhs.data == rhs.data
    }
}

extension Vertex:CustomStringConvertible {
    public var description: String {
        return "\(index): \(data)"
    }
}
