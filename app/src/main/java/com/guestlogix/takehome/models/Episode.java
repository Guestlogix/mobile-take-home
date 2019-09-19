package com.guestlogix.takehome.models;

import android.util.JsonReader;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.guestlogix.takehome.network.response.ObjectMappingFactory;

import java.util.ArrayList;
import java.util.List;

public class Episode {

    private String id;
    private String name;
    private String episode;
    private String created;
    private String[] characters;

    public Episode(String id, String name, String episode, String created, String[] characters) {
        this.id = id;
        this.name = name;
        this.episode = episode;
        this.created = created;
        this.characters = characters;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEpisode() {
        return episode;
    }

    public String getCreated() {
        return created;
    }

    public String[] getCharacters() {
        return characters;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Episode article = (Episode) obj;
        return article.id.equals(this.id);
    }

    public static DiffUtil.ItemCallback<Episode> DIFF_CALLBACK = new DiffUtil.ItemCallback<Episode>() {
        @Override
        public boolean areItemsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
            return oldItem.equals(newItem);
        }
    };



    public static class EpisodeObjectMappingFactory implements ObjectMappingFactory<Episode> {

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
}
