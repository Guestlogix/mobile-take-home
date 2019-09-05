package com.example.rickandmortydata.mapper;

import com.example.rickandmortydata.entity.CharacterData;
import com.example.rickandmortydata.entity.EpisodeListData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CharacterJsonMapper {

    private Gson mGson;

    public CharacterJsonMapper(){
        mGson = new Gson();
    }

    public List<CharacterData> transformCharacterList(String characterListJson) {
        final Type listOfCharacters = new TypeToken<List<CharacterData>>(){}.getType();
        return mGson.fromJson(characterListJson, listOfCharacters);
    }
}
