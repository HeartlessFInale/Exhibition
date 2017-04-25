package com.exhibition.exhibition.models;

import java.util.List;

/**
 * Created by yatinkaushal on 4/24/17.
 */

public class GalleryResult implements SearchResult {
    private List<Gallery> galleryList;
    private Type type;

    public GalleryResult(List<Gallery> galleries, Type type) {
        galleryList = galleries;
        this.type = type;
    }

    public List<Gallery> getGalleryList() {
        return galleryList;
    }

    public Type getType() {
        return type;
    }
}
