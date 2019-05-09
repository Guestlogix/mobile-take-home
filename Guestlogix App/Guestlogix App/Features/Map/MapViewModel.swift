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
    
    func showSearchView() {
        steps.accept(AppStep.searchIsRequired)
    }
    
}
