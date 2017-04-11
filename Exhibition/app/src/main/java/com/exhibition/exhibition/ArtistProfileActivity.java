package com.exhibition.exhibition;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.exhibition.exhibition.adapters.ArtAdapter;
import com.exhibition.exhibition.models.Art;
import com.exhibition.exhibition.models.Artist;
import com.exhibition.exhibition.models.ArtistDetails;
import com.exhibition.exhibition.models.RefreshableActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistProfileActivity extends AppCompatActivity implements RefreshableActivity {
    private static final int PICK_IMAGE = 1;
    Artist artist;
    CircleImageView circleImageView;
    TextView bio;
    TextView name;
    private ArtistDetails artistDetails;
    private RecyclerView artRecyclerView;
    private ArtAdapter artAdapter;
    private List<Art> arts = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        artist = getIntent().getParcelableExtra("artist");
        getSupportActionBar().setTitle(artist.name);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        circleImageView = (CircleImageView) findViewById(R.id.artistProfile);
        if (!artist.picture.isEmpty()) {
            Glide.with(this)
                    .load(ApiHelper.URL + ApiHelper.IMAGES + artist.picture)
                    .into(circleImageView);
        }
        bio = (TextView) findViewById(R.id.bio);
        name = (TextView) findViewById(R.id.name);
        artRecyclerView = (RecyclerView) findViewById(R.id.artRecyclerView);
        artAdapter = new ArtAdapter(this, arts, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        artRecyclerView.setLayoutManager(layoutManager);
        artRecyclerView.setAdapter(artAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void refresh() {
        arts.clear();
        new GetArtistDetail().execute();
        progressDialog.show();
    }

    private class GetArtistDetail extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                artistDetails = ApiHelper.getArtistDetails(artist.id);
                artist = artistDetails.artist;
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
        bio.setText(artist.description);
        name.setText(artist.name);
        bio.setText(artist.description);
        progressDialog.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_artist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.action_submit) {
            Intent intent = new Intent(ArtistProfileActivity.this, UploadImageActivity.class);
            intent.putExtra("artist", artist);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
