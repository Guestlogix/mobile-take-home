package com.guestlogix.takehome.data;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "episodes")
public final class Episode {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String id;

    @NonNull
    @ColumnInfo(name = "name")
    private final String name;

    @NonNull
    @ColumnInfo(name = "episode")
    private final String episode;

    @NonNull
    @ColumnInfo(name = "created")
    private final String created;

    @NonNull
    @ColumnInfo(name = "characters")
    private final String characters;

    public Episode(
        @NonNull String id,
        @NonNull String name,
        @NonNull String episode,
        @NonNull String created,
        @NonNull String[] characters
    ) {
        this.id = id;
        this.name = name;
        this.episode = episode;
        this.created = created;

        String str = Arrays.toString(characters);
        this.characters = str.substring(1, str.length()-1);
    }

    public Episode(
        @NonNull String id,
        @NonNull String name,
        @NonNull String episode,
        @NonNull String created,
        @NonNull String characters
    ) {
        this.id = id;
        this.name = name;
        this.episode = episode;
        this.created = created;
        this.characters = characters;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getEpisode() {
        return episode;
    }

    @NonNull
    public String getCreated() {
        return created;
    }

    @NonNull
    public String getCharacters() {
        return characters;
    }

    @NonNull
    public String[] getCharacterIds() {
        return characters.split(",");
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
