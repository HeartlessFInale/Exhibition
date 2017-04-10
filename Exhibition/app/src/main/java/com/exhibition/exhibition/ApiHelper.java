package com.exhibition.exhibition;

import android.util.Log;

import com.exhibition.exhibition.models.Art;
import com.exhibition.exhibition.models.Artist;
import com.exhibition.exhibition.models.ArtistDetails;
import com.exhibition.exhibition.models.Gallery;
import com.exhibition.exhibition.models.GalleryDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yatinkaushal on 4/4/17.
 */

public class ApiHelper {
    public static final String URL = "http://cc101a62.ngrok.io/";
    public static final String IMAGES = "images/";

    private static String getGalleryList() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"getGalleryList?artist_id=1")
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper", s);
        return s;
    }

    public static List<Gallery> getGalleries() throws IOException {
        String s = getGalleryList();
        List<Gallery> galleries = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(s);
            for (int i = 0; i < array.length(); i++) {
                galleries.add(convertJSONObjectToGallery(array.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return galleries;
    }

    public static GalleryDetails getGalleryDetails(int id) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"getGalleryDetail?gallery_id=" + id)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper", s);
        JSONObject jsonObject = new JSONObject(s);
        List<Artist> artists = convertJSONArrayToArtist(jsonObject.getJSONArray("gallery_artist"));
        List<Art> arts = convertJSONArrayToArt(jsonObject.getJSONArray("gallery_art"));
        Gallery gallery = convertJSONObjectToGalleryDetail(jsonObject.getJSONObject("gallery_detail"));
        GalleryDetails galleryDetails = new GalleryDetails(gallery);
        galleryDetails.artists.addAll(artists);
        galleryDetails.arts.addAll(arts);
        galleryDetails.gallery = gallery;
        return galleryDetails;
    }

    public static ArtistDetails getArtistDetails(int id) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"getArtistDetail?artist_id=" + id)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: Artist", s);
        JSONObject jsonObject = new JSONObject(s);
        ArtistDetails artistDetails = new ArtistDetails(convertJSONObjectToArtist(jsonObject.getJSONObject("artist_detail")));
        artistDetails.arts.addAll(convertJSONArrayToArt(jsonObject.getJSONArray("artworks")));
        return artistDetails;
    }

    private static Artist convertJSONObjectToArtist(JSONObject jsonObject) throws JSONException {
        return new Artist(jsonObject.getString("name"), jsonObject.getInt("artist_id"), jsonObject.getString("picture"), jsonObject.getString("description"));
    }

    public static Gallery convertJSONObjectToGalleryDetail(JSONObject jsonObject) throws JSONException {
        Gallery gallery = convertJSONObjectToGallery(jsonObject);
        gallery.year = jsonObject.getInt("year");
        gallery.photo = jsonObject.getString("photo");
        return gallery;
    }

    public static Gallery convertJSONObjectToGallery(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        int id = jsonObject.getInt("gallery_id");
        int isFav = jsonObject.getInt("is_fav");
        String photo = jsonObject.getString("photo");
        return new Gallery(name, description, id, isFav, photo);
    }

    public static List<Artist> convertJSONArrayToArtist(JSONArray jsonArray) throws JSONException {
        List<Artist> artists = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            String picture = jsonObject.getString("picture");
            int id = jsonObject.getInt("artist_id");
            artists.add(new Artist(name, id, picture));
        }
        return artists;
    }

    public static List<Art> convertJSONArrayToArt(JSONArray jsonArray) throws JSONException {
        List<Art> arts = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            String picture = jsonObject.getString("picture");
            String description = jsonObject.getString("description");
            int id = jsonObject.getInt("art_id");
            arts.add(new Art(id, description, name, picture));
        }
        return arts;
    }

}
