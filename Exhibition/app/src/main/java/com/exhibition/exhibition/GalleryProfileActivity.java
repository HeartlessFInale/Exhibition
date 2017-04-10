package com.exhibition.exhibition;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.exhibition.exhibition.adapters.ArtAdapter;
import com.exhibition.exhibition.adapters.ArtistAdapter;
import com.exhibition.exhibition.models.Art;
import com.exhibition.exhibition.models.Artist;
import com.exhibition.exhibition.models.ArtistDetails;
import com.exhibition.exhibition.models.Gallery;
import com.exhibition.exhibition.models.GalleryDetails;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GalleryProfileActivity extends AppCompatActivity {
    private RecyclerView artistRecyclerView;
    private RecyclerView artRecyclerView;
    private ArtistAdapter artistAdapter;
    private ArtAdapter artAdapter;
    private List<Artist> artists = new ArrayList<>();
    private List<Art> arts = new ArrayList<>();
    private Gallery gallery;
    private TextView name;
    private TextView description;
    private ImageView imageView;
    private ToggleButton likeToggle;
    private TextView year;
    private GalleryDetails galleryDetails;
    private ArtistDetails artistDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_profile);
        gallery = getIntent().getParcelableExtra("gallery");
        getSupportActionBar().setTitle(gallery.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = (TextView) findViewById(R.id.textView);
        description = (TextView) findViewById(R.id.textView4);
        likeToggle = (ToggleButton) findViewById(R.id.imageView2);
        imageView = (ImageView) findViewById(R.id.imageView);
        year = (TextView) findViewById(R.id.textView2);
        likeToggle.setChecked(gallery.isFav == 1);
        name.setText(gallery.name);
        description.setText(gallery.description);
        artistRecyclerView = (RecyclerView) findViewById(R.id.artistRecyclerView);
        artRecyclerView = (RecyclerView) findViewById(R.id.artRecyclerView);
//        for (int i = 0; i < 10; i++) {
//            artists.add(new Artist());
//        }
        artistAdapter = new ArtistAdapter(this, artists);
        artAdapter = new ArtAdapter(this, arts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        artistRecyclerView.setLayoutManager(layoutManager);
        artistRecyclerView.setAdapter(artistAdapter);
        artRecyclerView.setLayoutManager(layoutManager2);
        artRecyclerView.setAdapter(artAdapter);
        new GetGalleryDetails().execute();
    }

    private class GetGalleryDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                galleryDetails = ApiHelper.getGalleryDetails(gallery.id);
                artists.addAll(galleryDetails.artists);
                arts.addAll(galleryDetails.arts);
            } catch (Exception e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateUI();
                }
            });
            return null;
        }
    }

    private void updateUI() {
        artAdapter.notifyDataSetChanged();
        artistAdapter.notifyDataSetChanged();
        year.setText("Since " + galleryDetails.gallery.year);
        if (!TextUtils.isEmpty(galleryDetails.gallery.photo)) {
            Glide.with(this)
                    .load(ApiHelper.URL + ApiHelper.IMAGES + galleryDetails.gallery.photo)
                    .into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_submit) {
            new GetMyArts().execute();
        }
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void submitArtwork() {
        View view = View.inflate(this, R.layout.choose_arts, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ArtAdapter artAdapter2 = new ArtAdapter(this, artistDetails.arts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(artAdapter2);
        new AlertDialog.Builder(this)
                .setTitle("Which would you like to submit?")
                .setView(view)
                .create()
                .show();
    }

    private class GetMyArts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                artistDetails = ApiHelper.getArtistDetails(1);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    submitArtwork();
                }
            });
            return null;
        }
    }

}
