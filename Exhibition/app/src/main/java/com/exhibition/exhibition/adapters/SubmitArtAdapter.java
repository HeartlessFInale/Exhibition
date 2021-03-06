package com.exhibition.exhibition.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.exhibition.exhibition.ApiHelper;
import com.exhibition.exhibition.R;
import com.exhibition.exhibition.models.Art;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yatinkaushal on 4/1/17.
 */

public class SubmitArtAdapter extends RecyclerView.Adapter<SubmitArtAdapter.ViewHolder> {
    private Context context;
    private List<Art> artists;

    public SubmitArtAdapter(Context context, List<Art> artists) {
        this.context = context;
        this.artists = artists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(ApiHelper.URL + ApiHelper.IMAGES + artists.get(position).picture)
                .into(holder.imageView);
        holder.textView.setText(artists.get(position).name);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            (itemView.findViewById(R.id.cardView)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Art Page coming soon!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
