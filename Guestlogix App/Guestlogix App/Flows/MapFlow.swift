//
//  MapFlow.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import Reusable
import RxFlow
import UIKit

class MapFlow: Flow {
    var root: Presentable {
        return self.rootViewController
    }
    
    private let rootViewController = UINavigationController()
    private let services: AppServices
    
    init(withServices services: AppServices) {
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
        default:
            return .none
        }
    }
    
    private func showMapScreen() -> FlowContributors {
        let viewController = MapViewController.instantiate(withViewModel: MapViewModel(), andServices: self.services)
        self.rootViewController.pushViewController(viewController, animated: true)
        return .one(flowContributor: .contribute(withNextPresentable: viewController, withNextStepper: viewController.viewModel))
    }
}
