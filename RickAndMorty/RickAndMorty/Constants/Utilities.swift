//
//  Utilities.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

extension UIImageView {
    func load(url: URL) {
        let activityIndicator = ActivityIndicator(frame: CGRect.init(x: self.frame.size.width/2, y: self.frame.size.height/2, width: 20, height: 20))
        activityIndicator.start()
        self.addSubview(activityIndicator)
        DispatchQueue.global().async {
            if let data = try? Data(contentsOf: url) {
                if let image = UIImage(data: data) {
                    DispatchQueue.main.async {
                        self.image = image
                        activityIndicator.stop()
                    }
                }
            }
        }
    }
}

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

class ActivityIndicator: UIActivityIndicatorView {
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.frame = frame
        self.style = .gray
        self.hidesWhenStopped = true
    }
    
    public func stop() {
        self.stopAnimating()
    }
    
    public func start() {
        self.startAnimating()
    }
    
    required init(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}


protocol ErrorHandlingInUI {
    func showAlert(title: String?, message: String?, viewController: UIViewController)
}

extension ErrorHandlingInUI {
    func showAlert(title: String?, message: String?, viewController: UIViewController) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        DispatchQueue.main.async {
            viewController.present(alert, animated: true, completion: nil)
        }
    }
}
