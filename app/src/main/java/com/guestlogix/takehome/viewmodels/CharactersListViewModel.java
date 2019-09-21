package com.guestlogix.takehome.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.guestlogix.takehome.models.Character;
import com.guestlogix.takehome.network.GuestlogixApi;
import com.guestlogix.takehome.network.GuestlogixException;
import com.guestlogix.takehome.network.NetworkState;
import com.guestlogix.takehome.network.ResponseListener;

import java.util.List;

public class CharactersListViewModel extends ViewModel {

    private MutableLiveData<List<Character>> characters = new MutableLiveData<>();
    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

    public CharactersListViewModel(String[] characterIds) {
        networkState.postValue(NetworkState.LOADING);

        GuestlogixApi.getAPI().getCharactersList(characterIds, new ResponseListener<List<Character>>() {
            @Override
            public void onResponse(List<Character> response) {
                characters.postValue(response);
                networkState.postValue(NetworkState.DONE);
            }

            @Override
            public void onFailure(GuestlogixException e) {
                networkState.postValue(NetworkState.ERROR);
            }
        });
    }

    public MutableLiveData<List<Character>> getCharacters() {
        return characters;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
