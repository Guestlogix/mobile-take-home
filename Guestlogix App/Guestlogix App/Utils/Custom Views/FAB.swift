//
//  FAB.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import UIKit

@IBDesignable
class FAB: UIView {
    private let imageView = UIImageView()
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        self.updateView()
    }
    
    public required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
        self.updateView()
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        self.updateView()
    }
    
    @IBInspectable var color: UIColor = .lightGray {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var shadowcolor: UIColor = .black {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var shadowOffset: CGSize = CGSize(width: 2, height: 2) {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var shadowRadius: CGFloat = 3 {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var shadowOpacity: Float = 0.5 {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var image: UIImage? {
        didSet {
            self.updateView()
        }
    }
    
    @IBInspectable var imageWidth: CGFloat = 20 {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var imageHeight: CGFloat = 20 {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var imageTintColor: UIColor = .black {
        didSet {
            updateView()
        }
    }
    
    override class var layerClass: AnyClass {
        return CAGradientLayer.self
    }
    
    private func updateView() {
        isUserInteractionEnabled = true
        backgroundColor = color
        
        if let sublayers = self.layer.sublayers {
            for layer in sublayers {
                layer.removeFromSuperlayer()
            }
        }
        
        let layer = self.layer as! CAGradientLayer
        layer.shadowColor = shadowcolor.cgColor
        layer.shadowOffset = shadowOffset
        layer.shadowRadius = shadowRadius
        layer.shadowOpacity = shadowOpacity
        layer.cornerRadius = self.frame.width / 2
        
        if self.image != nil {
            self.imageView.image = self.image?.withRenderingMode(.alwaysTemplate)
        }
        self.imageView.frame = CGRect(x: (self.frame.width - self.imageWidth) / 2, y: (self.frame.height - self.imageHeight) / 2, width: self.imageWidth, height: self.imageHeight)
        self.imageView.tintColor = imageTintColor
        
        addSubview(self.imageView)
    }
}

