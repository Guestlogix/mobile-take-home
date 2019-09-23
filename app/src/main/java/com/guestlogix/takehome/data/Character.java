package com.guestlogix.takehome.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "character")
public final class Character implements Parcelable{

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String id;

    @NonNull
    @ColumnInfo(name = "name")
    private final String name;

    @NonNull
    @ColumnInfo(name = "status")
    private final String status;

    @NonNull
    @ColumnInfo(name = "species")
    private final String species;

    @NonNull
    @ColumnInfo(name = "type")
    private final String type;

    @NonNull
    @ColumnInfo(name = "gender")
    private final String gender;

    @Nullable
    @ColumnInfo(name = "origin")
    private final String origin;

    @Nullable
    @ColumnInfo(name = "location")
    private final String location;

    @Nullable
    @ColumnInfo(name = "image")
    private final String image;

    @NonNull
    @ColumnInfo(name = "created")
    private final String created;

    public Character(
        @NonNull String id,
        @NonNull String name,
        @NonNull String status,
        @NonNull String species,
        @NonNull String type,
        @NonNull String gender,
        @NonNull String origin,
        @NonNull String location,
        @NonNull String image,
        @NonNull String created
    ) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.image = image;
        this.created = created;
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
    public int describeContents() {
        return 0;
    }

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

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    @NonNull
    public String getSpecies() {
        return species;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public String getGender() {
        return gender;
    }

    @NonNull
    public String getOrigin() {
        return origin;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    @NonNull
    public String getImage() {
        return image;
    }

    @NonNull
    public String getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Character article = (Character) obj;
        return article.id.equals(this.id) &&
            article.name.equals(this.name) &&
            article.status.equals(this.status) &&
            article.species.equals(this.species) &&
            article.type.equals(this.type) &&
            article.gender.equals(this.gender) &&
            article.origin.equals(this.origin) &&
            article.location.equals(this.location) &&
            article.image.equals(this.image) &&
            article.created.equals(this.created);
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
