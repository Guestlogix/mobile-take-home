package com.guestlogix.takehome.datasource;

import android.util.JsonReader;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.guestlogix.takehome.models.Character;
import com.guestlogix.takehome.models.CharactersResponse;
import com.guestlogix.takehome.network.GuestlogixApi;
import com.guestlogix.takehome.network.NetworkResponseListener;
import com.guestlogix.takehome.network.NetworkState;

public class CharactersDataSource extends PageKeyedDataSource<Integer, Character> {

    private MutableLiveData<NetworkState> networkState;

    CharactersDataSource() {
        networkState = new MutableLiveData<>();
    }


    public MutableLiveData getNetworkState() {
        return networkState;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, Character> callback) {

        networkState.postValue(NetworkState.LOADING);
        GuestlogixApi.getAPI().getCharactersList(null, null, new NetworkResponseListener() {
            @Override
            public void onResponse(JsonReader reader) {
                try {
                    CharactersResponse response = new CharactersResponse.CharactersResponseObjectMappingFactory().instantiate(reader);
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
                           @NonNull LoadCallback<Integer, Character> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, Character> callback) {

        networkState.postValue(NetworkState.LOADING);

        GuestlogixApi.getAPI().getCharactersList(params.key, null, new NetworkResponseListener() {
            @Override
            public void onResponse(JsonReader reader) {
                try {
                    CharactersResponse response = new CharactersResponse.CharactersResponseObjectMappingFactory().instantiate(reader);
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