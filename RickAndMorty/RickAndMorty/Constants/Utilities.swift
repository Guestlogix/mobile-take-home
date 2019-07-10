//
//  Utilities.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class Utilities: NSObject {
    
    class func convertToDict(data: Data?) -> [String: AnyObject]? {
        do {
            if let data = data {
                return try JSONSerialization.jsonObject(with: data, options: .allowFragments) as? [String: AnyObject]
            }
        } catch {
            print(error.localizedDescription)
        }
        return nil
    }
    
}

class CustomJSONDecoder: JSONDecoder {
    
    /// Helps in decoding the data to model object array
    class func decodeResponseModelArrayObject<T: Serializable>(model: T.Type, data: Data?) -> [T]? {
        do {
            if let dataForParsing = data {
                /// Decoding
                let responseData = try JSONDecoder().decode([T].self, from: dataForParsing)
                return responseData
            }
            return nil
        } catch let error {
            print(error)
            return nil
        }
    }
    
    ///  Helps in decoding the data to model object
    ///
    /// - Parameters:
    ///   - model: Any Serializable Model
    ///   - data: Data Object
    /// - Returns: Generic Type
    class func decodeResponseModelObject<T: Serializable>(model: T.Type, data: Data?) -> T? {
        do {
            if let dataForParsing = data {
                // Decoding from data
                let responseData = try JSONDecoder().decode(T.self, from: dataForParsing)
                return responseData
            }
            return nil
        } catch let error {
            print(error)
            return nil
        }
    }
}
