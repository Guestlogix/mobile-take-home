//
//  SplashScreenViewController.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class SplashScreenViewController: UIViewController {
    
    let splashScreenViewModel = SplashScreenViewModel()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        splashScreenViewModel.getAllRequestURLs { (status) in
            if status {
                ServiceUrl.episodeDataURL = self.splashScreenViewModel.responseData?.episodes ?? ""
            }
        }
    }
    
    
    class func instantiateFromStoryboard() -> SplashScreenViewController {
        let storyboard = UIStoryboard.init(name: Constants.Storyboard.splashScreenStoryboard, bundle: nil)
        return storyboard.instantiateViewController(withIdentifier: String(describing: self)) as! SplashScreenViewController
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
