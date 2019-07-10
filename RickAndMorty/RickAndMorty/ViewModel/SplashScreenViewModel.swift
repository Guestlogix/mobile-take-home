//
//  SplashScreenViewModel.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class SplashScreenViewModel {
    var responseData: BaseAPIModel?
    
    func getAllRequestURLs(completionHandler: @escaping CompletionHandler) {
        let url = ServiceUrl.baseURL
        let baseService = BaseService(serviceType: .GET, serviceURL: url)
        baseService.startService(completionHandler: { (status, data) in
            if let responseData = CustomJSONDecoder.decodeResponseModelObject(model: BaseAPIModel.self, data: data) {
                self.responseData = responseData
                completionHandler(true)
            }
        }) { (status, errorValue) in
            completionHandler(false)
        }
    }
}
