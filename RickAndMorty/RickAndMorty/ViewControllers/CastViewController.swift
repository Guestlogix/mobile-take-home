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
        castListTableView.castListTableViewDelegate = self
        fetchCasts(castIds ?? "")
    }
    
    private func fetchCasts(_ castIds: String) {
        charactersViewModel.getEpisodes(castIds) { (status) in
            if status {
                
            } else {
//                TODO handle
            }
        }
    }
    
    class func instantiateFromStoryboard() -> CastViewController {
        let storyboard = UIStoryboard.init(name: Constants.Storyboard.mainStoryboard, bundle: nil)
        return storyboard.instantiateViewController(withIdentifier: String(describing: self)) as! CastViewController
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}


extension CastViewController: CastListTableViewDelegate {
    func didSelectCharacter(_ model: CharacterModel) {
        let characterDetailViewController = CastDetailViewController.instantiateFromStoryboard()
        characterDetailViewController.characterModel = model
        self.navigationController?.pushViewController(characterDetailViewController, animated: true)
    }
}
