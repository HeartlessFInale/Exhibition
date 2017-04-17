package com.exhibition.exhibition;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
    public static final String URL = "http://1535b42c.ngrok.io/";
    public static final String IMAGES = "images/";

    private static String getGalleryList(int id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"getGalleryList?artist_id=" + id)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: getGalleryL", s);
        return s;
    }

    public static List<Gallery> getGalleries(int id) throws IOException {
        String s = getGalleryList(id);
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

    public static GalleryDetails getGalleryDetails(int id, int artistId) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"getGalleryDetail?gallery_id=" + id)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: getGalleryDs", s);
        JSONObject jsonObject = new JSONObject(s);
        List<Artist> artists = convertJSONArrayToArtist(jsonObject.getJSONArray("gallery_artist"));
        List<Art> arts = convertJSONArrayToArt(jsonObject.getJSONArray("gallery_art"), artistId);
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
        Log.d("ApiHelper: getArtistDs", s);
        JSONObject jsonObject = new JSONObject(s);
        ArtistDetails artistDetails = new ArtistDetails(convertJSONObjectToArtist(jsonObject.getJSONObject("artist_detail")));
        artistDetails.arts.addAll(convertJSONArrayToArt(jsonObject.getJSONArray("artworks"), id));
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

    public static List<Art> convertJSONArrayToArt(JSONArray jsonArray, int artist_id) throws JSONException {
        List<Art> arts = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            String picture = jsonObject.getString("picture");
            String description = jsonObject.getString("description");
            int id = jsonObject.getInt("art_id");
            arts.add(new Art(id, description, name, picture, artist_id));
        }
        return arts;
    }

    public static void deleteArt(String artId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"deleteArt?art_id=" + artId)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: deleteArt", s);
    }

    public static String submitArt(Context context, String artId, String artistId, String galleryId) throws IOException, JSONException {
        Log.d("ApiHelper: submitArt1", artId + " " + artistId + " " + galleryId);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"addSubmission?art_id=" + artId + "&artist_id=" + artistId + "&gallery_id=" + galleryId)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: submitArt2", s);
        JSONObject jsonObject = new JSONObject(s);
        String s2 = jsonObject.getString("return_status");
        return s2;
    }
}
