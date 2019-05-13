//
//  UIView+Extension.swift
//  GuestlogixTechnicalTest
//
//  Created by Camila Gaitan on 5/12/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import Foundation
import UIKit
import KRActivityIndicatorView

extension UIView{
    
    func loadingFrame() {
        self.frame = CGRect(x: (UIScreen.main.bounds.width/2) - 30, y: UIScreen.main.bounds.height/2, width: 50, height: 50)
    }
    
}
