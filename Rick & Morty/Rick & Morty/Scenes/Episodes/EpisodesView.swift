//
//  EpisodesView.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import SwiftUI

struct EpisodesView: View {
    @State private var error: String? = nil
    @State private var episodes: [Episode] = []
    
    var body: some View {
        NavigationView {
            VStack {
                error.flatMap { Text($0) }
                List(episodes) { episode in
                    NavigationLink(destination: EpisodeDetail(episode: episode)) {
                        EpisodeRow(episode: episode)
                    }
                }.onAppear() {
                    self.loadEpisodes()
                }
            }
            .navigationBarTitle(Text("Rick & Morty"))
        }
    }
    
    private func loadEpisodes() {
        let request = EpisodesRequest()
        App.networking.request(request) {
            result in
                        
            switch result {
            case let .success(episodes):
                self.episodes = episodes.results
            case .failure(_):
                self.error = "Sorry, the episodes could not be loaded"
            }
        }
    }
}

struct EpisodeRow: View {
    let episode: Episode
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(episode.episode)
                
                Text(episode.airDate)
                    .font(.footnote)
                    .foregroundColor(.gray)
                
                Text(episode.name)
            }
        }
    }
}
