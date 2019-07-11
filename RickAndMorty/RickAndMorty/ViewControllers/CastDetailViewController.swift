//
//  CastDetailViewController.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class CastDetailViewController: UIViewController {
    
    @IBOutlet var castDetailTableView: CharacterDetailTableView!
    @IBOutlet weak var profileImageView: UIImageView!
    var characterModel: CharacterModel?

    override func viewDidLoad() {
        super.viewDidLoad()

        self.navigationItem.title = "Cast Detail"
        
        castDetailTableView.dataSourceValue = characterModel ?? CharacterModel()
    }
    
    private func loadProfileImage() {
        if let profileUrlString = characterModel?.imageURL, let profileUrl = URL(string: profileUrlString) {
            profileImageView.load(url: profileUrl)
            profileImageView.layer.cornerRadius = profileImageView.frame.size.width/2
            profileImageView.clipsToBounds = true
        }
    }
    
    class func instantiateFromStoryboard() -> CastDetailViewController {
        let storyboard = UIStoryboard.init(name: Constants.Storyboard.mainStoryboard, bundle: nil)
        return storyboard.instantiateViewController(withIdentifier: String(describing: self)) as! CastDetailViewController
    }

}
