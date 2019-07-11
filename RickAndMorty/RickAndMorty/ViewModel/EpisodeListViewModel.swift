//
//  EpisodeListViewModel.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class EpisodeListViewModel: NSObject {
    
    var responseData: EpisodeModel?
    
    func getEpisodes(completionHandler: @escaping CompletionHandler) {
        let url = ServiceUrl.episodeDataURL + "/"
        let baseService = BaseService(serviceType: .GET, serviceURL: url)
        baseService.startService(completionHandler: { (status, data) in
            if let responseData = CustomJSONDecoder.decodeResponseModelObject(model: EpisodeModel.self, data: data) {
                self.responseData = responseData
                completionHandler(true)
            }
        }) { (status, errorValue) in
            completionHandler(false)
        }
    }
    
    func getCharacterIds(_ characters: [String]) -> String {
        let characterIds = Array(Set(characters.map { (a) -> String in
            if let value = a.last {
                return String(value)
            }
            return ""
        }))
        return characterIds.joined(separator: ",")
    }
}
