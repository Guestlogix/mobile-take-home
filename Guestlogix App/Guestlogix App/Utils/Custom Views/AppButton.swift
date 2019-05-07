//
//  AppButton.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import UIKit

@IBDesignable
public class AppButton: UIView {
    private let buttonLabel = UILabel()
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        updateView()
    }
    
    public required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
        updateView()
    }
    
    public override func layoutSubviews() {
        super.layoutSubviews()
        updateView()
    }
    
    @IBInspectable public var isActive: Bool = true {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable public var title: String = "BUTTON TEXT" {
        didSet {
            updateView()
        }
    }
    
    public override class var layerClass: AnyClass {
        return CAGradientLayer.self
    }
    
    private func updateView() {
        isUserInteractionEnabled = true
        
        if let sublayers = self.layer.sublayers {
            for layer in sublayers {
                layer.removeFromSuperlayer()
            }
        }
        
        let layer = self.layer as! CAGradientLayer
        layer.cornerRadius = frame.height / 2
        layer.colors = isActive ?
            [UIColor("#37490e").cgColor, UIColor("#607335").cgColor, UIColor("#758648").cgColor, UIColor("#899862").cgColor, UIColor("#8fa15f").cgColor]
            :
            [UIColor("#a2a1a1").cgColor, UIColor("#939393").cgColor, UIColor("#9a9a9a").cgColor, UIColor("#b1b1b1").cgColor, UIColor("#d6d6d6").cgColor]
        layer.locations = [0, 0.54, 0.68, 0.86, 1]
        
        layer.shadowColor = UIColor.black.cgColor
        layer.shadowOffset = CGSize(width: 2, height: 2)
        layer.shadowRadius = 4
        layer.shadowOpacity = 0.5
        
        buttonLabel.frame = CGRect(x: 0, y: 0, width: frame.width, height: frame.height)
        buttonLabel.text = title
        buttonLabel.textColor = .white
        buttonLabel.font = .boldSystemFont(ofSize: 17)
        buttonLabel.textAlignment = .center
        
        addSubview(buttonLabel)
    }
}

