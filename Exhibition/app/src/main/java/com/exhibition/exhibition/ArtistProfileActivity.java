package com.exhibition.exhibition;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.exhibition.exhibition.adapters.ArtAdapter;
import com.exhibition.exhibition.models.Art;
import com.exhibition.exhibition.models.Artist;
import com.exhibition.exhibition.models.ArtistDetails;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistProfileActivity extends AppCompatActivity {
    Artist artist;
    CircleImageView circleImageView;
    TextView bio;
    TextView name;
    private ArtistDetails artistDetails;
    private RecyclerView artRecyclerView;
    private ArtAdapter artAdapter;
    private List<Art> arts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        artist = getIntent().getParcelableExtra("artist");
        getSupportActionBar().setTitle(artist.name);
        circleImageView = (CircleImageView) findViewById(R.id.artistProfile);
        if (!artist.picture.isEmpty()) {
            Glide.with(this)
                    .load(ApiHelper.URL + ApiHelper.IMAGES + artist.picture)
                    .into(circleImageView);
        }
        bio = (TextView) findViewById(R.id.bio);
        bio.setText(artist.description);
        name = (TextView) findViewById(R.id.name);
        name.setText(artist.name);
        artRecyclerView = (RecyclerView) findViewById(R.id.artRecyclerView);
        artAdapter = new ArtAdapter(this, arts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        artRecyclerView.setLayoutManager(layoutManager);
        artRecyclerView.setAdapter(artAdapter);
        new GetArtistDetail().execute();
    }

    private class GetArtistDetail extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                artistDetails = ApiHelper.getArtistDetails(artist.id);
                arts.addAll(artistDetails.arts);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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
        bio.setText(artistDetails.artist.description);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
