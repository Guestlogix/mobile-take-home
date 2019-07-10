//
//  EpisodeListTableView.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

protocol EpisodeListDelegate: class {
    func didSelectEpisode(_ model: EpisodeResultModel)
}

class EpisodeListTableView: UITableView {

    var dataSourceValue = [EpisodeResultModel]() {
        didSet {
            self.reloadData()
        }
    }

    weak var episodeListDelegate: EpisodeListDelegate?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        self.register(UINib(nibName: Constants.Episode.episodeListCell, bundle: nil), forCellReuseIdentifier: Constants.Episode.episodeListCell)
        
        self.delegate = self
        self.dataSource = self
    }
}

extension EpisodeListTableView: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableView.automaticDimension
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        episodeListDelegate?.didSelectEpisode(dataSourceValue[indexPath.row])
    }
}

extension EpisodeListTableView: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataSourceValue.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: Constants.Episode.episodeListCell, for: indexPath) as! EpisodeListTableViewCell
        cell.configureCell(dataSourceValue[indexPath.row])
        cell.selectionStyle = .none
        return cell
    }
    
}
