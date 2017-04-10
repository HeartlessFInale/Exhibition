package com.exhibition.exhibition.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yatinkaushal on 4/1/17.
 */

public class Artist implements Parcelable {
    public String name;
    public int id;
    public String picture;
    public String description;

    public Artist(String name, int id, String picture) {
        this.name = name;
        this.id = id;
        this.picture = picture;
    }

    protected Artist(Parcel in) {
        name = in.readString();
        id = in.readInt();
        picture = in.readString();
        description = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public Artist(String name, int id, String picture, String description) {
        this.name = name;
        this.id = id;
        this.picture = picture;
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(picture);
        dest.writeString(description);
    }
}
