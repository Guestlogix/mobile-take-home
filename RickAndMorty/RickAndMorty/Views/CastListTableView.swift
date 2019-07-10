//
//  CastListTableView.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class CastListTableView: UITableView {

    var dataSourceValue = CharacterSegregationModel() {
        didSet {
            self.reloadData()
        }
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        self.register(UINib(nibName: Constants.Character.characterListCell, bundle: nil), forCellReuseIdentifier: Constants.Character.characterListCell)
        self.register(UINib(nibName: Constants.Character.characterHeaderViewCell, bundle: nil), forHeaderFooterViewReuseIdentifier: Constants.Character.characterHeaderViewCell)
        
        self.delegate = self
        self.dataSource = self
    }
    
}


extension CastListTableView: UITableViewDelegate {
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableView.automaticDimension
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 30.0
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = tableView.dequeueReusableHeaderFooterView(withIdentifier: Constants.Character.characterHeaderViewCell) as! CharacterHeaderViewCell
        if section == 0 {
            headerView.configure("Alive")
        } else {
            headerView.configure("Dead")
        }
        return headerView
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
//        episodeListDelegate?.didSelectEpisode(dataSourceValue[indexPath.row])
    }
}

extension CastListTableView: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0 {
            return dataSourceValue.livingCharacters?.count ?? 0
        } else {
            return dataSourceValue.deadCharacters?.count ?? 0
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: Constants.Character.characterListCell, for: indexPath) as! CastListTableViewCell
        if indexPath.section == 0 {
            cell.configureCell(dataSourceValue.livingCharacters?[indexPath.row] ?? CharacterModel())
        } else {
            cell.configureCell(dataSourceValue.deadCharacters?[indexPath.row] ?? CharacterModel())
        }
        
        cell.selectionStyle = .none
        return cell
    }
    
}
