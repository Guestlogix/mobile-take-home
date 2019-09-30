package com.guestlogix.takehome.network.response;

import android.util.JsonReader;

import com.guestlogix.takehome.data.Episode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EpisodeParsingFactory implements ObjectFactory<Episode> {

    @Override
    public Episode instantiate(JsonReader reader) throws Exception {
        String id = null;
        String name = null;
        String episode = null;
        String created = null;
        List<String> characters = new ArrayList<>();

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
                case "episode":
                    episode = reader.nextString();
                    break;
                case "created":
                    created = reader.nextString();
                    break;
                case "characters":
                    reader.beginArray();
                    while (reader.hasNext()) {
                        String url = reader.nextString();
                        characters.add(url.substring(url.lastIndexOf('/') + 1));
                    }

                    reader.endArray();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }

        reader.endObject();
        return new Episode(id, name, episode, created, characters.toArray(new String[0]));
    }
}