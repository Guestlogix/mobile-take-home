//
//  AirportSearchViewModel.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 09/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import RxCocoa
import RxFlow
import RxSwift

class AirportSearchViewModel: ServicesViewModel, Stepper {
    let steps = PublishRelay<Step>()
    typealias Services = HasMapService
    var services: Services!
    
    var sourceTextFieldString = BehaviorRelay<String>(value: "")
    var destinationTextFieldString = BehaviorRelay<String>(value: "")
    var showRouteButtonIsActive = BehaviorRelay<Bool>(value: false)
    var isLoading = BehaviorRelay<Bool>(value: false)
    var airportMarkers = BehaviorRelay<[AirportMarker]>(value: [])
    var errorMessage = BehaviorRelay<String>(value: "")
    private let disposeBag = DisposeBag()
    
    public init() {
        Observable.combineLatest(
            self.sourceTextFieldString.asObservable(),
            self.destinationTextFieldString.asObservable()
        ) { source, destination in
            self.validateSearchFields(source: source, destination: destination)
        }.subscribe { _ in
        }.disposed(by: self.disposeBag)
    }
    
    func validateSearchFields(source: String, destination: String) {
        let searchFieldsAreValid = sourceIsValid(source) && destinationIsValid(destination)
        self.showRouteButtonIsActive.accept(searchFieldsAreValid)
    }
    
    func sourceIsValid(_ source: String) -> Bool {
        return source.trim().count >= 3
    }
    
    func destinationIsValid(_ destination: String) -> Bool {
        return destination.trim().count >= 3
    }
    
    func getAirportMarkers(from originIATA: String?, to destinationIATA: String?) {
        guard let originIATA = originIATA?.uppercased() else { return }
        guard let destinationIATA = destinationIATA?.uppercased() else { return }
        
        if originIATA == destinationIATA {
            self.errorMessage.accept("Destination IATA code cannot be the same as origin IATA code")
            return
        }
        
        self.isLoading.accept(true)
        
        DispatchQueue.global(qos: .userInitiated).async { [weak self] in
            guard let self = self else {
                return
            }
            self.services.mapService
                .getJourneyRoutes(from: originIATA, to: destinationIATA)
                .flatMap({ (vertices) -> Observable<[AirportMarker]> in
                    
                    var markers = [AirportMarker]()
                    for vertex in vertices {
                        let airport = self.services.mapService.getAirport(forIATA: vertex.value)
                        markers.append(AirportMarker(airport: airport.value))
                    }
                    
                    if markers.count > 0 {
                        DispatchQueue.main.async { [weak self] in
                            self!.isLoading.accept(false)
                        }
                    } else {
                        self.getErrorForRoute(from: originIATA, to: destinationIATA)
                        DispatchQueue.main.async { [weak self] in
                            self!.isLoading.accept(false)
                        }
                    }
                    return Observable.from(optional: markers)
                })
                .bind(to: self.airportMarkers)
                .disposed(by: self.disposeBag)
        }
    }
    
    func getErrorForRoute(from originIATA: String?, to destinationIATA: String?) {
        guard let originIATA = originIATA?.uppercased() else { return }
        guard let destinationIATA = destinationIATA?.uppercased() else { return }
        
        let checkOrigin = self.services.mapService.getAirport(forIATA: originIATA).value.iata3.count > 0
        
        let checkDestination = self.services.mapService.getAirport(forIATA: destinationIATA).value.iata3.count > 0
        
        if !checkOrigin {
            self.errorMessage.accept("There's no airport with IATA code \"\(originIATA)\"")
            return
        }
        
        if !checkDestination {
            self.errorMessage.accept("There's no airport with IATA code \"\(destinationIATA)\"")
            return
        }
        
        self.errorMessage.accept("There's no route between \"\(originIATA)\" and \"\(destinationIATA)\"")
    }
}
