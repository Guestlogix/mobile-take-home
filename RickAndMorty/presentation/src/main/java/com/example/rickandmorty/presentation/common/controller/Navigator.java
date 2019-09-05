package com.example.rickandmorty.presentation.common.controller;

import com.example.rickandmorty.presentation.episodedetail.model.Character;

public interface Navigator {

    /**
     * Navigates to the episode details view which will display a list of characters for the
     * selected episode.
     * @param characterIds - the list of character Ids
     */
    void navigateToEpisodeDetail(final String characterIds);

    /**
     * Navigates to the character detail view for the selected character.
     * @param character
     */
    void navigateToCharacterDetail(final Character character);
}
