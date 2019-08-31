//
//  LoadableImage.swift
//  Rick & Morty
//
//  Created by Bushra Shahid on 8/31/19.
//  Copyright © 2019 Bushra Shahid. All rights reserved.
//

import SwiftUI

struct LoadableImage: View {
    let url: URL
    @State private var image = Image("")
    
    var body: some View {
        image
            .resizable()
            .onAppear {
                App.networking.data(self.url) { result in
                    if case let .success(data) = result,
                        let uiImage = UIImage(data: data) {
                        self.image = Image(uiImage: uiImage)
                    }
                }
        }
    }
}
