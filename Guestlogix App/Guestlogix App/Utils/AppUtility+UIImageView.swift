//
//  AppUtility+UIImageView.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import UIKit

extension UIImageView {
    func tint(color: UIColor) {
        self.image = self.image!.withRenderingMode(.alwaysTemplate)
        self.tintColor = color
    }
    func tint(color: String) {
        self.image = self.image!.withRenderingMode(.alwaysTemplate)
        self.tintColor = UIColor(color)
    }
}
