//
//  RickAndMortyTests.swift
//  RickAndMortyTests
//
//  Created by Kalangutkar Ankita Vinod on 7/10/19.
//  Copyright © 2019 Kalangutkar Ankita Vinod. All rights reserved.
//

import XCTest
@testable import RickAndMorty

class RickAndMortyTests: XCTestCase {
    var episodeViewController: EpisodeViewController!
    var characterViewController: CharacterViewController!

    override func setUp() {
        episodeViewController = EpisodeViewController.instantiateFromStoryboard()
        _ = episodeViewController.view
        
        characterViewController = CharacterViewController.instantiateFromStoryboard()
        _ = characterViewController.view
    }

    func testFakeServiceForFetchingEpisodes() {
        let fakeEpisodeService = FakeEpisodeServiceCall()
        let episodeViewModel = EpisodeListViewModel(episodeListService: fakeEpisodeService)
        episodeViewController.episodeListViewModel = episodeViewModel
        episodeViewController.fetchAllEpisodes()
        
        XCTAssertNotNil(episodeViewController.episodeListTableView.dataSourceValue)
        
    }
    
    func testFakeServiceForFetchingCharacters() {
        let fakeCharacterService = FakeCharacterServiceCall()
        let characterViewModel = CharactersViewModel(characterListService: fakeCharacterService)
        characterViewController.charactersViewModel = characterViewModel
        characterViewController.fetchCasts("1,3,4")
        
        XCTAssertNotNil(characterViewController.castListTableView.dataSourceValue)
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }

    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
