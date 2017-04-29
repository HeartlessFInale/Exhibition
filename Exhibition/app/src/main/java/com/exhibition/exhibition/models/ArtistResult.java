package com.exhibition.exhibition.models;

import java.util.List;

/**
 * Created by yatinkaushal on 4/24/17.
 */

public class ArtistResult implements SearchResult {
    private List<Artist> artistList;
    private Type type;

    public ArtistResult(List<Artist> artists, Type type) {
        artistList = artists;
        this.type = type;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public Type getType() {
        return type;
    }
}
