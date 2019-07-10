//
//  ViewController.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class EpisodeViewController: UIViewController {

    @IBOutlet weak var episodeListTableView: EpisodeListTableView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        episodeListTableView.episodeListDelegate = self
    }

    class func instantiateFromStoryboard() -> EpisodeViewController {
        let storyboard = UIStoryboard.init(name: Constants.Storyboard.mainStoryboard, bundle: nil)
        return storyboard.instantiateViewController(withIdentifier: String(describing: self)) as! EpisodeViewController
    }
}


extension EpisodeViewController: EpisodeListDelegate {
    func didSelectEpisode(_ model: EpisodeModel) {
        
    }
}
