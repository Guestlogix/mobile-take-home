//
//  CharactersViewModel.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/11/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

protocol CharacterServiceProtocol {
    func getCharactersFor(_ castIds: String, completionHandler: @escaping (_ characters: [CharacterModel]) -> Void, failureHandler: @escaping (_ error: String) -> Void)
}

class CharacterListService: CharacterServiceProtocol {
    func getCharactersFor(_ castIds: String, completionHandler: @escaping (_ characters: [CharacterModel]) -> Void, failureHandler: @escaping (_ error: String) -> Void) {
        let url = ServiceUrl.characterDataURL + "/" + castIds
        let baseService = BaseService(serviceType: .GET, serviceURL: url)
        baseService.startService(completionHandler: { (status, data) in
            if let responseData = CustomJSONDecoder.decodeResponseModelArrayObject(model: CharacterModel.self, data: data) {
//                self.responseData = responseData
//                self.characterSegregation()
                completionHandler(responseData)
            }
        }) { (status, errorValue) in
            failureHandler(errorValue ?? "Error")
        }
    }
}

class CharactersViewModel {

    private var responseData: [CharacterModel]?
    var finalResponse: CharacterSegregationModel?
    
    private let characterListService: CharacterServiceProtocol?
    
    init(characterListService: CharacterServiceProtocol = CharacterListService()) {
        self.characterListService = characterListService
    }
    
    func fetchCharactersFor(_ castIds: String, completionHandler: @escaping CompletionHandler) {
        characterListService?.getCharactersFor(castIds, completionHandler: { (results) in
            self.responseData = results
            self.characterSegregation()
            completionHandler(true)
        }) { (error) in
            completionHandler(false)
        }
    }
    
    func characterSegregation() {
        finalResponse = CharacterSegregationModel()
        finalResponse?.livingCharacters = [CharacterModel]()
        finalResponse?.deadCharacters = [CharacterModel]()
        
        finalResponse?.livingCharacters = responseData?.filter({ (model) -> Bool in
            return model.status?.caseInsensitiveCompare("Alive") == ComparisonResult.orderedSame
        })
        
        finalResponse?.deadCharacters = responseData?.filter({ (model) -> Bool in
            return model.status?.caseInsensitiveCompare("Dead") == ComparisonResult.orderedSame
        })
    }
    
}
