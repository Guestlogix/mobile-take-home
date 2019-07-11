//
//  CharacterDetailTableView.swift
//  RickAndMorty
//
//  Created by Ankita Kalangutkar on 11/07/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class CharacterDetailTableView: UITableView {

    var dataSourceValue = CharacterModel() {
        didSet {
            self.reloadData()
        }
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        self.register(UINib(nibName: Constants.CharacterDetail.characterDetailCell, bundle: nil), forCellReuseIdentifier: Constants.CharacterDetail.characterDetailCell)
        
        self.delegate = self
        self.dataSource = self
        
        self.separatorColor = UIColor.clear
    }
}


extension CharacterDetailTableView: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 75
    }
    
//    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
//        episodeListDelegate?.didSelectEpisode(dataSourceValue[indexPath.row])
//    }
}

extension CharacterDetailTableView: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 4
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: Constants.CharacterDetail.characterDetailCell, for: indexPath) as! CharacterDetailTableViewCell
        switch indexPath.row {
        case 0:
            cell.configureCell("Name:", dataSourceValue.characterName ?? "")
        case 1:
            cell.configureCell("Gender:", dataSourceValue.gender ?? "")
        case 2:
            cell.configureCell("Status:", dataSourceValue.status ?? "")
        case 3:
            cell.configureCell("Species:", dataSourceValue.species ?? "")
        default:
            break
        }
        
        cell.selectionStyle = .none
        return cell
    }
    
}
