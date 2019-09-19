package com.guestlogix.takehome.viewmodels;

import android.util.JsonReader;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.guestlogix.takehome.models.Character;
import com.guestlogix.takehome.network.GuestlogixApi;
import com.guestlogix.takehome.network.NetworkResponseListener;
import com.guestlogix.takehome.network.NetworkState;
import com.guestlogix.takehome.network.response.ArrayMappingFactory;

import java.util.List;

public class CharactersListViewModel extends ViewModel {

    private MutableLiveData<List<Character>> characters = new MutableLiveData<>();
    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

    public CharactersListViewModel(String[] characterIds) {
        networkState.postValue(NetworkState.LOADING);

        GuestlogixApi.getAPI().getCharactersList(characterIds, new NetworkResponseListener() {
            @Override
            public void onResponse(JsonReader reader) {
                try {
                    List<Character> response = new ArrayMappingFactory<>(new Character.CharacterObjectMappingFactory()).instantiate(reader);
                    characters.postValue(response);
                    networkState.postValue(NetworkState.DONE);
                } catch (Exception e) {
                    networkState.postValue(NetworkState.ERROR);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
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
