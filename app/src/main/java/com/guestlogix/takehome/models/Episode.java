package com.guestlogix.takehome.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

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
}
