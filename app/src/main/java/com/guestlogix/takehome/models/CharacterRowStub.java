package com.guestlogix.takehome.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.guestlogix.takehome.data.Character;

public class CharacterRowStub {

    private String name;
    private String created;
    private String status;
    private String image;
    private Character reference;

    public CharacterRowStub(String name, String created, String status, String image, Character reference) {
        this.name = name;
        this.created = created;
        this.status = status;
        this.image = image;
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public String getCreated() {
        return created;
    }

    public Character getReference() {
        return reference;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        CharacterRowStub episode = (CharacterRowStub) obj;
        return episode.name.equals(this.name)
            && episode.created.equals(this.created)
            && episode.image.equals(this.image)
            && episode.status.equals(this.status);
    }

    public static DiffUtil.ItemCallback<CharacterRowStub> DIFF_CALLBACK = new DiffUtil.ItemCallback<CharacterRowStub>() {
        @Override
        public boolean areItemsTheSame(@NonNull CharacterRowStub oldItem, @NonNull CharacterRowStub newItem) {
            return oldItem.reference.getId().equals(newItem.reference.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CharacterRowStub oldItem, @NonNull CharacterRowStub newItem) {
            return oldItem.equals(newItem);
        }
    };
}
