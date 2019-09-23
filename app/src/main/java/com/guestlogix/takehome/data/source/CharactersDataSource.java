package com.guestlogix.takehome.data.source;

import androidx.annotation.NonNull;

import com.guestlogix.takehome.data.Character;

import java.util.List;

public interface CharactersDataSource {

    public interface LoadCharactersCallback {

        void onCharactersLoaded(List<Character> characters);

        void onDataNotAvailable();
    }

    public interface GetCharacterCallback {

        void onCharacterLoaded(Character episode);

        void onDataNotAvailable();
    }

    void getCharacters(String characterIds, @NonNull LoadCharactersCallback callback);

    void saveCharacter(@NonNull Character character);

    void refreshCharacters();

    void deleteAllCharacters();

    void killCharacter(String id);

    void getCharacter(@NonNull String taskId, @NonNull GetCharacterCallback callback);
}
