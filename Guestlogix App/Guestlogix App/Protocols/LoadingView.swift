//
//  LoadingView.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 09/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import UIKit

protocol LoadingView: class {
    func showFeedback(feedbackMessage: String)
    func showLoading(displayMessage: String)
    func hideLoading()
}
