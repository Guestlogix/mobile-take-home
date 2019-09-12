package com.guestlogix.takehome.datasource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.guestlogix.takehome.models.Character;
import com.guestlogix.takehome.network.NetworkState;

import java.util.ArrayList;

public class CharactersDataSource extends PageKeyedDataSource<Integer, Character> {

    private static final String TAG = CharactersDataSource.class.getSimpleName();

    private MutableLiveData<NetworkState> networkState;

    CharactersDataSource() {
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
                            @NonNull LoadInitialCallback<Integer, Character> callback) {

        networkState.postValue(NetworkState.LOADING);

        ArrayList<Character> dummyList = new ArrayList<>();
        dummyList.add(new Character("1", "Rick Sanchez", "Alive", "Human", "", "Male", "Earth (C-137)", "Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/character/avatar/1.jpeg", "2017-11-04T18:48:46.250Z"));
        dummyList.add(new Character("2", "Morty Smith", "Alive", "Human", "", "Male", "Earth (C-137)", "Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/character/avatar/2.jpeg", "2017-11-04T18:50:21.651Z"));
        dummyList.add(new Character("35", "Bepisian", "Alive", "Alien", "Bepisian", "unknown", "Bepis 9", "Bepis 9", "https://rickandmortyapi.com/api/character/avatar/35.jpeg", "2017-11-05T09:27:38.491Z"));
        dummyList.add(new Character("38", "Beth Smith", "Alive", "Human", "", "Female", "Earth (C-137)", "Earth (C-137)", "https://rickandmortyapi.com/api/character/avatar/38.jpeg", "2017-11-05T09:48:44.230Z"));
        dummyList.add(new Character("62", "Canklanker Thom", "Dead", "Alien", "Gromflomite", "Male", "Gromflom Prime", "unknown", "https://rickandmortyapi.com/api/character/avatar/62.jpeg", "2017-11-05T12:06:23.217Z"));
        dummyList.add(new Character("92", "Davin", "Dead", "Human", "", "Male", "Earth (C-137)", "Earth (C-137)", "https://rickandmortyapi.com/api/character/avatar/92.jpeg", "2017-12-01T11:20:51.247Z"));
        dummyList.add(new Character("127",
            "Frank Palicky",
            "Dead",
            "Human",
            "",
            "Male",
            "Earth (C-137)",
            "Earth (C-137)",
            "https://rickandmortyapi.com/api/character/avatar/127.jpeg",
            "2017-12-26T19:22:48.474Z"));
        dummyList.add(new Character("144",
            "Glenn",
            "Dead",
            "Alien",
            "Gromflomite",
            "Male",
            "unknown",
            "Interdimensional Customs",
            "https://rickandmortyapi.com/api/character/avatar/144.jpeg",
            "2017-12-29T10:37:48.319Z"));
        dummyList.add(new Character("158",
            "Hookah Alien",
            "Alive",
            "Alien",
            "Tuskfish",
            "unknown",
            "unknown",
            "Interdimensional Customs",
            "https://rickandmortyapi.com/api/character/avatar/158.jpeg",
            "2017-12-29T15:53:48.952Z"));
        dummyList.add(new Character("175",
            "Jerry Smith",
            "Alive",
            "Human",
            "",
            "Male",
            "Earth (C-137)",
            "Earth (C-137)",
            "https://rickandmortyapi.com/api/character/avatar/175.jpeg",
            "2017-12-29T18:07:17.610Z"));
        dummyList.add(new Character("179",
            "Jessica",
            "Alive",
            "Cronenberg",
            "",
            "Female",
            "Earth (C-137)",
            "Earth (C-137)",
            "https://rickandmortyapi.com/api/character/avatar/179.jpeg",
            "2017-12-29T18:34:37.806Z"));
        dummyList.add(new Character("181",
            "Jessica's Friend",
            "Alive",
            "Human",
            "",
            "Female",
            "Earth (C-137)",
            "Earth (Replacement Dimension)",
            "https://rickandmortyapi.com/api/character/avatar/181.jpeg",
            "2017-12-29T18:47:23.345Z"));
        dummyList.add(new Character("239",
            "Mr. Goldenfold",
            "Alive",
            "Cronenberg",
            "",
            "Male",
            "Earth (C-137)",
            "Earth (C-137)",
            "https://rickandmortyapi.com/api/character/avatar/239.jpeg",
            "2017-12-30T17:42:11.894Z"));
        dummyList.add(new Character("249",
            "Mrs. Sanchez",
            "unknown",
            "Human",
            "",
            "Female",
            "unknown",
            "unknown",
            "https://rickandmortyapi.com/api/character/avatar/249.jpeg",
            "2017-12-30T18:18:10.965Z"));
        dummyList.add(new Character("271",
            "Principal Vagina",
            "Alive",
            "Cronenberg",
            "",
            "Male",
            "Earth (C-137)",
            "Earth (C-137)",
            "https://rickandmortyapi.com/api/character/avatar/271.jpeg",
            "2017-12-31T14:08:43.602Z"));
        dummyList.add(new Character("338",
            "Summer Smith",
            "Alive",
            "Human",
            "",
            "Female",
            "Earth (C-137)",
            "Earth (C-137)",
            "https://rickandmortyapi.com/api/character/avatar/338.jpeg",
            "2018-01-10T16:55:03.390Z"));
        dummyList.add(new Character("394",
            "Davin",
            "Dead",
            "Cronenberg",
            "",
            "Male",
            "Earth (C-137)",
            "Earth (C-137)",
            "https://rickandmortyapi.com/api/character/avatar/394.jpeg",
            "2018-01-20T19:48:42.201Z"));
        dummyList.add(new Character("395",
            "Greebybobe",
            "Alive",
            "Alien",
            "Greebybobe",
            "unknown",
            "Girvonesk",
            "Worldender's lair",
            "https://rickandmortyapi.com/api/character/avatar/395.jpeg",
            "2018-04-15T16:46:31.715Z"));
        dummyList.add(new Character("435",
            "Pripudlian",
            "Alive",
            "Alien",
            "Pripudlian",
            "unknown",
            "unknown",
            "Earth (Replacement Dimension)",
            "https://rickandmortyapi.com/api/character/avatar/435.jpeg",
            "2018-04-16T22:21:57.962Z"));

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
                           @NonNull LoadCallback<Integer, Character> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, Character> callback) {

        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);

        ArrayList<Character> dummyList = new ArrayList<>();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        callback.onResult(dummyList, null);
        networkState.postValue(NetworkState.DONE);
    }
}