package com.guestlogix.takehome.data.source.remote;

import androidx.annotation.NonNull;

import com.guestlogix.takehome.data.Episode;
import com.guestlogix.takehome.data.source.EpisodesDataSource;
import com.guestlogix.takehome.models.EpisodeResponse;
import com.guestlogix.takehome.network.GuestlogixApi;
import com.guestlogix.takehome.network.GuestlogixException;
import com.guestlogix.takehome.network.ResponseListener;

public class EpisodesRemoteDataSource implements EpisodesDataSource {

    private static EpisodesRemoteDataSource INSTANCE;

    public static EpisodesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EpisodesRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private EpisodesRemoteDataSource() {}

    /**
     * Note: {@link LoadEpisodesCallback#onDataNotAvailable(GuestlogixException)}  is never fired. In a real remote data
     * source implementation, this would be fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getEpisodes(int page, final @NonNull LoadEpisodesCallback callback) {
        GuestlogixApi.getAPI().getEpisodesList(page, new ResponseListener<EpisodeResponse>() {
            @Override
            public void onResponse(EpisodeResponse response) {
                callback.onEpisodesLoaded(response.getResults());
            }

            @Override
            public void onFailure(GuestlogixException e) {
                callback.onDataNotAvailable(e);
            }
        });
    }

    @Override
    public void saveEpisode(@NonNull Episode episode) {
//        Not Required
    }

    @Override
    public void deleteAllEpisodes() {
//        Not Required
    }
}
