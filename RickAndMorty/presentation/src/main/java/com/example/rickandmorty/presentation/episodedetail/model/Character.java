package com.example.rickandmorty.presentation.episodedetail.model;

public class Character {
    private int id;
    private String name;
    private String gender;
    private String status;
    private String imageUrl;
    private String url;
    private String origin;
    private String location;
    private String species;

    public Character(int id,
                     String name,
                     String gender, String status,
                     String imageUrl,
                     String url,
                     String origin,
                     String location,
                     String species) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.status = status;
        this.imageUrl = imageUrl;
        this.url = url;
        this.origin = origin;
        this.location = location;
        this.species = species;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getOrigin() {
        return origin;
    }

    public String getLocation() {
        return location;
    }

    public String getSpecies() {
        return species;
    }
}
