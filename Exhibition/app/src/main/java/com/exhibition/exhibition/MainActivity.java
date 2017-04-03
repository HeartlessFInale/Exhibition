package com.exhibition.exhibition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.exhibition.exhibition.adapters.GalleryAdapter;
import com.exhibition.exhibition.models.Gallery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //MainActivity

    private RecyclerView recyclerView;
    private List<Gallery> galleries = new ArrayList<>();
    private GalleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        for (int i = 0; i < 10; i++) {
            galleries.add(new Gallery());
        }
        adapter = new GalleryAdapter(this, galleries);
        recyclerView.setAdapter(adapter);
    }
}
