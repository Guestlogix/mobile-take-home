//
//  ViewController.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class EpisodeViewController: UIViewController, ErrorHandlingInUI {

    @IBOutlet weak var episodeListTableView: EpisodeListTableView!
    let episodeListViewModel = EpisodeListViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationItem.title = Constants.Episode.episodeTitle
        episodeListTableView.episodeListDelegate = self
        fetchAllEpisodes()
    }
    
    private func fetchAllEpisodes() {
        let activityIndicator = ActivityIndicator(frame: CGRect.init(x: view.frame.size.width/2, y: view.frame.size.height/2, width: 60, height: 60))
        activityIndicator.start()
        view.addSubview(activityIndicator)
        
        episodeListViewModel.getEpisodes { (status) in
            if status {
                self.episodeListTableView.dataSourceValue = self.episodeListViewModel.responseData?.results ?? [EpisodeResultModel]()
                activityIndicator.stop()
            } else {
//                handle failure
                activityIndicator.stop()
                self.showAlert(title: "", message: Constants.Episode.episodeErrorMessage, viewController: self)
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
        let castIds = episodeListViewModel.getCharacterIds(model.characterURLs ?? [])
        print(castIds)
        
        let castViewController = CastViewController.instantiateFromStoryboard()
        castViewController.castIds = castIds
        self.navigationController?.pushViewController(castViewController, animated: true)
    }
}
