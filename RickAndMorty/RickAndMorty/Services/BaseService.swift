//
//  BaseService.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

enum ServiceType: String {
    case GET
    case POST
    case PUT
    case DELETE
    case PATCH
}

typealias CompletionHandler = (_ status: Bool) -> Void

typealias SuccessClosure = (_ status: Bool, _ data: Data?) -> Void
typealias FailureClosure = (_ status: Bool, _ errorMessage: String?) -> Void

protocol Serializable: Codable {
    func serialize() -> Data?
}

extension Serializable {
    func serialize() -> Data? {
        let encoder = JSONEncoder()
        return try? encoder.encode(self)
    }
}

class BaseService: NSObject {
    var serviceType: ServiceType?
    var serviceURL: String?
    var requestBody: [String: AnyObject] = ["": "" as AnyObject]
    
    override init() {
        super.init()
    }
    
    convenience init(serviceType: ServiceType, serviceURL: String, requestData: Data? = nil) {
        self.init()
        self.serviceType = serviceType
        self.serviceURL = serviceURL
        if let requestData = requestData, let body = Utilities.convertToDict(data: requestData) {
            self.requestBody = body
        }
    }
    
    func startService(completionHandler: @escaping SuccessClosure, failureHandler: @escaping FailureClosure) {
//        TODO reachability
        
        let configuration = URLSessionConfiguration.default
        configuration.timeoutIntervalForRequest = TimeInterval(Constants.kRequestTimeOut)
        var request: URLRequest = URLRequest.init(url: URL.init(string: self.serviceURL ?? "")!)
        request.httpMethod = serviceType?.rawValue
        
        let session: URLSession = URLSession.init(configuration: configuration, delegate: nil, delegateQueue: .main)
        
        if serviceType == .POST {
            do {
                request.httpBody = try JSONSerialization.data(withJSONObject: requestBody, options: .prettyPrinted)
            } catch let error {
                print(error.localizedDescription)
            }
        }
        
        let dataTask = session.dataTask(with: request) { (data, response, error) -> Void in
            if let error = error {
                failureHandler(false, error.localizedDescription)
                session.finishTasksAndInvalidate()
            } else if let httpResponse = response as? HTTPURLResponse {
//                do {
//                    if let data = data, let json = try JSONSerialization.jsonObject(with: data) as? [String: Any] {
//
//                    } catch {
//
//                    }
//                }
                if httpResponse.statusCode != 200 {
                    
                } else {
                    completionHandler(true, data)
                }
            } else {
                completionHandler(true, data)
                session.finishTasksAndInvalidate()
            }
        }
        dataTask.resume()
        
    }
}
