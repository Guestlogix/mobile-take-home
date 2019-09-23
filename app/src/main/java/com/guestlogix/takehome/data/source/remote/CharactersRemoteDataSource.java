package com.guestlogix.takehome.data.source.remote;

import androidx.annotation.NonNull;

import com.guestlogix.takehome.data.Character;
import com.guestlogix.takehome.data.source.CharactersDataSource;
import com.guestlogix.takehome.network.GuestlogixApi;
import com.guestlogix.takehome.network.GuestlogixException;
import com.guestlogix.takehome.network.ResponseListener;

import java.util.List;

public class CharactersRemoteDataSource implements CharactersDataSource {

    private static CharactersRemoteDataSource INSTANCE;

    public static CharactersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CharactersRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private CharactersRemoteDataSource() {}

    /**
     * Note: {@link LoadCharactersCallback#onDataNotAvailable()} is never fired. In a real remote data
     * source implementation, this would be fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getCharacters(String characterIds, final @NonNull LoadCharactersCallback callback) {
        GuestlogixApi.getAPI().getCharactersList(characterIds, new ResponseListener<List<Character>>() {
            @Override
            public void onResponse(List<Character> response) {
                callback.onCharactersLoaded(response);
            }

            @Override
            public void onFailure(GuestlogixException exception) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveCharacter(@NonNull Character task) {
//        Not Required
    }

    @Override
    public void refreshCharacters() {
        // Not required because the {@link CharactersRepository} handles the logic of refreshing the
    }

    @Override
    public void deleteAllCharacters() {
//        Not Required
    }

    @Override
    public void killCharacter(String id) {
//        Not Required
    }

    @Override
    public void getCharacter(@NonNull String Id, @NonNull GetCharacterCallback callback) {
//        Not Required
    }
}
