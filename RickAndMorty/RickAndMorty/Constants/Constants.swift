//
//  Constants.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class Constants: NSObject {
    public static let initialScreenErrorMessage = "Could not load the services"
    public static let kRequestTimeOut = 60
    public static let kInitialNavigationController = "InitialNavigationController"
    public struct Storyboard {
        public static let mainStoryboard = "Main"
        public static let splashScreenStoryboard = "SplashScreen"
    }
    
    public struct Episode {
        public static let episodeListCell = "EpisodeListTableViewCell"
        public static let episodeTitle = "Episodes"
        public static let episodeErrorMessage = "Could not load episodes"
    }
    
    public struct Character {
        public static let characterListCell = "CastListTableViewCell"
        public static let characterHeaderViewCell = "CharacterHeaderViewCell"
        public static let charactersTitle = "Casts in Episode"
        public static let charactersErrorMessage = "Could not load the characters"
    }
    
    public struct CharacterDetail {
        public static let characterDetailCell = "CharacterDetailTableViewCell"
        public static let characterDetailTitle = "Cast Detail"
    }
}
