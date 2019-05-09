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
        
        backgroundColor =  isActive ? .primary : .primaryDisabled
        
        buttonLabel.frame = CGRect(x: 0, y: 0, width: frame.width, height: frame.height)
        buttonLabel.text = title
        buttonLabel.textColor = .white
        buttonLabel.font = .boldSystemFont(ofSize: 17)
        buttonLabel.textAlignment = .center
        
        addSubview(buttonLabel)
    }
}

