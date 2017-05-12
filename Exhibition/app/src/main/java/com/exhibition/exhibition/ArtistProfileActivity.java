package com.exhibition.exhibition;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.exhibition.exhibition.adapters.ArtAdapter;
import com.exhibition.exhibition.models.Art;
import com.exhibition.exhibition.models.Artist;
import com.exhibition.exhibition.models.ArtistDetails;
import com.exhibition.exhibition.models.RefreshableActivity;
import com.squareup.picasso.Picasso;

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
    TextView traits;
    private String reason;

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
        traits = (TextView) findViewById(R.id.traits);
        if (!artist.picture.isEmpty()) {
            Picasso.with(this)
                    .load(ApiHelper.URL + ApiHelper.IMAGES + artist.picture)
                    .into(circleImageView);
        }
        bio = (TextView) findViewById(R.id.bio);
        name = (TextView) findViewById(R.id.name);
        artRecyclerView = (RecyclerView) findViewById(R.id.artRecyclerView);
        artAdapter = new ArtAdapter(this, arts, ArtAdapter.ACTION_DELETE);
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
//        progressDialog.show();
    }

    private class GetArtistDetail extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                artistDetails = ApiHelper.getArtistDetails(artist.id, true);
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
        if (!TextUtils.isEmpty(artist.traits)) {
            traits.setText("Traits:\n" + artist.traits);
        }
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
        if (id == R.id.action_add_trait) {
            final EditText editText = new EditText(this);
            new AlertDialog.Builder(this)
                .setTitle("Add Trait")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input = editText.getText().toString();
                        if (!TextUtils.isEmpty(input.trim())) {
                            new AddTrait().execute(input);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .setView(editText)
                .create()
                    .show();
        }
        if (id == R.id.action_report_artist) {
            promptReport();
        }
        return super.onOptionsItemSelected(item);
    }

    private void promptReport() {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this)
                .setView(editText)
                .setTitle("Reason for Reporting")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reason = editText.getText().toString();
                        if (!TextUtils.isEmpty(reason)) {
                            new ReportArtist().execute();
                        } else {
                            Toast.makeText(ArtistProfileActivity.this, "Cannot leave field blank", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private class AddTrait extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                final String message = ApiHelper.addArtistTrait(params[0], 1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ArtistProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                        new GetArtistDetail().execute();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class ReportArtist extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ApiHelper.report(ApiHelper.ReportType.ARTIST, artist.id, reason);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
