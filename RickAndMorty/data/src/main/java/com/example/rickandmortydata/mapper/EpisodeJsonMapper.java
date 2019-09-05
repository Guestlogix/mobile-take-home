package com.example.rickandmortydata.mapper;

import com.example.rickandmortydata.entity.EpisodeListData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class EpisodeJsonMapper {

    private final Gson mGson;

    public EpisodeJsonMapper() {
        this.mGson = new Gson();
    }

    public EpisodeListData transformEpisodeList(final String episodeJson) {
        final Type listOfEpisodeType = new TypeToken<EpisodeListData>(){}.getType();
        return mGson.fromJson(episodeJson, listOfEpisodeType);
    }
}
