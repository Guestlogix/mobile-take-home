//
//  EpisodeListViewModel.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

protocol EpisodeListServiceProtocol {
    func getEpisodes(completionHandler: @escaping (_ episodes: EpisodeModel) -> Void, failureHandler: @escaping (_ error: String) -> Void)
}

class EpisodeListService: EpisodeListServiceProtocol {
    
    func getEpisodes(completionHandler: @escaping (_ episodes: EpisodeModel) -> Void, failureHandler: @escaping (_ error: String) -> Void) {
        let url = ServiceUrl.episodeDataURL + "/"
        let baseService = BaseService(serviceType: .GET, serviceURL: url)
        baseService.startService(completionHandler: { (status, data) in
            if let responseData = CustomJSONDecoder.decodeResponseModelObject(model: EpisodeModel.self, data: data) {
                completionHandler(responseData)
            }
        }) { (status, errorValue) in
            failureHandler(errorValue ?? "Error")
        }
    }
}

/// Episode View Model
class EpisodeListViewModel {
    
    private let episodeListService: EpisodeListServiceProtocol?
    var responseData: EpisodeModel?
    
    init(episodeListService: EpisodeListServiceProtocol = EpisodeListService()) {
        self.episodeListService = episodeListService
    }
    
    func fetchEpisodes(completionHandler: @escaping CompletionHandler) {
        episodeListService?.getEpisodes(completionHandler: { (result) in
            self.responseData = result
            completionHandler(true)
        }, failureHandler: { (error) in
            completionHandler(false)
        })
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
