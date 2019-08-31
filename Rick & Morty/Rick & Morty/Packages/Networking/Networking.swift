//
//  Networking.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import Foundation

class Networking: NetworkingProtocol {
    
    private let session: URLSession
    
    init(session: URLSession = .shared) {
        self.session = session
    }
    
    func data(_ url: URL, completion: @escaping (Result<Data, Error>) -> Void) {
        session.dataTask(with: url) { (data, _, error) in
            if let data = data {
                completion(.success(data))
            } else if let error = error {
                completion(.failure(error))
            } else {
                completion(.failure(NetworkError.unknown))
            }
        }
    .resume()
    }
    
    func request<Request>(_ request: Request, completion: @escaping (NetworkResult<Request.Response, Error>) -> Void) where Request: NetworkRequest {
        guard let baseURL = URL(string: request.baseURL) else {
            assertionFailure("Invalid base url")
            return
        }
        
        var urlRequest = URLRequest(url: baseURL)
        urlRequest.httpMethod = request.method.rawValue
        request.headers.forEach { urlRequest.addValue($1, forHTTPHeaderField: $0) }
        request.path.forEach { urlRequest.url?.appendPathComponent($0) }
        
        session.dataTask(with: urlRequest) { (data, _, error) in
            DispatchQueue.main.async {
                if let data = data {
                    do {
                        completion(.success(try JSONDecoder().decode(Request.Response.self, from: data)))
                    } catch {
                        completion(.failure(NetworkError.decodingFailure))
                    }
                } else if let error = error {
                    completion(.failure(error))
                } else {
                    completion(.failure(NetworkError.unknown))
                }
            }
        }
        .resume()
    }
}
