//
//  FormViewController.swift
//  GuestlogixTechnicalTest
//
//  Created by Camila Gaitan on 5/12/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import Foundation
import UIKit
import SkyFloatingLabelTextField
import RMPickerViewController

class FormViewController: UIViewController {
    
    @IBOutlet weak var from: SkyFloatingLabelTextField!
    @IBOutlet weak var to: SkyFloatingLabelTextField!
    @IBOutlet weak var currentTextfield: UITextField!
    @IBOutlet weak var searchButton: UIButton!
    
    var airports: [Airport] = []
    var connections: [Airport] = []
    var routes: [Route] = []
    
    //MARK: Controller Lifecycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.configureUI()
    }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let controller: MapViewController = segue.destination as! MapViewController
        controller.origin = self.from.text
        controller.destination = self.to.text
        controller.airports = self.airports
        controller.routes = self.routes
    }
    
    //MARK: Configure UI
    
    func configureUI() {
        self.to.delegate = self
        self.from.delegate = self
        searchButton.layer.cornerRadius = 5.0
    }
    
    func openPickerViewController(data: [Any]) {
        
        let selectAction = RMAction<UIPickerView>(title: "Select", style: .done) { controller in
            let selectedRows = NSMutableArray()
            for i in 0 ..< controller.contentView.numberOfComponents {
                selectedRows.add(controller.contentView.selectedRow(inComponent: i));
            }
            let row: Int = selectedRows[0] as! Int
            self.currentTextfield.text = self.airports[row].code
        }
        
        let cancelAction = RMAction<UIPickerView>(title: "Cancel", style: .cancel) { _ in
        }
        
        let actionController = RMPickerViewController(style: RMActionControllerStyle.default, title: "List", message: "Plese select an option from  the list", select: selectAction, andCancel: cancelAction)!;
        actionController.picker.delegate = self;
        actionController.picker.dataSource = self;
        present(actionController, animated: true, completion: nil)
    }
    
    //MARK: Validate Form
    
    func isValidForm() -> Bool{
        
        clearErrorMessages()
        
        if (to.text?.count == 0 || from.text?.count == 0){
            return false
        }
        return true
        
    }
    
    func setErrorMessages(){
        
        for view in self.view.subviews as [UIView] {
            if view is SkyFloatingLabelTextField{
                let component = view as! SkyFloatingLabelTextField
                if component.text?.count == 0 {
                    component.errorMessage = "Please enter a value"
                }
            }
        }
        
    }
    
    func clearErrorMessages(){
        
        for view in self.view.subviews as [UIView] {
            if view is SkyFloatingLabelTextField{
                let component = view as! SkyFloatingLabelTextField
                if component.text?.count != 0 {
                    component.errorMessage = ""
                }
            }
        }
    }
    
    //MARK: Controller Actions
    
    @IBAction func searchAction(_ sender: Any) {
        if isValidForm(){
            self.performSegue(withIdentifier: "showMap", sender: nil)
            return
        }
        setErrorMessages()
    }
    
    

}

extension FormViewController: UIPickerViewDelegate, UIPickerViewDataSource{
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return airports.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return airports[row].code
    }
    
}

extension FormViewController: UITextFieldDelegate{
    
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        currentTextfield = textField as! SkyFloatingLabelTextField
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return false
    }
    
}
