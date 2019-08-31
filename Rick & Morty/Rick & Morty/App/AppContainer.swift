//
//  AppContainer.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import Foundation

let App = AppContainer()

struct AppContainer {
    let networking: NetworkingProtocol = Networking()
}
