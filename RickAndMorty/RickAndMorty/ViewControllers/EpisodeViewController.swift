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
    let episodeListViewModel = EpisodeListViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        episodeListTableView.episodeListDelegate = self
    }
    
    private func fetchAllEpisodes() {
        episodeListViewModel.getEpisodes { (status) in
            if status {
                self.episodeListTableView.dataSourceValue = self.episodeListViewModel.responseData?.results ?? [EpisodeResultModel]()
            } else {
//                TODO - handle
            }
        }
    }

    class func instantiateFromStoryboard() -> EpisodeViewController {
        let storyboard = UIStoryboard.init(name: Constants.Storyboard.mainStoryboard, bundle: nil)
        return storyboard.instantiateViewController(withIdentifier: String(describing: self)) as! EpisodeViewController
    }
    
}


extension EpisodeViewController: EpisodeListDelegate {
    func didSelectEpisode(_ model: EpisodeResultModel) {
        let castIds = episodeListViewModel.getCharacterIds(model.characters ?? [])
        print(castIds)
        
        let castViewController = CastViewController.instantiateFromStoryboard()
        castViewController.castIds = castIds
        self.navigationController?.pushViewController(castViewController, animated: true)
    }
}
