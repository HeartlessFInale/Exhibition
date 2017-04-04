package com.exhibition.exhibition;

import com.exhibition.exhibition.models.Gallery;

import org.json.JSONArray;
import org.json.JSONException;

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
    private static final String URL = "http://69ce8e1c.ngrok.io/";

    private static String getGalleryList() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"getGalleryList?artist_id=1")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static List<Gallery> getGalleries() throws IOException {
        String s = getGalleryList();
        List<Gallery> galleries = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(s);
            for (int i = 0; i < array.length(); i++) {
                String name = array.getJSONObject(i).getString("name");
                String description = array.getJSONObject(i).getString("description");
                galleries.add(new Gallery(name, description));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return galleries;
    }

    private static String getGalleryDetails() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL+"getGalleryDetail?gallery_id=1")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
