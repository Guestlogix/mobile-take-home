//
//  CharacterRequest.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import Foundation

extension Character: NetworkResponse {}

struct CharacterRequest: NetworkRequest {
    typealias Response = Character
    var baseURL: String { url }
    let method: HTTPMethod = .get
    var path: [String] = []
    var headers: [String : String] = [:]
   
    private let url: String
    
    init(url: String) {
        self.url = url
    }
}
