package com.exhibition.exhibition;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.exhibition.exhibition.adapters.GalleryAdapter;
import com.exhibition.exhibition.models.Gallery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Gallery> galleries = new ArrayList<>();
    private GalleryAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Galleries Near You");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new GalleryAdapter(this, galleries);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        galleries.clear();
        new GetGalleryList().execute();
        progressDialog.show();
    }

    private class GetGalleryList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                galleries.addAll(ApiHelper.getGalleries());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setUI();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private void setUI() {
        adapter.notifyDataSetChanged();
        progressDialog.hide();
    }
}
