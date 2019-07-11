//
//  EpisodeModel.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class EpisodeModel: Serializable {
    var info: InfoModel?
    var results: [EpisodeResultModel]?
}

class InfoModel: Serializable {
    var count: Int?
    var pages: Int?
    var next: String?
    var prev: String?
}

class EpisodeResultModel: Serializable {
    var episodeId: Int?
    var episodeName: String?
    var episodeType: String?
    var characterURLs: [String]?
    var date: String?
    
    private enum CodingKeys: String, CodingKey {
        case episodeName = "name"
        case episodeId = "id"
        case date = "created"
        case episodeType = "episode"
        case characterURLs = "characters"
    }
}
