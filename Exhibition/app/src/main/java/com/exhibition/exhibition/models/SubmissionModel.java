package com.exhibition.exhibition.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yatinkaushal on 4/16/17.
 */

public class SubmissionModel implements Parcelable{
    public String getGalleryId() {
        return galleryId;
    }

    public String getPicture() {
        return picture;
    }

    public String getArtId() {
        return artId;
    }

    public String getArtistId() {
        return artistId;
    }

    private String picture;
    private String artId;
    private String artistId;
    private String galleryId;

    public SubmissionModel(String galleryId, String picture, String artId, String artistId) {
        this.galleryId = galleryId;
        this.picture = picture;
        this.artId = artId;
        this.artistId = artistId;
    }

    protected SubmissionModel(Parcel in) {
        picture = in.readString();
        artId = in.readString();
        artistId = in.readString();
        galleryId = in.readString();
    }

    public static final Creator<SubmissionModel> CREATOR = new Creator<SubmissionModel>() {
        @Override
        public SubmissionModel createFromParcel(Parcel in) {
            return new SubmissionModel(in);
        }

        @Override
        public SubmissionModel[] newArray(int size) {
            return new SubmissionModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(picture);
        dest.writeString(artId);
        dest.writeString(artistId);
        dest.writeString(galleryId);
    }
}
