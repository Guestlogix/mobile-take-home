package com.guestlogix.takehome.datasource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.guestlogix.takehome.models.Episode;
import com.guestlogix.takehome.network.NetworkState;

import java.util.ArrayList;

public class EpisodesDataSource extends PageKeyedDataSource<Long, Episode> {

    private static final String TAG = EpisodesDataSource.class.getSimpleName();

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    public EpisodesDataSource() {
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }


    public MutableLiveData getNetworkState() {
        return networkState;
    }


    /*
     * Step 2: This method is responsible to load the data initially
     * when app screen is launched for the first time.
     * We are fetching the first page data from the api
     * and passing it via the callback method to the UI.
     */
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull LoadInitialCallback<Long, Episode> callback) {

        networkState.postValue(NetworkState.LOADING);

        ArrayList<Episode> dummyList = new ArrayList<>();
        dummyList.add(new Episode("1", "Episode 1", "Name 1", "Created 1"));
        dummyList.add(new Episode("2", "Episode 2", "Name 2", "Created 2"));
        dummyList.add(new Episode("3", "Episode 3", "Name 3", "Created 3"));
        dummyList.add(new Episode("4", "Episode 4", "Name 4", "Created 4"));
        dummyList.add(new Episode("5", "Episode 5", "Name 5", "Created 5"));
        dummyList.add(new Episode("6", "Episode 6", "Name 6", "Created 6"));
        dummyList.add(new Episode("7", "Episode 7", "Name 7", "Created 7"));
        dummyList.add(new Episode("8", "Episode 8", "Name 8", "Created 8"));
        dummyList.add(new Episode("9", "Episode 9", "Name 9", "Created 9"));
        dummyList.add(new Episode("10", "Episode 10", "Name 10", "Created 10"));
        dummyList.add(new Episode("11", "Episode 11", "Name 11", "Created 11"));
        dummyList.add(new Episode("12", "Episode 12", "Name 12", "Created 12"));
        dummyList.add(new Episode("13", "Episode 13", "Name 13", "Created 13"));
        dummyList.add(new Episode("14", "Episode 14", "Name 14", "Created 14"));
        dummyList.add(new Episode("15", "Episode 15", "Name 15", "Created 15"));
        dummyList.add(new Episode("16", "Episode 16", "Name 16", "Created 16"));
        dummyList.add(new Episode("17", "Episode 17", "Name 17", "Created 17"));
        dummyList.add(new Episode("18", "Episode 18", "Name 18", "Created 18"));
        dummyList.add(new Episode("19", "Episode 19", "Name 19", "Created 19"));
        dummyList.add(new Episode("20", "Episode 20", "Name 20", "Created 20"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callback.onResult(dummyList, null, 2l);

        initialLoading.postValue(NetworkState.DONE);
        networkState.postValue(NetworkState.DONE);
    }



    @Override
    public void loadBefore(@NonNull LoadParams<Long> params,
                           @NonNull LoadCallback<Long, Episode> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params,
                          @NonNull LoadCallback<Long, Episode> callback) {

        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);

        ArrayList<Episode> dummyList = new ArrayList<>();

        if(params.key == 2l) {
            dummyList.add(new Episode("21", "Episode 21", "Name 21", "Created 21"));
            dummyList.add(new Episode("22", "Episode 22", "Name 22", "Created 22"));
            dummyList.add(new Episode("23", "Episode 23", "Name 23", "Created 23"));
            dummyList.add(new Episode("24", "Episode 24", "Name 24", "Created 24"));
            dummyList.add(new Episode("25", "Episode 25", "Name 25", "Created 25"));
            dummyList.add(new Episode("26", "Episode 26", "Name 26", "Created 26"));
            dummyList.add(new Episode("27", "Episode 27", "Name 27", "Created 27"));
            dummyList.add(new Episode("28", "Episode 28", "Name 28", "Created 28"));
            dummyList.add(new Episode("29", "Episode 29", "Name 29", "Created 29"));
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        callback.onResult(dummyList, null);
        networkState.postValue(NetworkState.DONE);
    }
}