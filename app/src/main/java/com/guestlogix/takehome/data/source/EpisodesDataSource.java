package com.guestlogix.takehome.data.source;

import androidx.annotation.NonNull;

import com.guestlogix.takehome.data.Episode;
import com.guestlogix.takehome.network.GuestlogixException;

import java.util.List;

public interface EpisodesDataSource {

    interface LoadEpisodesCallback {

        void onEpisodesLoaded(List<Episode> episodes);

        void onDataNotAvailable(GuestlogixException e);
    }

    void getEpisodes(int page, @NonNull LoadEpisodesCallback callback);

    void saveEpisode(@NonNull Episode episode);

    void deleteAllEpisodes();
}
