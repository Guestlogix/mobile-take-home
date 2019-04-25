//
//  MapViewController.swift
//  guesLogixChallenge
//
//  Created by Omar Padierna on 2019-04-23.
//  Copyright © 2019 Omar Padierna. All rights reserved.
//

import UIKit
import Mapbox

class RouteViewController: UIViewController {
    
    @IBOutlet weak var routeLabel: UILabel!
    @IBOutlet weak var mapView: MGLMapView!
    
    var coordinates = [CLLocationCoordinate2D]()
    var route = [Edge<Airport>]()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        //Extract coordinates
       coordinates = extractCoordinates()
        routeLabel.text = printRoute()
        //Map setup
        setUpMap()
 
    }
    

}
