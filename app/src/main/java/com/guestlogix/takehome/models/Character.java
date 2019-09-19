package com.guestlogix.takehome.models;

import android.util.JsonReader;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.guestlogix.takehome.network.response.ObjectMappingFactory;

public class Character {

    private String id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String origin;
    private String location;
    private String image;
    private String created;

    public Character(String id,
                     String name,
                     String status,
                     String species,
                     String type,
                     String gender,
                     String origin,
                     String location,
                     String image,
                     String created) {

        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.created = created;
        this.image = image;
    }

    public String getId() {
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

    public String getOrigin() {
        return origin;
    }

    public String getLocation() {
        return location;
    }

    public String getCreated() {
        return created;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Character article = (Character) obj;
        return article.id.equals(this.id);
    }

    public static DiffUtil.ItemCallback<Character> DIFF_CALLBACK = new DiffUtil.ItemCallback<Character>() {
        @Override
        public boolean areItemsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static class CharacterObjectMappingFactory implements ObjectMappingFactory<Character> {

        @Override
        public Character instantiate(JsonReader reader) throws Exception {
            String id = null;
            String name = null;
            String status = null;
            String species = null;
            String type = null;
            String gender = null;
            String origin = null;
            String location = null;
            String image = null;
            String created = null;

            reader.beginObject();

            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case "id":
                        id = reader.nextString();
                        break;
                    case "name":
                        name = reader.nextString();
                        break;
                    case "status":
                        status = reader.nextString();
                        break;
                    case "species":
                        species = reader.nextString();
                        break;
                    case "type":
                        type = reader.nextString();
                        break;
                    case "gender":
                        gender = reader.nextString();
                        break;
                    case "origin":
                        origin = readNameFromObject(reader);
                        break;
                    case "location":
                        location = readNameFromObject(reader);
                        break;
                    case "image":
                        image = reader.nextString();
                        break;
                    case "created":
                        created = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }

            reader.endObject();

            return new Character(id, name, status, species, type, gender, origin, location, image, created);
        }

        private String readNameFromObject(JsonReader reader) throws Exception {

            String name = null;
            reader.beginObject();

            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case "name":
                        name = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }

            reader.endObject();

            return name;
        }
    }
}
