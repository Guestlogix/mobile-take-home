package com.guestlogix.takehome.data.source;

import androidx.annotation.NonNull;

import com.guestlogix.takehome.data.Episode;

import java.util.List;

public interface EpisodesDataSource {

    public interface LoadEpisodesCallback {

        void onEpisodesLoaded(List<Episode> episodes);

        void onDataNotAvailable();
    }

    public interface GetEpisodeCallback {

        void onEpisodeLoaded(Episode episode);

        void onDataNotAvailable();
    }

    void getEpisodes(int page, @NonNull LoadEpisodesCallback callback);

    void saveEpisode(@NonNull Episode episode);

    void refreshEpisodes();

    void deleteAllEpisodes();
}
