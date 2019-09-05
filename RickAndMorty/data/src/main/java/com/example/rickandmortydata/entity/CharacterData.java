package com.example.rickandmortydata.entity;

import com.google.gson.annotations.SerializedName;

public class CharacterData {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    @SerializedName("image")
    private String imageUrl;
    private String url;
    private Location location;
    private Origin origin;

    public CharacterData(int id,
                         String name,
                         String status,
                         String species,
                         String type,
                         String gender,
                         String imageUrl,
                         String url,
                         Location location,
                         Origin origin) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.url = url;
        this.location = location;
        this.origin = origin;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public Location getLocation() {
        return location;
    }

    public Origin getOrigin() {
        return origin;
    }

    public class Origin {
        private String name;
        private String url;

        public Origin(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }

    public class Location {
        private String name;
        private String url;

        public Location(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }

}
