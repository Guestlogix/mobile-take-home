//
//  Constants.swift
//  RickAndMorty
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import UIKit

class Constants: NSObject {
    public static let kRequestTimeOut = 60
    public static let kInitialNavigationController = "InitialNavigationController"
    public struct Storyboard {
        public static let mainStoryboard = "Main"
        public static let splashScreenStoryboard = "SplashScreen"
    }
    
    public struct Episode {
        public static let episodeListCell = "EpisodeListTableViewCell"
    }
    
    public struct Character {
        public static let characterListCell = "CastListTableViewCell"
        public static let characterHeaderViewCell = "CharacterHeaderViewCell"
    }
}
