//
//  AirportSearchViewController.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 09/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import Reusable
import RxCocoa
import RxFlow
import RxGesture
import RxSwift
import UIKit

class AirportSearchViewController: UIViewController, StoryboardBased, ViewModelBased, LoadingView {
    internal var viewModel: AirportSearchViewModel!
    private let disposeBag = DisposeBag()
    @IBOutlet var sourceTextField: AppTextField!
    @IBOutlet var destinationTextField: AppTextField!
    @IBOutlet var showRouteButton: AppButton!
    @IBOutlet var cancelButton: AppCancelButton!
    private let appUtility = AppUtility()
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        navigationController?.isNavigationBarHidden = true
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(true)
        resetFormFields()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        sourceTextField
            .element
            .rx
            .text.map { $0 ?? "" }
            .bind(to: viewModel.sourceTextFieldString)
            .disposed(by: disposeBag)
        
        destinationTextField
            .element
            .rx
            .text
            .map { $0 ?? "" }
            .bind(to: viewModel.destinationTextFieldString)
            .disposed(by: disposeBag)
        
        viewModel
            .showRouteButtonIsActive
            .asObservable()
            .subscribe(onNext: { buttonIsActive in
                self.showRouteButton.isActive = buttonIsActive
            }, onError: { error in
                print(error.localizedDescription)
            }, onCompleted: {
            }) {
            }.disposed(by: disposeBag)
        
        viewModel
            .isLoading
            .asObservable()
            .subscribe(onNext: { isLoading in
                if isLoading {
                    self.showLoading(displayMessage: "Searching for shortest route...")
                } else {
                    self.hideLoading()
                }
            }, onError: { error in
                print(error.localizedDescription)
            }, onCompleted: {
            }) {
            }.disposed(by: disposeBag)
        
        viewModel
            .airportMarkers
            .skip(1)
            .subscribe(onNext: { markers in
                if markers.count > 0 {
                    DispatchQueue.main.async { [weak self] in
                        self!.cancelSearch()
                        (self!.navigationController?.viewControllers[0] as! MapViewController).drawMarkers(markers: markers)
                    }
                }
            }, onError: { error in
                print(error.localizedDescription)
            }, onCompleted: {
            }) {
            }.disposed(by: disposeBag)
        
        viewModel
            .errorMessage
            .skip(1)
            .subscribe(onNext: { message in
                self.showFeedback(feedbackMessage: message)
            }, onError: { error in
                print(error.localizedDescription)
            }, onCompleted: {
            }) {
            }.disposed(by: disposeBag)
        
        showRouteButton
            .rx
            .tapGesture()
            .when(.recognized)
            .subscribe(onNext: { _ in
                if self.showRouteButton.isActive {
                    self.view.endEditing(true)
                    self.viewModel.getAirportMarkers(from: self.sourceTextField.element.text, to: self.destinationTextField.element.text)
                }
            }, onError: { error in
                print(error.localizedDescription)
            }, onCompleted: {
            }) {
            }.disposed(by: disposeBag)
        
        cancelButton
            .rx
            .tapGesture()
            .when(.recognized)
            .subscribe(onNext: { _ in
                self.cancelSearch()
            }, onError: { error in
                print(error.localizedDescription)
            }, onCompleted: {
            }) {
            }.disposed(by: disposeBag)
    }
    
    private func resetFormFields() {
        showRouteButton.isActive = false
        sourceTextField.element.text = ""
        destinationTextField.element.text = ""
    }
    
    func cancelSearch() {
        navigationController?.popViewController(animated: true)
    }
    
    func showFeedback(feedbackMessage: String) {
        appUtility.showErrorAlertMessage(for: self, with: feedbackMessage)
    }
    
    func showLoading(displayMessage: String) {
        appUtility.showLoading(displayMessage: displayMessage, tag: hashValue)
    }
    
    func hideLoading() {
        appUtility.hideLoading(tag: hashValue)
    }
}
