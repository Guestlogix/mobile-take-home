//
//  CastListTableView.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

protocol CastListTableViewDelegate: class {
    func didSelectCharacter(_ model: CharacterModel)
}

class CastListTableView: UITableView {

    var dataSourceValue = CharacterSegregationModel() {
        didSet {
            self.reloadData()
        }
    }
    
    weak var castListTableViewDelegate: CastListTableViewDelegate?

    override func awakeFromNib() {
        super.awakeFromNib()
        self.register(UINib(nibName: Constants.Character.characterListCell, bundle: nil), forCellReuseIdentifier: Constants.Character.characterListCell)
        self.register(UINib(nibName: Constants.Character.characterHeaderViewCell, bundle: nil), forHeaderFooterViewReuseIdentifier: Constants.Character.characterHeaderViewCell)
        
        self.delegate = self
        self.dataSource = self
        
        self.contentInset = UIEdgeInsets(top: -1, left: 0, bottom: 0, right: 0)
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
        if dataSourceValue.livingCharacters == nil && dataSourceValue.deadCharacters == nil {
            return 0
        }
        return 30
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
        if indexPath.section == 0 {
            castListTableViewDelegate?.didSelectCharacter(dataSourceValue.livingCharacters?[indexPath.row] ?? CharacterModel())
        } else {
            castListTableViewDelegate?.didSelectCharacter(dataSourceValue.deadCharacters?[indexPath.row] ?? CharacterModel())
        }
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
            if let data = dataSourceValue.livingCharacters?[indexPath.row] {
                cell.configureCell(data)
            }
        } else {
            if let data = dataSourceValue.deadCharacters?[indexPath.row] {
                cell.configureCell(data)
            }
        }
        
        cell.selectionStyle = .none
        return cell
    }
    
}
