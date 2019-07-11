//
//  CharacterModel.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/11/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class CharacterModel: Serializable {
    var characterName: String?
    var imageURL: String?
    var status: String?
    var gender: String?
    var species: String?
    
    private enum CodingKeys: String, CodingKey {
        case characterName = "name"
        case imageURL = "image"
        case status = "status"
        case gender = "gender"
        case species = "species"
    }
}

class CharacterSegregationModel: NSObject {
    var livingCharacters: [CharacterModel]?
    var deadCharacters: [CharacterModel]?
}
