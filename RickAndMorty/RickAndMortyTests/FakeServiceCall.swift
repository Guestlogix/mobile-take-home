//
//  FakeEpisodeServiceCall.swift
//  RickAndMortyTests
//
//  Created by Ankita Kalangutkar on 12/07/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit
@testable import RickAndMorty

class FakeEpisodeServiceCall: EpisodeListServiceProtocol {
    func getEpisodes(completionHandler: @escaping (EpisodeModel) -> Void, failureHandler: @escaping (String) -> Void) {
        let results = EpisodeModel()
        results.results = [EpisodeResultModel.init(episodeId: 1, episodeName: "Friends", episodeType: "S1E1", characterURLs: ["Joey", "Rachel", "Monica", "Phoebe", "Chandler", "Ross"], date: "2019/04/05")]
        completionHandler(results)
    }
}

class FakeCharacterServiceCall: CharacterServiceProtocol {
    func getCharactersFor(_ castIds: String, completionHandler: @escaping ([CharacterModel]) -> Void, failureHandler: @escaping (String) -> Void) {
        let results = [CharacterModel(characterName: "Joey", imageURL: "", status: "Alive", gender: "Male", species: "Human"), CharacterModel(characterName: "Ross", imageURL: "", status: "Alive", gender: "Male", species: "Human"), CharacterModel(characterName: "Chandler", imageURL: "", status: "Alive", gender: "Male", species: "Human")]
        
        completionHandler(results)
    }
    
    
}
