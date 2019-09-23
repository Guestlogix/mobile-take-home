package com.guestlogix.takehome.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.guestlogix.takehome.data.Episode;

public class EpisodeRowStub {

    private String name;
    private String episode;
    private String created;
    private Episode reference;

    public EpisodeRowStub(String name, String episode, String created, Episode reference) {
        this.name = name;
        this.episode = episode;
        this.created = created;
        this.reference = reference;
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

    public Episode getReference() {
        return reference;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        EpisodeRowStub episode = (EpisodeRowStub) obj;
        return episode.name.equals(this.name) && episode.episode.equals(this.episode);
    }

    public static DiffUtil.ItemCallback<EpisodeRowStub> DIFF_CALLBACK = new DiffUtil.ItemCallback<EpisodeRowStub>() {
        @Override
        public boolean areItemsTheSame(@NonNull EpisodeRowStub oldItem, @NonNull EpisodeRowStub newItem) {
            return oldItem.episode.equals(newItem.episode);
        }

        @Override
        public boolean areContentsTheSame(@NonNull EpisodeRowStub oldItem, @NonNull EpisodeRowStub newItem) {
            return oldItem.equals(newItem);
        }
    };
}
