//
//  ViewController.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-23.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import UIKit
import Mapbox

class AirportSelectionViewController: UIViewController {
    
    
    @IBOutlet weak var destinationTextField: UITextField!
    @IBOutlet weak var originTextField: UITextField!
    
    
    var routes = AdjacencyList<Airport>()
    var route = [Edge<Airport>]()
    var airports = [String:Airport]()
    var airlines = [String:Airline]()

    
    override func viewDidLoad() {
        super.viewDidLoad()
        styleNavigationBar()
        // Do any additional setup after loading the view.
        destinationTextField.delegate = self
        destinationTextField.autocapitalizationType = .allCharacters
        originTextField.delegate = self
        originTextField.autocapitalizationType = .allCharacters
        loadData()
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target:self, action: #selector(dismissKeyboard))
        view.addGestureRecognizer(tap)
    }
    
    
    @objc func dismissKeyboard() {
        view.endEditing(true)
    }
    
    func loadData () {
        //Load data from files
        if let airlinesDict = AirlineParser.parseData(filename: FILE.AIRLINES),
            let airportsDict = AirportParser.parseData(filename: FILE.AIRPORTS),
            let routesGraph = RouteParser.parseData(airlines: airlinesDict, airports: airportsDict, filename: FILE.ROUTES) {
            airlines = airlinesDict
            airports = airportsDict
            routes  = routesGraph
        } else {
            print ("There was a problem loading the data")
        }
    }
    
    //MARK: Buttons
    @IBAction func goTapped(_ sender: UIButton) {
        
        //Check if both inputs are empty
        if originTextField.text == nil && destinationTextField.text == nil {
            
            AlertUtil.showAlert(in: self, msg: AlertMessageText.EMPTY_INPUTS, title: AlertMessageTitle.ERROR)
            
        } else {
            //Check that origin is a valid entry
            guard let originIATA = originTextField.text, originIATA.count == 3 else {
                AlertUtil.showAlert(in: self, msg:AlertMessageText.BAD_ORIGIN , title: AlertMessageTitle.ERROR)
                return
            }
            //Check that destination is a valid entry
            guard let destinationIATA = destinationTextField.text, destinationIATA.count == 3 else {
                AlertUtil.showAlert(in: self, msg:AlertMessageText.BAD_DESTINATION , title: AlertMessageTitle.ERROR)
                return
            }
            
            //Check that origin and destination are different
            guard originIATA != destinationIATA else {
                AlertUtil.showAlert(in: self, msg:AlertMessageText.SAME_DESTINATIONS , title: AlertMessageTitle.ERROR)
                return
            }
            
            let originIataUpperCased = originIATA.uppercased()
            let destinationIataUpperCased = destinationIATA.uppercased()
            
            //Check that origin airport exists
            guard let origin = searchAirport(iata: originIataUpperCased) else {
                AlertUtil.showAlert(in: self, msg: AlertMessageText.NO_ORIGIN , title: AlertMessageTitle.ERROR)
                return
            }
            //Check that destination airport exists
            guard let destination = searchAirport(iata: destinationIataUpperCased) else {
                AlertUtil.showAlert(in: self, msg: AlertMessageText.NO_DESTINATION, title: AlertMessageTitle.ERROR)
                return
            }
            
            let originVertex = Vertex(data:origin)
            let destinationVertex = Vertex(data:destination)
            
            route = RouteManager.calculateRoute(routes: routes, origin: originVertex, destination: destinationVertex)
            
            if route.count > 0 {
                performSegue(withIdentifier: SEGUES.TO_MAP, sender: self)
            } else {
                AlertUtil.showAlert(in: self, msg: AlertMessageText.NO_ROUTE, title: AlertMessageTitle.ERROR)
            }
            
            
        }
        
    }
}

//Mark: Text Field delegate
extension AirportSelectionViewController: UITextFieldDelegate {
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        //Limit input to 3 upper case letters.
        if string.count == 0 {
            return true
        }
        let invalidCharacters = NSCharacterSet.letters.inverted
        
        let currentText = textField.text ?? ""
        
        let prospectiveText = (currentText as NSString).replacingCharacters(in: range, with: string)
        
        return prospectiveText.count <= 3 && prospectiveText.rangeOfCharacter(from: invalidCharacters) == nil
        
    }
}

//MARK: SEGUE
extension AirportSelectionViewController {
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == SEGUES.TO_MAP {
            let destinationVC = segue.destination as! RouteViewController
            destinationVC.route = route
        }
    }
}

//MARK: Navigation bar

extension AirportSelectionViewController {
    private func styleNavigationBar(){
        
        let navigationBar = self.navigationController?.navigationBar
        navigationBar?.barTintColor = COLORS.PRIMARY
        navigationBar?.titleTextAttributes = [NSAttributedString.Key.foregroundColor:COLORS.SECONDARY]
    }
}


