//
//  CharacterDetailTableViewCell.swift
//  RickAndMorty
//
//  Created by Ankita Kalangutkar on 11/07/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class CharacterDetailTableViewCell: UITableViewCell {

    @IBOutlet var headerTextLabel: UILabel!
    @IBOutlet var answerLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    func configureCell(_ headerText: String, _ modelValue: String) {
        headerTextLabel.text = headerText
        answerLabel.text = modelValue
    }
    
}
