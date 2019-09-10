package com.guestlogix.takehome.datasource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.guestlogix.takehome.models.Episode;
import com.guestlogix.takehome.network.NetworkState;

import java.util.ArrayList;

public class EpisodesDataSource extends PageKeyedDataSource<Integer, Episode> {

    private static final String TAG = EpisodesDataSource.class.getSimpleName();

    private MutableLiveData<NetworkState> networkState;

    EpisodesDataSource() {
        networkState = new MutableLiveData<>();
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
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, Episode> callback) {

        networkState.postValue(NetworkState.LOADING);

        ArrayList<Episode> dummyList = new ArrayList<>();
        dummyList.add(new Episode("1","Pilot", "S01E01", "2017-11-10T12:56:33.798Z"));
        dummyList.add(new Episode("2", "Lawnmower Dog", "S01E02", "2017-11-10T12:56:33.916Z"));
        dummyList.add(new Episode("3", "Anatomy Park", "S01E03", "2017-11-10T12:56:34.022Z"));
        dummyList.add(new Episode("4", "M. Night Shaym-Aliens!", "S01E04", "2017-11-10T12:56:34.129Z"));
        dummyList.add(new Episode("5", "Meeseeks and Destroy", "S01E05", "2017-11-10T12:56:34.236Z"));
        dummyList.add(new Episode("6", "Rick Potion #9", "S01E06", "2017-11-10T12:56:34.339Z"));
        dummyList.add(new Episode("7", "Raising Gazorpazorp", "S01E07", "2017-11-10T12:56:34.441Z"));
        dummyList.add(new Episode("8", "Rixty Minutes", "S01E08", "2017-11-10T12:56:34.543Z"));
        dummyList.add(new Episode("9", "Something Ricked This Way Comes", "S01E09", "2017-11-10T12:56:34.645Z"));
        dummyList.add(new Episode("10", "Close Rick-counters of the Rick Kind", "S01E10", "2017-11-10T12:56:34.747Z"));
        dummyList.add(new Episode("11", "Ricksy Business", "S01E11", "2017-11-10T12:56:34.850Z"));
        dummyList.add(new Episode("12", "A Rickle in Time", "S02E01", "2017-11-10T12:56:34.953Z"));
        dummyList.add(new Episode("13", "Mortynight Run", "S02E02", "2017-11-10T12:56:35.055Z"));
        dummyList.add(new Episode("14", "Auto Erotic Assimilation", "S02E03", "2017-11-10T12:56:35.158Z"));
        dummyList.add(new Episode("15", "Total Rickall", "S02E04", "2017-11-10T12:56:35.261Z"));
        dummyList.add(new Episode("16", "Get Schwifty", "S02E05", "2017-11-10T12:56:35.364Z"));
        dummyList.add(new Episode("17", "The Ricks Must Be Crazy", "S02E06", "2017-11-10T12:56:35.467Z"));
        dummyList.add(new Episode("18", "Big Trouble in Little Sanchez", "S02E07", "2017-11-10T12:56:35.569Z"));
        dummyList.add(new Episode("19", "Interdimensional Cable 2: Tempting Fate", "S02E08", "2017-11-10T12:56:35.669Z"));
        dummyList.add(new Episode("20", "Look Who's Purging Now", "S02E09", "2017-11-10T12:56:35.772Z"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callback.onResult(dummyList, null, 2);

        networkState.postValue(NetworkState.DONE);
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Episode> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, Episode> callback) {

        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);

        ArrayList<Episode> dummyList = new ArrayList<>();

        if (params.key == 2) {
            
            dummyList.add(new Episode("21", "The Wedding Squanchers", "S02E10", "2017-11-10T12:56:35.875Z"));
            dummyList.add(new Episode("22", "The Rickshank Rickdemption", "S03E01", "2017-11-10T12:56:35.983Z"));
            dummyList.add(new Episode("23", "Rickmancing the Stone", "S03E02", "2017-11-10T12:56:36.100Z"));
            dummyList.add(new Episode("24", "Pickle Rick", "S03E03", "2017-11-10T12:56:36.206Z"));
            dummyList.add(new Episode("25", "Vindicators 3: The Return of Worldender", "S03E04", "2017-11-10T12:56:36.310Z"));
            dummyList.add(new Episode("26", "The Whirly Dirly Conspiracy", "S03E05", "2017-11-10T12:56:36.413Z"));
            dummyList.add(new Episode("27", "Rest and Ricklaxation", "S03E06", "2017-11-10T12:56:36.515Z"));
            dummyList.add(new Episode("28", "The Ricklantis Mixup", "S03E07", "2017-11-10T12:56:36.618Z"));
            dummyList.add(new Episode("29", "Morty's Mind Blowers", "S03E08", "2017-11-10T12:56:36.726Z"));
            dummyList.add(new Episode("30", "The ABC's of Beth", "S03E09", "2017-11-10T12:56:36.828Z"));
            dummyList.add(new Episode("31", "The Rickchurian Mortydate", "S03E10", "2017-11-10T12:56:36.929Z"));
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