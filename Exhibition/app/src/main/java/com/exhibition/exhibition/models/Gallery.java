package com.exhibition.exhibition.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yatinkaushal on 4/1/17.
 */

public class Gallery implements Parcelable {
    public String name;
    public int image;
    public String description;
    public String artType;

    public Gallery() {}

    public Gallery(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected Gallery(Parcel in) {
        name = in.readString();
        image = in.readInt();
        description = in.readString();
        artType = in.readString();
    }

    public static final Creator<Gallery> CREATOR = new Creator<Gallery>() {
        @Override
        public Gallery createFromParcel(Parcel in) {
            return new Gallery(in);
        }

        @Override
        public Gallery[] newArray(int size) {
            return new Gallery[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(image);
        dest.writeString(description);
        dest.writeString(artType);
    }
}
