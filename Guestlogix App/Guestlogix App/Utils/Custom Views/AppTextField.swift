//
//  AppTextField.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import UIKit

@IBDesignable
class AppTextField: UIView {
    private let shell = UIView()
    private let shellPadding = CGFloat(10)
    private let imageView = UIImageView()
    public let element = UITextField()
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        updateView()
    }
    
    public required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
        updateView()
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        let width = frame.width
        let height = frame.height
        let xMargin = height / 2.5
        
        shell.frame = CGRect(x: 0, y: 0, width: width, height: height)
        imageView.frame = CGRect(x: xMargin, y: (shell.frame.height - imageHeight) / 2, width: imageWidth, height: imageHeight)
        
        let elementMargin = hasImage ? imageView.frame.maxX + 10 : xMargin
        let elementWidth = width - elementMargin - xMargin
        element.frame = CGRect(x: elementMargin, y: shellPadding, width: elementWidth, height: height - (shellPadding * 2))
    }
    
    @IBInspectable var placeHolderText: String = "placeHolderText" {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var textColor: UIColor = .black {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var fontSize: CGFloat = 15 {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var backGroundColor: UIColor = .white {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var hasImage: Bool = false {
        didSet {
            updateView()
        }
    }
    
    @IBInspectable var image: UIImage? {
        didSet {
            updateView()
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
    
    override class var layerClass: AnyClass {
        return CAGradientLayer.self
    }
    
    private func updateView() {
        if let sublayers = layer.sublayers {
            for layer in sublayers {
                layer.removeFromSuperlayer()
            }
        }
        
        let width = frame.width
        let height = frame.height
        let xMargin = height / 2.5
        
        // ****************************************************************** //
        shell.frame = CGRect(x: 0, y: 0, width: width, height: height)
        shell.layer.borderWidth = CGFloat(1.5)
        shell.layer.borderColor = UIColor("#979797").cgColor
        shell.layer.cornerRadius = shell.frame.height / 2
        shell.backgroundColor = backGroundColor
        addSubview(shell)
        
        // ****************************************************************** //
        if image != nil {
            imageView.image = image?.withRenderingMode(.alwaysTemplate)
            imageView.tint(color: "#969696")
        }
        
        imageView.removeFromSuperview()
        if hasImage {
            shell.addSubview(imageView)
        }
        
        imageView.frame = CGRect(x: xMargin, y: (height - imageHeight) / 2, width: imageWidth, height: imageHeight)
        
        // ****************************************************************** //
        let elementMargin = hasImage ? imageView.frame.maxX + 10 : xMargin
        let elementWidth = width - elementMargin - xMargin
        element.frame = CGRect(x: elementMargin, y: shellPadding, width: elementWidth, height: height - (shellPadding * 2))
        element.placeholder = placeHolderText
        element.attributedPlaceholder = NSAttributedString(string: placeHolderText, attributes: [NSAttributedString.Key.foregroundColor: UIColor("#303030")])
        element.textColor = textColor
        element.font = .systemFont(ofSize: fontSize)
        addSubview(element)
    }
}

