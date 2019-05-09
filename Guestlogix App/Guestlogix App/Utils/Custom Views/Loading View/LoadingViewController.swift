//
//  LoadingViewController.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 09/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import UIKit

class LoadingViewController: UIViewController {
    @IBOutlet var displayMessageLabel: UILabel!
    var displayMessage: String!
    override func viewDidLoad() {
        super.viewDidLoad()
        modalPresentationStyle = .overCurrentContext
        view.backgroundColor = UIColor("#000000", alpha: 0.6)
        displayMessageLabel.text = displayMessage
    }
}
