package com.exhibition.exhibition.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exhibition.exhibition.ArtDetailActivity;
import com.exhibition.exhibition.ArtistProfileActivity;
import com.exhibition.exhibition.GalleryProfileActivity;
import com.exhibition.exhibition.R;
import com.exhibition.exhibition.models.Art;
import com.exhibition.exhibition.models.ArtResult;
import com.exhibition.exhibition.models.Artist;
import com.exhibition.exhibition.models.ArtistResult;
import com.exhibition.exhibition.models.Gallery;
import com.exhibition.exhibition.models.GalleryResult;
import com.exhibition.exhibition.models.SearchResult;

import java.util.List;

/**
 * Created by yatinkaushal on 4/25/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    boolean isArt;
    boolean isArtist;
    boolean isGallery;
    private List<Art> artResults;
    private List<Artist> artistResults;
    private List<Gallery> galleryResults;
    private boolean noResults;
    private Activity context;

    public SearchAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (noResults) {
            holder.textView.setText("No Results");
            return;
        }
        if (isArt) {
            holder.textView.setText(artResults.get(position).name);
        } else if (isArtist) {
            holder.textView.setText(artistResults.get(position).name);
        } else if (isGallery) {
            holder.textView.setText(galleryResults.get(position).name);
        }
    }

    @Override
    public int getItemCount() {
        int size = -1;
         if (artResults != null) {
             size = artResults.size();
         } else if (artistResults != null) {
             size = artistResults.size();
         } else if (galleryResults != null){
             size = galleryResults.size();
         }
        if (size == 0) {
            noResults = true;
            return 1;
        } else {
            noResults = false;
            return size;
        }
    }

    public void setArtResults(List<Art> artResults) {
        isArt = true;
        this.artResults = artResults;
    }

    public void setArtistResults(List<Artist> artistResults) {
        isArtist = true;
        this.artistResults = artistResults;
    }

    public void setGalleryResults(List<Gallery> galleryResults) {
        isGallery = true;
        this.galleryResults = galleryResults;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!noResults) {
                        if (isArt) {
                            Intent intent = new Intent(context, ArtDetailActivity.class);
                            intent.putExtra("art", artResults.get(getAdapterPosition()));
                            context.startActivity(intent);
                        } else if (isArtist) {
                            Intent intent = new Intent(context, ArtistProfileActivity.class);
                            intent.putExtra("artist", artistResults.get(getAdapterPosition()));
                            context.startActivity(intent);
                        } else if (isGallery) {
                            Intent intent = new Intent(context, GalleryProfileActivity.class);
                            intent.putExtra("gallery", galleryResults.get(getAdapterPosition()));
                            context.startActivity(intent);
                        }
                    }
                }

            });
        }



    }
}
