//
//  MapStepper.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import RxCocoa
import RxFlow
import RxSwift

class MapStepper: Stepper {
    let steps = PublishRelay<Step>()
    
    var initialStep: Step {
        return AppStep.mapIsRequired
    }
    
    func mapIsRequired() {
        self.steps.accept(AppStep.mapIsRequired)
    }
}

