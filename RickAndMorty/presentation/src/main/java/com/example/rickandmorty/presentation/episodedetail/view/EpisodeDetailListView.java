package com.example.rickandmorty.presentation.episodedetail.view;

import com.example.rickandmorty.presentation.episodedetail.model.Character;

import java.util.List;

public interface EpisodeDetailListView {

    /**
     * Displays the loadview while the list is being loaded.
     */
    void showLoadingView();

    /**
     * @param charactersList
     * Displays the list of characters to the user.
     */
    void showCharactersList(List<Character> charactersList);

    void showCharacterDetail(Character character);

    /**
     * Displays a Toast to the user incase of error.
     * @param message
     */
    void showError(String message);
}
