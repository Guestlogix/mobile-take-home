//
//  NetworkingProtocol.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import Foundation

protocol NetworkingProtocol {
    func data(_  url: URL, completion: @escaping (NetworkResult<Data, Error>) -> Void)
    func request<Request: NetworkRequest>(_ request: Request, completion: @escaping (NetworkResult<Request.Response, Error>) -> Void)
}
