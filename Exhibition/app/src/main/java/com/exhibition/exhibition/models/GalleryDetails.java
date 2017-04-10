package com.exhibition.exhibition.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yatinkaushal on 4/9/17.
 */

public class GalleryDetails {
    public List<Artist> artists = new ArrayList<>();
    public List<Art> arts = new ArrayList<>();
    public Gallery gallery;

    public GalleryDetails(Gallery gallery) {
        this.gallery = gallery;
    }

}
