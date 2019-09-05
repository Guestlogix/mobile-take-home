package com.example.rickandmortydata.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EpisodeData {

    private int id;
    private String name;

    @SerializedName("air_date")
    private String airDate;

    private String episode;

    @SerializedName("characters")
    private List<String> characterList;

    private String url;
    private String created;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAirDate() {
        return airDate;
    }

    public String getEpisode() {
        return episode;
    }

    public List<String> getCharacterList() {
        return characterList;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated() {
        return created;
    }
}
