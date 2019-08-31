//
//  CharacterDetail.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import SwiftUI

struct CharacterDetail: View {
    let character: Character
    var body: some View {
        VStack(alignment: .leading) {
            URL(string: character.image).flatMap {
                LoadableImage(url: $0)
            }
            Text(character.status.rawValue)
            Text("Species: \(character.species)")
            Text("Origin: \(character.origin.name)")
            Text("Location: \(character.location.name)")
        }
        .navigationBarTitle(Text(character.name))
    }
}
