//
//  AppUtility+UIImage.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import UIKit

extension UIImage {
    func tint(tintColor: UIColor) -> UIImage {
        UIGraphicsBeginImageContextWithOptions(self.size, false, self.scale)
        let context: CGContext = UIGraphicsGetCurrentContext()!
        
        context.translateBy(x: 0, y: self.size.height)
        context.scaleBy(x: 1.0, y: -1.0)
        context.setBlendMode(.normal)
        let rect: CGRect = CGRect(x: 0, y: 0, width: self.size.width, height: self.size.height)
        
        context.clip(to: rect, mask: self.cgImage!)
        
        tintColor.setFill()
        context.fill(rect)
        
        var newImage = UIGraphicsGetImageFromCurrentImageContext()!
        UIGraphicsEndImageContext()
        
        newImage = newImage.withRenderingMode(.alwaysOriginal)
        return newImage
    }
}
