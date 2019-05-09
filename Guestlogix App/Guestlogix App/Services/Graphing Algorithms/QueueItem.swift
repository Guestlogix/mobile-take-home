//
//  QueueItem.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 08/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import Foundation

private class QueueItem<Element: Equatable>: Comparable {
    let value: Element
    var priority: Double
    
    init(_ value: Element, priority: Double) {
        self.value = value
        self.priority = priority
    }
    
    static func < (lhs: QueueItem<Element>, rhs: QueueItem<Element>) -> Bool {
        return lhs.priority < rhs.priority
    }
    
    static func == (lhs: QueueItem<Element>, rhs: QueueItem<Element>) -> Bool {
        return lhs.priority == rhs.priority
    }
}
