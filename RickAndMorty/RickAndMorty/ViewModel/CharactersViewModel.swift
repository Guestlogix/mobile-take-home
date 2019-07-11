//
//  CharactersViewModel.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/11/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class CharactersViewModel: NSObject {

    private var responseData: [CharacterModel]?
    var finalResponse: CharacterSegregationModel?
    
    func getCharactersFor(_ castIds: String, completionHandler: @escaping CompletionHandler) {
        let url = ServiceUrl.characterDataURL + "/" + castIds
        let baseService = BaseService(serviceType: .GET, serviceURL: url)
        baseService.startService(completionHandler: { (status, data) in
            if let responseData = CustomJSONDecoder.decodeResponseModelArrayObject(model: CharacterModel.self, data: data) {
                self.responseData = responseData
                self.characterSegregation()
                completionHandler(true)
            }
        }) { (status, errorValue) in
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
