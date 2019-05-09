//
//  MapViewModel.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import RxCocoa
import RxFlow
import RxSwift

class MapViewModel: ServicesViewModel, Stepper {
    let steps = PublishRelay<Step>()
    typealias Services = HasMapService
    var services: Services!
    private let disposeBag = DisposeBag()
    
    public init() {
    }
    
    
    func getRoutes(from originAirport: Airport, to destinationAirport : Airport) {
        let originIATA = originAirport.iata3
        let destinationIATA = destinationAirport.iata3
        
    }
    
    func q()  {
        services.mapService
            .getJourneyRoutes(from: "LOS", to: "123")
            .subscribe(onNext: { (vertices) in

            }, onError: { (_) in
                
            }, onCompleted: {
            }) {
                
        }.disposed(by: disposeBag)
        
        /*
        services.mapService.getAllAirports().subscribe(onNext: { (ports) in
            print(ports)
        }, onError: { (_) in
            
        }, onCompleted: {
            
        }) {
            
        }.disposed(by: disposeBag)
        */
    }

}
