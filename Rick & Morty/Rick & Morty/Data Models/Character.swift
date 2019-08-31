//
//  Character.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import Foundation

struct Character: Identifiable, Decodable {
    enum Status: String, Decodable, CaseIterable {
        case alive = "Alive"
        case dead = "Dead"
    }
    
    let id: Int
    let name: String
    let status: Status
    let species: String
    let type: String
    let gender: String
    let origin: Location
    let location: Location
    let image: String
}

struct Location: Decodable {
    let name: String
    let url: String
}
