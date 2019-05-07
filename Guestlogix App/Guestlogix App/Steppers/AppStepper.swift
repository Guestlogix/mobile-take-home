//
//  AppStepper.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import RxCocoa
import RxFlow
import RxSwift

class AppStepper: Stepper {
    let steps = PublishRelay<Step>()
    private let appServices: AppServices
    private let disposeBag = DisposeBag()
    
    init(withServices services: AppServices) {
        self.appServices = services
    }
    
    var initialStep: Step {
        return AppStep.mapIsRequired
    }
    
    /// callback used to emit steps once the FlowCoordinator is ready to listen to them to contribute to the Flow
    func readyToEmitSteps() {
        Observable.from(optional: AppStep.mapIsRequired)
            .bind(to: self.steps)
            .disposed(by: self.disposeBag)
    }
}


