package com.exhibition.exhibition.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exhibition.exhibition.GalleryProfileActivity;
import com.exhibition.exhibition.R;
import com.exhibition.exhibition.models.Gallery;

import java.util.List;

/**
 * Created by yatinkaushal on 4/1/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private Context context;
    private List<Gallery> galleries;

    public GalleryAdapter(Context context, List<Gallery> galleries) {
        this.context = context;
        this.galleries = galleries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.galleryName.setText(galleries.get(position).name);
    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView galleryName;

        public ViewHolder(View itemView) {
            super(itemView);
            galleryName = (TextView) itemView.findViewById(R.id.galleryName);
            (itemView.findViewById(R.id.cardView)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GalleryProfileActivity.class);
                    intent.putExtra("gallery", galleries.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
