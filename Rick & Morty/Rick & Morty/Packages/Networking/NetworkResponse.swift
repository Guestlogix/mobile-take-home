//
//  NetworkingTypes.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import Foundation

typealias NetworkResult = Result

protocol NetworkResponse: Decodable {}

enum NetworkError: Error {
    case unknown
    case unreachable
    case decodingFailure
}

