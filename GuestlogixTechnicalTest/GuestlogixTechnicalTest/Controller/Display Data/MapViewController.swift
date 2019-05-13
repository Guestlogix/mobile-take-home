//
//  MapViewController.swift
//  GuestlogixTechnicalTest
//
//  Created by Camila Gaitan on 5/12/19.
//  Copyright © 2019 Maika. All rights reserved.
//

import Foundation
import UIKit
import MapKit
import KRActivityIndicatorView

class MapViewController: UIViewController{
    
    @IBOutlet weak var map: MKMapView?
    
    var loadingIndicator: KRActivityIndicatorView?
    var origin: String?
    var destination: String?
    var connections: [Airport] = []
    var airports: [Airport] = []
    var routes: [Route] = []
    
    lazy var viewModel: MapViewModel = {
        return MapViewModel()
    }()
    
    //MARK: Controller lifeCycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.controllerConfiguration()
        self.configureLoadingIndicator()
        
        DispatchQueue.global(qos: .userInitiated).async {
            self.connections = self.viewModel.createConnections()
            DispatchQueue.main.async {
                self.loadingIndicator?.isHidden = true
                self.map?.alpha = 1.0
                self.configureUI()
            }
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
    }
    
    //MArk: Init Methods
    
    func controllerConfiguration() {
        self.viewModel.airports = self.airports
        self.viewModel.routes = self.routes
    }
    
    //MARK: Configure UI
    
    func configureLoadingIndicator() {
        
        self.loadingIndicator = KRActivityIndicatorView(colors: [UIColor.orange])
        self.loadingIndicator?.loadingFrame()
        self.loadingIndicator?.isHidden = false
        self.map!.addSubview(self.loadingIndicator!)
        self.map?.alpha = 0.5
    }
    
    func configureUI() {
        
        map?.delegate = self
        let locations = createRoute(origin: self.origin!, destination: self.destination!)
        var points: [CLLocationCoordinate2D] = []
        
        for item in locations{
            let node = airports.first(where: {$0.code == item})
            let coordinate = CLLocationCoordinate2D(latitude: (node?.coordinate.coordinate.latitude)!, longitude: (node?.coordinate.coordinate.longitude)!)
            points.append(coordinate)
            
            let annotation = MKPointAnnotation()
            annotation.coordinate = coordinate
            annotation.title = node?.name
            map?.addAnnotation(annotation)
        }
        
        
        if points.count > 0{
            
            let span = MKCoordinateSpan(latitudeDelta: 100, longitudeDelta: 100)
            let region = MKCoordinateRegion(center: points[0], span: span)
            map?.setRegion(region, animated: true)
            
            let linea = MKPolyline(coordinates: points, count: points.count)
            map?.addOverlay(linea)
            return
        }
        
        self.createErrorAlert()
    }
    
    
    func createErrorAlert() {
        
        let alert = UIAlertController(title: "Route was not found", message: "The route was not found between points", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: { action in
            self.navigationController?.popViewController(animated: true)
        }))
        self.present(alert, animated: true)
    }
    
    func createRoute(origin: String, destination: String) -> [String] {
        let sourceNode = connections.first(where: {$0.code == origin})
        let destinationNode =  connections.first(where: {$0.code == destination})
        
        if (sourceNode != nil && destinationNode != nil){
            let path = self.viewModel.shortestPath(source: sourceNode!, destination: destinationNode!)
            if let succession: [String] = path?.array.reversed().compactMap({ $0 as? Airport}).map({$0.code}) {
                print("Find Quickest path: \(succession)")
                return succession
            }
            
            print("No path between \(sourceNode?.name ?? "") & \(destinationNode?.name ?? "")")
        }
        return[]
    }
    

    
    @IBAction func backAction(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    
}

extension MapViewController: MKMapViewDelegate{
    
    func mapView(_ mapView: MKMapView, rendererFor overlay: MKOverlay) -> MKOverlayRenderer {
        
        if overlay is MKPolyline{
            let polyline = MKPolylineRenderer(overlay: overlay)
            polyline.strokeColor = .orange
            polyline.lineWidth = 5
            return polyline
        }
        return MKOverlayRenderer(overlay: overlay)
    }
}
