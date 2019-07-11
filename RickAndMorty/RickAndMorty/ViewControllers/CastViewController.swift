//
//  CastViewController.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class CastViewController: UIViewController {

    @IBOutlet weak var castListTableView: CastListTableView!
    let charactersViewModel = CharactersViewModel()
    
    var castIds: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "Casts in Episode"
        
        castListTableView.castListTableViewDelegate = self
        fetchCasts(castIds ?? "")
    }
    
    private func fetchCasts(_ castIds: String) {
        let activityIndicator = ActivityIndicator(frame: CGRect.init(x: view.frame.size.width/2, y: view.frame.size.height/2, width: 60, height: 60))
        activityIndicator.start()
        view.addSubview(activityIndicator)
        
        charactersViewModel.getCharactersFor(castIds) { (status) in
            if status {
                self.castListTableView.dataSourceValue = self.charactersViewModel.finalResponse ?? CharacterSegregationModel()
                activityIndicator.stop()
            } else {
//                TODO handle
            }
        }
    }
    
    class func instantiateFromStoryboard() -> CastViewController {
        let storyboard = UIStoryboard.init(name: Constants.Storyboard.mainStoryboard, bundle: nil)
        return storyboard.instantiateViewController(withIdentifier: String(describing: self)) as! CastViewController
    }

}


extension CastViewController: CastListTableViewDelegate {
    func didSelectCharacter(_ model: CharacterModel) {
        let characterDetailViewController = CastDetailViewController.instantiateFromStoryboard()
        characterDetailViewController.characterModel = model
        self.navigationController?.pushViewController(characterDetailViewController, animated: true)
    }
}
