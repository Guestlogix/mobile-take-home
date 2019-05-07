//
//  AppUtility+Color.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import UIKit

extension UIColor {
    static let primary = UIColor("#009dd5")
    static let primaryDark = UIColor("#008BCA")
    static let primaryDisabled = primary.withAlphaComponent(0.5)
    static let accent = UIColor("#f99100")
    
    convenience init(_ hexColorCode: String, alpha: CGFloat = 1) {
        var trimmedHexColorCode = hexColorCode.trim().uppercased()
        
        if trimmedHexColorCode.hasPrefix("#") {
            trimmedHexColorCode = String(trimmedHexColorCode.suffix(from: trimmedHexColorCode.index(trimmedHexColorCode.startIndex, offsetBy: 1)))
        }
        
        var rgbValue: UInt32 = 0
        Scanner(string: trimmedHexColorCode).scanHexInt32(&rgbValue)
        
        let components = (
            R: CGFloat((rgbValue & 0xFF0000) >> 16) / 255,
            G: CGFloat((rgbValue & 0x00FF00) >> 8) / 255,
            B: CGFloat(rgbValue & 0x0000FF) / 255
        )
        self.init(red: components.R, green: components.G, blue: components.B, alpha: alpha)
    }
}
