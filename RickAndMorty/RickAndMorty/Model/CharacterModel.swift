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
    
    init() {
        
    }
    
    convenience init(characterName: String, imageURL: String, status: String, gender: String, species: String) {
        self.init()
        self.characterName = characterName
        self.imageURL = imageURL
        self.status = status
        self.gender = gender
        self.species = species
    }
}

class CharacterSegregationModel: NSObject {
    var livingCharacters: [CharacterModel]?
    var deadCharacters: [CharacterModel]?
}
