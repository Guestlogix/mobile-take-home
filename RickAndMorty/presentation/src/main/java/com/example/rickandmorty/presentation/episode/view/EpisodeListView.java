package com.example.rickandmorty.presentation.episode.view;

import com.example.rickandmorty.presentation.episode.model.Episode;

import java.util.List;

/**
 * Interface representing a View to display a list of episodes.
 */
public interface EpisodeListView {

    /**
     * Displays the loading spinner while the list of episodes are being downloaded.
     */
    void showLoadingView();

    /**
     * Display the list of episodes to the user.
     */
    void showEpisodesList(List<Episode> episodesList);

    /**
     * Displays a list of characters in the selected episode.
     * @param characterIds
     */
    void showEpisodeDetails(String characterIds);

    /**
     * Displays a toast message to the user in case of error.
     * @param message
     */
    void showError(String message);
}
