//
//  MapViewController.swift
//  Guestlogix App
//
//  Created by Adeyinka Adediji on 07/05/2019.
//  Copyright © 2019 Adeyinka Adediji. All rights reserved.
//

import MapKit
import Reusable
import RxCocoa
import RxFlow
import RxGesture
import RxSwift
import UIKit

class MapViewController: UIViewController, StoryboardBased, ViewModelBased, MKMapViewDelegate {
    internal var viewModel: MapViewModel!
    private let disposeBag = DisposeBag()
    @IBOutlet var mapView: MKMapView!
    @IBOutlet var searchRouteButton: AppButton!
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        navigationController?.isNavigationBarHidden = true
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        mapView.delegate = self
        
        searchRouteButton
            .rx
            .tapGesture()
            .when(.recognized)
            .subscribe(onNext: { _ in
                self.cleanUpMap()
                self.viewModel.showSearchView()
            }, onError: { error in
                print(error.localizedDescription)
            }, onCompleted: {
            }) {
            }.disposed(by: disposeBag)
    }
    
    private func cleanUpMap() {
        for overlay in mapView.overlays {
            mapView.removeOverlay(overlay)
        }
        for annotation in mapView.annotations {
            mapView.removeAnnotation(annotation)
        }
    }
    
    func drawMarkers(markers: [AirportMarker]) {
        var coordinates = [CLLocationCoordinate2D]()
        var annotations = [MKPointAnnotation]()
        for marker in markers {
            coordinates.append(marker.coodinates)
            let annotation = MKPointAnnotation()
            annotation.coordinate = marker.coodinates
            annotation.title = marker.title
            annotation.subtitle = marker.subTitle
            annotations.append(annotation)
            mapView.addAnnotation(annotation)
        }
        let geodesicPolyline = MKGeodesicPolyline(coordinates: &coordinates, count: markers.count)
        mapView.addOverlay(geodesicPolyline)
        
        mapView.showAnnotations(annotations, animated: true)
    }
    
    func mapView(_ mapView: MKMapView, rendererFor overlay: MKOverlay) -> MKOverlayRenderer {
        guard let polyline = overlay as? MKPolyline else {
            return MKOverlayRenderer()
        }
        
        let renderer = MKPolylineRenderer(polyline: polyline)
        renderer.lineWidth = 3.0
        renderer.alpha = 0.5
        renderer.strokeColor = .blue
        
        return renderer
    }
    
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        let planeIdentifier = "planeIdentifier"
        
        let annotationView = mapView.dequeueReusableAnnotationView(withIdentifier: planeIdentifier)
            ?? MKAnnotationView(annotation: annotation, reuseIdentifier: planeIdentifier)
        
        var image = UIImage(named: "airplane")
        image = image?.scaleImage(toFitSize: CGSize(width: 30, height: 30))
        annotationView.image = image
        annotationView.canShowCallout = true
        
        return annotationView
    }
}
