//
//  MapViewController.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import Reusable
import RxCocoa
import RxFlow
import RxSwift
import UIKit

class MapViewController: UIViewController, StoryboardBased, ViewModelBased {
    internal var viewModel: MapViewModel!
    private let disposeBag = DisposeBag()

    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
