package com.example.rickandmorty.presentation.episode.model;

import java.util.Date;
import java.util.List;

public class Episode {

    private int id;
    private String name;
    private String dateAired;
    private List<String> characters;

    public Episode(int id, String name, String dateAired, List<String> characters) {
        this.id = id;
        this.name = name;
        this.dateAired = dateAired;
        this.characters = characters;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateAired() {
        return dateAired;
    }

    public List<String> getCharacters() {
        return characters;
    }
}
