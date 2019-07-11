//
//  CastViewController.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

/// Character listing screen
class CharacterViewController: UIViewController, ErrorHandlingInUI {

    @IBOutlet weak var castListTableView: CastListTableView!
    var charactersViewModel = CharactersViewModel()
    
    var castIds: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = Constants.Character.charactersTitle
        self.navigationItem.backBarButtonItem = UIBarButtonItem(title:"", style:.plain, target:nil, action:nil)
        castListTableView.castListTableViewDelegate = self
        fetchCasts(castIds ?? "")
    }
    
    /// Api call for fetching all the characters associated with the episode
    ///
    /// - Parameter castIds: character IDs
    func fetchCasts(_ castIds: String) {
        let activityIndicator = ActivityIndicator(frame: CGRect.init(x: view.center.x, y: view.center.y, width: 60, height: 60))
        activityIndicator.start()
        view.addSubview(activityIndicator)
        
        charactersViewModel.fetchCharactersFor(castIds) { (status) in
            if status {
                self.castListTableView.dataSourceValue = self.charactersViewModel.finalResponse ?? CharacterSegregationModel()
                activityIndicator.stop()
            } else {
//                 handle
                activityIndicator.stop()
                self.showAlert(title: "", message: Constants.Character.charactersErrorMessage, viewController: self)
            }
        }
    }
    
    /// Insantiate the class
    ///
    /// - Returns: self
    class func instantiateFromStoryboard() -> CharacterViewController {
        let storyboard = UIStoryboard.init(name: Constants.Storyboard.mainStoryboard, bundle: nil)
        return storyboard.instantiateViewController(withIdentifier: String(describing: self)) as! CharacterViewController
    }

}


// MARK: - CastListTableViewDelegate
extension CharacterViewController: CastListTableViewDelegate {
    func didSelectCharacter(_ model: CharacterModel) {
        let characterDetailViewController = CharacterDetailViewController.instantiateFromStoryboard()
        characterDetailViewController.characterModel = model
        self.navigationController?.pushViewController(characterDetailViewController, animated: true)
    }
}
