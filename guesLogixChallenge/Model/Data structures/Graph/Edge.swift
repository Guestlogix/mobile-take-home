//
//  Edge.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-24.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import Foundation

public struct Edge<T:Hashable> {
    public let source: Vertex<T>
    public let destination: Vertex<T>
    public let weight: Double?
    public let label: String?
}
