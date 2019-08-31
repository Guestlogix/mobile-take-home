//
//  NetworkRequest.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import Foundation

protocol NetworkRequest {
    associatedtype Response: NetworkResponse
    var baseURL: String { get }
    var method: HTTPMethod { get }
    var path: [String] { get }
    var body: [String : String] { get }
    var headers: [String : String] { get set }
}

extension NetworkRequest {
    var body: [String: String] { return [:] }
}
