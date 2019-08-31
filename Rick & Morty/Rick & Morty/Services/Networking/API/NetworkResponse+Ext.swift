//
//  NetworkResponse+Ext.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import Foundation

struct AnyNetworkResponse<Result: Decodable>: NetworkResponse {
    let info: ResponseInfo
    let results: [Result]
}

struct ResponseInfo: Decodable {
    let count: Int
    let pages: Int
    let next: String
    let prev: String
}
