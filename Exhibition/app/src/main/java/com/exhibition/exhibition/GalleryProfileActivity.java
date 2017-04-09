package com.exhibition.exhibition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.exhibition.exhibition.adapters.ArtistAdapter;
import com.exhibition.exhibition.models.Artist;
import com.exhibition.exhibition.models.Gallery;

import java.util.ArrayList;
import java.util.List;

public class GalleryProfileActivity extends AppCompatActivity {
    private RecyclerView artistRecyclerView;
    private RecyclerView artRecyclerView;
    private ArtistAdapter artistAdapter;
    private ArtistAdapter artAdapter;
    private List<Artist> artists = new ArrayList<>();
    private Gallery gallery;
    private TextView name;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_profile);
        gallery = getIntent().getParcelableExtra("gallery");
        name = (TextView) findViewById(R.id.textView);
        description = (TextView) findViewById(R.id.textView4);
        name.setText(gallery.name);
        description.setText(gallery.description);
        artistRecyclerView = (RecyclerView) findViewById(R.id.artistRecyclerView);
        artRecyclerView = (RecyclerView) findViewById(R.id.artRecyclerView);
        for (int i = 0; i < 10; i++) {
            artists.add(new Artist());
        }
        artistAdapter = new ArtistAdapter(this, artists);
        artAdapter = new ArtistAdapter(this, artists);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        artistRecyclerView.setLayoutManager(layoutManager);
        artistRecyclerView.setAdapter(artistAdapter);
        artRecyclerView.setLayoutManager(layoutManager2);
        artRecyclerView.setAdapter(artAdapter);
    }
}
