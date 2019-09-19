package com.guestlogix.takehome.datasource;

import android.util.JsonReader;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.guestlogix.takehome.models.Episode;
import com.guestlogix.takehome.models.EpisodeResponse;
import com.guestlogix.takehome.network.GuestlogixApi;
import com.guestlogix.takehome.network.NetworkResponseListener;
import com.guestlogix.takehome.network.NetworkState;

public class EpisodesDataSource extends PageKeyedDataSource<Integer, Episode> {

    private MutableLiveData<NetworkState> networkState;

    EpisodesDataSource() {
        networkState = new MutableLiveData<>();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, Episode> callback) {

        networkState.postValue(NetworkState.LOADING);
        GuestlogixApi.getAPI().getEpisodesList(null, new NetworkResponseListener() {
            @Override
            public void onResponse(JsonReader reader) {
                try {
                    EpisodeResponse response = new EpisodeResponse.EpisodeResponseObjectMappingFactory().instantiate(reader);
                    Integer nextPage = response.getInfo().getCount() > 1 ? 2 : null;
                    callback.onResult(response.getResults(), null, nextPage);
                    networkState.postValue(NetworkState.DONE);
                } catch (Exception e) {
                    networkState.postValue(NetworkState.ERROR);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                networkState.postValue(NetworkState.ERROR);
                Log.e("test", message);
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Episode> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, Episode> callback) {

        networkState.postValue(NetworkState.LOADING);

        GuestlogixApi.getAPI().getEpisodesList(params.key, new NetworkResponseListener() {
            @Override
            public void onResponse(JsonReader reader) {
                try {
                    EpisodeResponse response = new EpisodeResponse.EpisodeResponseObjectMappingFactory().instantiate(reader);
                    Integer nextPage = response.getInfo().getPages() > params.key ? params.key+1 : null;
                    callback.onResult(response.getResults(), nextPage);
                    networkState.postValue(NetworkState.DONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    networkState.postValue(NetworkState.ERROR);
                }
            }

            @Override
            public void onFailure(String message) {
                networkState.postValue(NetworkState.ERROR);
                Log.e("test", message);
            }
        });
    }
}