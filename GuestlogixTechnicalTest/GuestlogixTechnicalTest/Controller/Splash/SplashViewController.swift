//
//  SplashViewController.swift
//  GuestlogixTechnicalTest
//
//  Created by Camila Gaitan on 5/12/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import Foundation
import UIKit

class SplashViewController: UIViewController {
    
    lazy var viewModel: SplashViewModel = {
        return SplashViewModel()
    }()
    
    //MARK: Controller LifeCycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.configureUI()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let formController: FormViewController = segue.destination as! FormViewController
        formController.airports = viewModel.airports
        formController.connections = viewModel.connections
        formController.routes = viewModel.routes
    }
    
    //MARK: Configure UI
    
    func configureUI()  {
        
        viewModel.loadAirports()
        viewModel.loadRoutes()
        self.performSegue(withIdentifier: "showForm", sender: nil)
    }
}
