package com.exhibition.exhibition;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.exhibition.exhibition.adapters.ArtAdapter;
import com.exhibition.exhibition.models.Art;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GallerySubmissionActivity extends AppCompatActivity {

    int galleryId;
    RecyclerView recyclerView;
    ArtAdapter adapter;
    List<Art> arts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_submission);
        getSupportActionBar().setTitle("Gallery Submissions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        galleryId = getIntent().getIntExtra("gallery_id", -1);
        if (galleryId == -1) {
            finish();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new ArtAdapter(this, arts, ArtAdapter.ACTION_ACCEPT_REJECT);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public void onResume(){
        super.onResume();
        arts.clear();
        adapter.notifyDataSetChanged();
        new GetSubmissions().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class GetSubmissions extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                arts.addAll(ApiHelper.getGallerySubmissions(galleryId));
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

    public void updateUI() {
        adapter.notifyDataSetChanged();
    }
}
