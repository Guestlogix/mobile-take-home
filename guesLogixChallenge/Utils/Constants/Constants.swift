//
//  Constants.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-23.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import Foundation
import UIKit


//MARK: - File Information

enum FILE {
    static let AIRPORTS = "airports"
    static let AIRLINES = "airlines"
    static let ROUTES = "routes"
}

//MARK: - Alert Button Name
enum AlertButtonName {
    static let OK = "Ok"
}

//MARK: - Alert Message Title
enum AlertMessageTitle {
    static let ERROR = "Error"
}

//MARK: - Alert Message Text
enum AlertMessageText {
    static let BAD_ORIGIN = "Origin airport can't be empty and it has to be 3 letters long. Please try again"
    static let BAD_DESTINATION = "Destination airport can't be empty and it has to be 3 letters long. Please try again"
    static let EMPTY_INPUTS = "Fields can't be empty please write origin and destination codes"
    static let NO_ORIGIN = "Origin airport not found"
    static let NO_DESTINATION = "Destination airport not found"
    static let SAME_DESTINATIONS = "Origin and Destination can't be the same.Please change one and try again"
    static let NO_ROUTE = "We could not find a suitable route for these options. Please try again with different destinations"
}

//MARK: Segues
enum SEGUES {
    static let TO_MAP = "goToMap"
}

//MARK: Colors

enum COLORS {
    static let PRIMARY = #colorLiteral(red: 0, green: 0.6960706115, blue: 0.8644302487, alpha: 1)
    static let SECONDARY = #colorLiteral(red: 1, green: 1, blue: 1, alpha: 1)
}
