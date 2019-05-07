//
//  CardView.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import UIKit

@IBDesignable
class CardView: UIView {
    @IBInspectable var cornerRadius: CGFloat = 10
    @IBInspectable var shadowRadius: CGFloat = 3
    @IBInspectable var shadowOffsetWidth: Int = 0
    @IBInspectable var shadowOffsetHeight: Int = 3
    @IBInspectable var shadowColour: UIColor? = UIColor.black
    @IBInspectable var shadowOpacity: Float = 0.5
    @IBInspectable var masksToBounds: Bool = false
    
    override func layoutSubviews() {
        layer.cornerRadius = cornerRadius
        let shadowPath = UIBezierPath(roundedRect: bounds, cornerRadius: cornerRadius)
        
        layer.masksToBounds = masksToBounds
        layer.shadowColor = shadowColour?.cgColor
        layer.shadowOffset = CGSize(width: shadowOffsetWidth, height: shadowOffsetHeight)
        layer.shadowOpacity = shadowOpacity
        layer.shadowPath = shadowPath.cgPath
        layer.shadowRadius = shadowRadius
    }
}
