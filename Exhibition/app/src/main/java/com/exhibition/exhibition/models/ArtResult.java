package com.exhibition.exhibition.models;

import java.util.List;

/**
 * Created by yatinkaushal on 4/24/17.
 */

public class ArtResult implements SearchResult {
    private List<Art> artList;
    private Type type;

    public ArtResult(List<Art> arts, Type type) {
        artList = arts;
        this.type = type;
    }

    public List<Art> getArtList() {
        return artList;
    }

    public Type getType() {
        return type;
    }

}
