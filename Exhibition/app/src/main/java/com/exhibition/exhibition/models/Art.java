package com.exhibition.exhibition.models;

/**
 * Created by yatinkaushal on 4/9/17.
 */
public class Art {
    public int id;
    public int artist_id;
    public String description;
    public String name;
    public String picture;

    public Art() {}

    public Art(int id, String description, String name, String picture, int artist_id) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.picture = picture;
        this.artist_id = artist_id;
    }
}
