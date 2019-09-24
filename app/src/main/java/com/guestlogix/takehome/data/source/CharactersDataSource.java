package com.guestlogix.takehome.data.source;

import androidx.annotation.NonNull;

import com.guestlogix.takehome.data.Character;
import com.guestlogix.takehome.network.GuestlogixException;

import java.util.List;

public interface CharactersDataSource {

    interface LoadCharactersCallback {

        void onCharactersLoaded(List<Character> characters);

        void onDataNotAvailable(GuestlogixException e);
    }

    interface GetCharacterCallback {

        void onCharacterLoaded(Character character);

        void onDataNotAvailable();
    }

    void getCharacters(String characterIds, @NonNull LoadCharactersCallback callback);

    void saveCharacter(@NonNull Character character);

    void deleteAllCharacters();

    void killCharacter(String id, @NonNull GetCharacterCallback callback);

    void getCharacter(@NonNull String id, @NonNull GetCharacterCallback callback);
}
