//
//  AppUtility+String.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import Foundation

extension String {
    func trim() -> String {
        return self.trimmingCharacters(in: .whitespacesAndNewlines)
    }

    var URLEscapedString: String {
        return self.addingPercentEncoding(withAllowedCharacters: .urlHostAllowed)!
    }
}
