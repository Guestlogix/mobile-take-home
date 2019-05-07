//
//  MapService.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import Moya
import RxCocoa
import RxSwift

protocol HasMapService {
    var mapService: MapService { get }
}

class MapService {
    static let shared = MapService()
    private let disposeBag = DisposeBag()
    
    init() {
    }
    
    func getFlightRoutes() {
        
    }
    
    func getAirports() {
        
    }
}

