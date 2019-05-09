//
//  AppUtility.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import UIKit

class AppUtility {
    func showLoading(displayMessage: String, tag: Int) {
        let loadingViewController = R.storyboard.loadingViewController.loadingViewController()
        loadingViewController?.displayMessage = displayMessage
        let loadingView = loadingViewController?.view!
        loadingView?.tag = tag
        UIApplication.shared.keyWindow?.addSubview(loadingView!)
    }
    
    func hideLoading(tag: Int) {
        let windowSubviews = (UIApplication.shared.keyWindow?.subviews)!
        for subview in windowSubviews {
            if subview.tag == tag {
                subview.removeFromSuperview()
                break
            }
        }
    }
    
    private func basicAlertMessage(for viewController: UIViewController, with message: String) {
        let alert = UIAlertController(title: nil, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        viewController.present(alert, animated: true, completion: nil)
    }
    
    func showErrorAlertMessage(for viewController: UIViewController, with message: String) {
        basicAlertMessage(for: viewController, with: message)
    }
}
