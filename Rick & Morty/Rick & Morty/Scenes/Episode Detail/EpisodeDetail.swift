//
//  EpisodeDetail.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import SwiftUI

struct EpisodeDetail: View {
    let episode: Episode
    @State private var characters: [Character] = []
    
    var body: some View {
        List(characters) { character in
            NavigationLink(destination: CharacterDetail(character: character)) {
                EpisodeDetailRow(character: character)
            }
        }
        .onAppear() {
            self.loadCharacters()
        }
        .navigationBarTitle(Text(episode.name))
    }
    
    private func loadCharacters() {
        episode.characters.forEach {
            App.networking.request(CharacterRequest(url: $0)) { result in
                if case let .success(character) = result {
                    self.characters.append(character)
                }
            }
        }
    }
}

struct EpisodeDetailRow: View {
    let character: Character
    var body: some View {
        HStack {
            URL(string: character.image).flatMap {
                LoadableImage(url: $0)
                    .frame(width: 50, height: 50)
            }
            VStack(alignment: .leading) {
                Text(character.name)
                    .foregroundColor(character.status == .alive ? .black : .gray)
                Text(character.status.rawValue)
                    .font(.footnote)
                    .foregroundColor(character.status == .alive ? .black : .gray)
            }
        }
    }
}
