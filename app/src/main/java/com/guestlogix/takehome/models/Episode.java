package com.guestlogix.takehome.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Episode {

    private String id;
    private String name;
    private String episode;
    private String created;

    public Episode(String id,
            String name,
            String episode,
            String created) {

        this.id = id;
        this.name = name;
        this.episode = episode;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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
