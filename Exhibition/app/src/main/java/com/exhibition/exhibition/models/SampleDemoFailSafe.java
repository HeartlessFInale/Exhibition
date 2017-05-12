package com.exhibition.exhibition.models;

import java.util.ArrayList;

/**
 * Created by yatinkaushal on 5/12/17.
 */

//Fail Safe in case server dies?
public class SampleDemoFailSafe {

    public static ArrayList<Gallery> getGalleryList() {
        ArrayList<Gallery> galleries = new ArrayList<>();
        galleries.add(new Gallery("Gallery 1", "Gallery 1 description", 1, 1));
        galleries.add(new Gallery("Gallery 2", "Gallery 2 description", 2, 0));
        galleries.add(new Gallery("Gallery 3", "Gallery 3 description", 3, 0));
        return galleries;
    }

}
