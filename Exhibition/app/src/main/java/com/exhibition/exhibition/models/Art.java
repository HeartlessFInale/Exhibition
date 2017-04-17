package com.exhibition.exhibition.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yatinkaushal on 4/9/17.
 */
public class Art implements Parcelable {
    public int id;
    public int artist_id;
    public String description;
    public String name;
    public String picture;
    public String traits;

    public Art() {}

    public Art(int id, String description, String name, String picture, int artist_id, String traits) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.picture = picture;
        this.artist_id = artist_id;
        this.traits = traits;
    }

    protected Art(Parcel in) {
        id = in.readInt();
        artist_id = in.readInt();
        description = in.readString();
        name = in.readString();
        picture = in.readString();
        traits = in.readString();
    }

    public static final Creator<Art> CREATOR = new Creator<Art>() {
        @Override
        public Art createFromParcel(Parcel in) {
            return new Art(in);
        }

        @Override
        public Art[] newArray(int size) {
            return new Art[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(artist_id);
        dest.writeString(description);
        dest.writeString(name);
        dest.writeString(picture);
        dest.writeString(traits);
    }
}
