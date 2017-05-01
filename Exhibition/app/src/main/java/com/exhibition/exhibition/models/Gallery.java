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
    public int id;
    public int isFav;
    public int year;
    public String photo;
    public double latitude;
    public double longitude;

    public Gallery() {}

    public Gallery(String name, String description, int id, int isFav) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.isFav = isFav;
    }

    protected Gallery(Parcel in) {
        name = in.readString();
        image = in.readInt();
        description = in.readString();
        artType = in.readString();
        id = in.readInt();
        isFav = in.readInt();
        photo = in.readString();
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

    public Gallery(String name, String description, int id, int isFav, String photo) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.isFav = isFav;
        this.photo = photo;
    }

    public Gallery(String name, String description, int gallery_id, String photo) {
        this.name = name;
        this.description = description;
        this.id = gallery_id;
        this.photo = photo;
    }

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
        dest.writeInt(id);
        dest.writeInt(isFav);
        dest.writeString(photo);
    }
}
