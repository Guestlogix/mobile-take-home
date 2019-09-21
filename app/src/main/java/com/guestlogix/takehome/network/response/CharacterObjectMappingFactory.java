package com.guestlogix.takehome.network.response;

import android.util.JsonReader;

import com.guestlogix.takehome.models.Character;

public class CharacterObjectMappingFactory implements ObjectFactory<Character> {

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