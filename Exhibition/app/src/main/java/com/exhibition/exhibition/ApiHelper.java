package com.exhibition.exhibition;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.exhibition.exhibition.models.Art;
import com.exhibition.exhibition.models.ArtResult;
import com.exhibition.exhibition.models.Artist;
import com.exhibition.exhibition.models.ArtistDetails;
import com.exhibition.exhibition.models.ArtistResult;
import com.exhibition.exhibition.models.Gallery;
import com.exhibition.exhibition.models.GalleryDetails;
import com.exhibition.exhibition.models.GalleryResult;
import com.exhibition.exhibition.models.SearchResult;

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
    public static final String URL = "http://f339ce17.ngrok.io/";
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

    public static ArtistDetails getArtistDetails(int id, boolean isProfile) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"getArtistDetail?artist_id=" + id)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: getArtistDs", s);
        JSONObject jsonObject = new JSONObject(s);
        if (!isProfile) {
            ArtistDetails artistDetails = new ArtistDetails(convertJSONObjectToArtist(jsonObject.getJSONObject("artist_detail")));
            artistDetails.arts.addAll(convertJSONArrayToArt(jsonObject.getJSONArray("artworks"), id));
            return artistDetails;
        } else {
            ArtistDetails artistDetails = new ArtistDetails(convertJSONObjectToArtistProfile(jsonObject.getJSONObject("artist_detail")));
            artistDetails.arts.addAll(convertJSONArrayToArt(jsonObject.getJSONArray("artworks"), id));
            return artistDetails;
        }
    }

    private static Artist convertJSONObjectToArtistProfile(JSONObject jsonObject) throws JSONException {
        return new Artist(jsonObject.getString("name"), jsonObject.getInt("artist_id"), jsonObject.getString("picture"), jsonObject.getString("description"), jsonObject.getString("traits"));
    }

    public static Art getArtDetails(int id) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"getArtDetail?art_id=" + id)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: getArtDs", s);
        JSONArray jsonArray = new JSONArray(s);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        return new Art(jsonObject.getInt("art_id"), jsonObject.getString("description"), jsonObject.getString("name"), jsonObject.getString("picture"), jsonObject.getInt("artist_id"), jsonObject.getString("traits"));
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
        if (jsonObject.has("traits")) {
            String traits = jsonObject.getString("traits");
            return new Gallery(name, description, id, isFav, photo, traits);
        }
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
            String traits = jsonObject.getString("traits");
            arts.add(new Art(id, description, name, picture, artist_id, traits));
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

    public static String submitArt(String artId, String artistId, String galleryId) throws IOException, JSONException {
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

    public static String addTrait(String artId, String trait) throws IOException, JSONException {
        Log.d("ApiHelper: addTrait1", artId + " " + trait);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"addArtTrait?art_id=" + artId + "&trait=" + trait)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: addTrait2", s);
        JSONObject jsonObject = new JSONObject(s);
        String s2 = jsonObject.getString("status");
        return s2;
    }
    ///search?search_term=Blue&artist_id=1

    public static List<SearchResult> search(String artistId, String query) throws IOException, JSONException {
        Log.d("ApiHelper: search1", artistId + " " + query);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"search?search_term=" + query + "&artist_id=" + artistId)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: search2", s);
        JSONObject jsonObject = new JSONObject(s);
        List<SearchResult> searchResults = new ArrayList<>();
        try {
            ArtResult artResult = new ArtResult(convertJSONArrayToArtResult(jsonObject.getJSONArray("art_list")), SearchResult.Type.ART);
            searchResults.add(artResult);
        } catch (JSONException e) {
            List<Art> arts = new ArrayList<>();
            arts.add(convertJSONObjectToArtResult(jsonObject.getJSONObject("art_list")));
            ArtResult artResult = new ArtResult(arts, SearchResult.Type.ART);
            searchResults.add(artResult);
        }
        try {
            ArtistResult artistResult = new ArtistResult(convertJSONArrayToArtistResult(jsonObject.getJSONArray("artist_list")), SearchResult.Type.ARTIST);
            searchResults.add(artistResult);
        } catch (JSONException e) {
            List<Artist> artists = new ArrayList<>();
            artists.add(convertJSONObjectToArtistResult(jsonObject.getJSONObject("artist_list")));
            ArtistResult artistResult = new ArtistResult(artists, SearchResult.Type.ARTIST);
            searchResults.add(artistResult);
        }
        try {
            GalleryResult galleryResult = new GalleryResult(convertJSONArrayToGalleryResult(jsonObject.getJSONArray("gallery_list")), SearchResult.Type.GALLERY);
            searchResults.add(galleryResult);
        } catch (JSONException e) {
            List<Gallery> galleries = new ArrayList<>();
            galleries.add(convertJSONObjectToGalleryResult(jsonObject.getJSONObject("gallery_list")));
            GalleryResult galleryResult = new GalleryResult(galleries, SearchResult.Type.GALLERY);
            searchResults.add(galleryResult);
        }
        return searchResults;
    }

    private static Gallery convertJSONObjectToGalleryResult(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("traits")) {
            return new Gallery(jsonObject.getString("name"), jsonObject.getString("description"), jsonObject.getInt("gallery_id"), jsonObject.getInt("is_fav"), jsonObject.getString("photo"), jsonObject.getString("traits"));
        }
        return new Gallery(jsonObject.getString("name"), jsonObject.getString("description"), jsonObject.getInt("gallery_id"), jsonObject.getInt("is_fav"), jsonObject.getString("photo"));
    }

    private static Artist convertJSONObjectToArtistResult(JSONObject jsonObject) throws JSONException {
        return new Artist(jsonObject.getString("name"), jsonObject.getInt("artist_id"), jsonObject.getString("picture"), jsonObject.getString("description"));
    }

    private static Art convertJSONObjectToArtResult(JSONObject jsonObject) throws JSONException {
        return new Art(jsonObject.getInt("art_id"), jsonObject.getString("description"), jsonObject.getString("name"), jsonObject.getString("picture"), 1, jsonObject.getString("traits"));
    }

    private static List<Art> convertJSONArrayToArtResult(JSONArray artList) throws JSONException {
        List<Art> arts = new ArrayList<>();
        for (int i = 0; i < artList.length(); i++) {
            JSONObject jsonObject = artList.getJSONObject(i);
            arts.add(new Art(jsonObject.getInt("art_id"), jsonObject.getString("description"), jsonObject.getString("name"), jsonObject.getString("picture")));
        }
        return arts;
    }

    private static List<Artist> convertJSONArrayToArtistResult(JSONArray artistList) throws JSONException {
        List<Artist> artists = new ArrayList<>();
        for (int i = 0; i < artistList.length(); i++) {
            JSONObject jsonObject = artistList.getJSONObject(i);
            artists.add(new Artist(jsonObject.getString("name"), jsonObject.getInt("artist_id"), jsonObject.getString("picture"), jsonObject.getString("description")));
        }
        return artists;
    }

    private static List<Gallery> convertJSONArrayToGalleryResult(JSONArray galleryList) throws JSONException {
        List<Gallery> galleries = new ArrayList<>();
        for (int i = 0; i < galleryList.length(); i++) {
            JSONObject jsonObject = galleryList.getJSONObject(i);
            if (jsonObject.has("traits")) {
                galleries.add(new Gallery(jsonObject.getString("name"), jsonObject.getString("description"), jsonObject.getInt("gallery_id"), jsonObject.getString("photo"), jsonObject.getString("traits")));
            }
            galleries.add(new Gallery(jsonObject.getString("name"), jsonObject.getString("description"), jsonObject.getInt("gallery_id"), jsonObject.getString("photo")));
        }
        return galleries;
    }

    public static String addArtistTrait(String trait, int artistId) throws IOException, JSONException {
        Log.d("ApiHelper: addArtistT1", artistId + " " + trait);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"addArtistTrait?artist_id=" + artistId + "&trait=" + trait)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: addArtistT2", s);
        JSONObject jsonObject = new JSONObject(s);
        return jsonObject.getString("status");
    }

    public static String addGalleryTrait(String trait, int galleryId) throws IOException, JSONException {
        Log.d("ApiHelper: addGalleryT1", galleryId + " " + trait);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"addGalleryTrait?gallery_id=" + galleryId + "&trait=" + trait)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: addGalleryT2", s);
        JSONObject jsonObject = new JSONObject(s);
        return jsonObject.getString("status");
    }

    public static List<SearchResult> searchTrait(String trait) throws IOException, JSONException {
        Log.d("ApiHelper: searchTrait1", trait);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"searchTrait?search_term=" + trait)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: searchTrait2", s);
        JSONObject jsonObject = new JSONObject(s);
        List<SearchResult> searchResults = new ArrayList<>();
        ArtResult artResult = new ArtResult(convertJSONArrayToArtResult(jsonObject.getJSONArray("art_list")), SearchResult.Type.ART);
        searchResults.add(artResult);
        ArtistResult artistResult = new ArtistResult(convertJSONArrayToArtistResult(jsonObject.getJSONArray("artist_list")), SearchResult.Type.ARTIST);
        searchResults.add(artistResult);
        GalleryResult galleryResult = new GalleryResult(convertJSONArrayToGalleryResult(jsonObject.getJSONArray("gallery_list")), SearchResult.Type.GALLERY);
        searchResults.add(galleryResult);
        return searchResults;
    }

    public static void addFavGallery(int galleryId, int artistId) throws IOException {
        Log.d("ApiHelper: addGallery1", "galleryId: " + galleryId);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"addFavGallery?artist_id=" + artistId + "&gallery_id=" + galleryId)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: addGallery2", s);
    }

    public static void deleteFavGallery(int galleryId, int artistId) throws IOException {
        Log.d("ApiHelper: delGallery1", "galleryId: " + galleryId);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"deleteFavGallery?artist_id=" + artistId + "&gallery_id=" + galleryId)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: delGallery2", s);
    }

    public static List<Art> getGallerySubmissions(int galleryId) throws IOException, JSONException {
        Log.d("ApiHelper: getSubm1", "galleryId: " + galleryId);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"getSubmissions?gallery_id=" + galleryId)
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        Log.d("ApiHelper: getSubm2", s);
        List<Art> arts = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(s);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            arts.add(new Art(jsonObject.getInt("art_id"), jsonObject.getString("name"), jsonObject.getString("picture")));
        }
        return arts;
    }
}
