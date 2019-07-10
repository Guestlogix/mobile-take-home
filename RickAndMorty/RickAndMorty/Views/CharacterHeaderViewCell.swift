//
//  CharacterHeaderViewCell.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/11/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class CharacterHeaderViewCell: UITableViewHeaderFooterView {

    @IBOutlet weak var headerNameLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    func configure(_ title: String) {
        headerNameLabel.text = title
    }
}
