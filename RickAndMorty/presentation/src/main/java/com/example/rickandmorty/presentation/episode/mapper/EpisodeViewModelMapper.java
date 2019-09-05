package com.example.rickandmorty.presentation.episode.mapper;

import com.example.rickandmorty.presentation.episode.model.Episode;
import com.example.rickandmortydata.entity.EpisodeData;

import io.reactivex.functions.Function;

/**
 * Converts a EpisodeData to Episode view model.
 */
public class EpisodeViewModelMapper implements Function<EpisodeData, Episode> {
    @Override
    public Episode apply(EpisodeData episodeData) {
        if (episodeData == null) {
            throw new IllegalStateException();
        }
        final Episode episode =
                new Episode(
                        episodeData.getId(),
                        episodeData.getName(),
                        episodeData.getAirDate(),
                        episodeData.getCharacterList());
        return episode;
    }
}
