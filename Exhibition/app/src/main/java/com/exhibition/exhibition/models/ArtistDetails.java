package com.exhibition.exhibition.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yatinkaushal on 4/9/17.
 */
public class ArtistDetails {
    public Artist artist;
    public List<Art> arts = new ArrayList<>();

    public ArtistDetails(Artist artist) {
        this.artist = artist;
    }
}
