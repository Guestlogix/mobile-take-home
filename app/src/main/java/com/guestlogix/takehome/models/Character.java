package com.guestlogix.takehome.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Character implements Parcelable {

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

    public Character(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.status = parcel.readString();
        this.species = parcel.readString();
        this.type = parcel.readString();
        this.gender = parcel.readString();
        this.origin = parcel.readString();
        this.location = parcel.readString();
        this.created = parcel.readString();
        this.image = parcel.readString();
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(status);
        dest.writeString(species);
        dest.writeString(type);
        dest.writeString(gender);
        dest.writeString(origin);
        dest.writeString(location);
        dest.writeString(image);
        dest.writeString(created);
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
    public int describeContents() {
        return 0;
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
}
