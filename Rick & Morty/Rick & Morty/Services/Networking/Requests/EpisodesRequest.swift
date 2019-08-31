//
//  EpisodesRequest.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import Foundation

struct EpisodesRequest: NetworkRequest {
    typealias Response = AnyNetworkResponse<Episode>
    let method: HTTPMethod = .get
    let path: [String] = ["episode"]
    var headers: [String : String] = [:]
}
