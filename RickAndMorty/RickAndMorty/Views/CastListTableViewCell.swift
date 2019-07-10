//
//  CastListTableViewCell.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class CastListTableViewCell: UITableViewCell {

    @IBOutlet weak var characterNameLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    func configureCell(_ model: CharacterModel) {
        characterNameLabel.text = model.characterName ?? ""
    }
    
}
