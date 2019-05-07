//
//  AppFlow.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import RxFlow
import UIKit

class AppFlow: Flow {
    var root: Presentable {
        return self.rootViewController
    }
    
    private  var rootViewController = UIViewController()
    
    private let services: AppServices
    
    init(services: AppServices) {
        self.services = services
    }
    
    deinit {
        print("\(type(of: self)): \(#function)")
    }
    
    func navigate(to step: Step) -> FlowContributors {
        guard let step = step as? AppStep else { return .none }
        
        switch step {
        case .mapIsRequired:
            return self.showMapScreen()
        default: return .none
        }
    }
    
    private func showMapScreen() -> FlowContributors {
        let mapFlow = MapFlow(withServices: self.services)
        
        Flows.whenReady(flow1: mapFlow) { [unowned self] root in
            self.rootViewController.present(root, animated: false)
        }
        
        return .one(flowContributor: .contribute(withNextPresentable: mapFlow,
                                                 withNextStepper: OneStepper(withSingleStep: AppStep.mapIsRequired)))
    }
    
}

