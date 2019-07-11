//
//  SplashScreenViewController.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

/// This is the initial screen for loading all the URL before going to Episodes and Characters
class SplashScreenViewController: UIViewController, ErrorHandlingInUI {

    let splashScreenViewModel = SplashScreenViewModel()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        fetchAllURLs()
    }
    
    /// Fetch all the urls, such as for downloading episode and characters
    private func fetchAllURLs() {
        let activityIndicator = ActivityIndicator(frame: CGRect.init(x: view.center.x, y: view.center.y, width: 60, height: 60))
        activityIndicator.start()
        view.addSubview(activityIndicator)
        
        splashScreenViewModel.getAllRequestURLs { (status) in
            if status {
                ServiceUrl.episodeDataURL = self.splashScreenViewModel.responseData?.episodes ?? ""
                ServiceUrl.characterDataURL = self.splashScreenViewModel.responseData?.characters ?? ""
                activityIndicator.stop()
//                 navigate
                self.navigateToEpisodeList()
            } else {
                activityIndicator.stop()
                self.showAlert(title: "", message: Constants.initialScreenErrorMessage, viewController: self)
            }
        }
    }
    
    /// Changing a root view controller
    private func navigateToEpisodeList() {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        let storyboard = UIStoryboard(name: Constants.Storyboard.mainStoryboard, bundle: nil)
        let navigationViewController = storyboard.instantiateViewController(withIdentifier: Constants.kInitialNavigationController) as! UINavigationController
        UIView.transition(with: appDelegate.window!, duration: 0.5, options: .transitionCrossDissolve, animations: {
            appDelegate.window?.rootViewController = navigationViewController
        }, completion: nil)
    }
    
    /// Insantiate the class
    ///
    /// - Returns: self
    class func instantiateFromStoryboard() -> SplashScreenViewController {
        let storyboard = UIStoryboard.init(name: Constants.Storyboard.splashScreenStoryboard, bundle: nil)
        return storyboard.instantiateViewController(withIdentifier: String(describing: self)) as! SplashScreenViewController
    }

}
