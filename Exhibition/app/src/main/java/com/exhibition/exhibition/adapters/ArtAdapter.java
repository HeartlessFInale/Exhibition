package com.exhibition.exhibition.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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
import com.exhibition.exhibition.models.RefreshableActivity;

import java.io.IOException;
import java.util.List;

/**
 * Created by yatinkaushal on 4/1/17.
 */

public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ViewHolder> {
    private Context context;
    private List<Art> artists;
    private boolean canDelete;
    private RefreshableActivity refreshableActivity;

    public ArtAdapter(Context context, List<Art> artists) {
        this.context = context;
        this.artists = artists;
    }

    public ArtAdapter(Context context, List<Art> arts, RefreshableActivity refreshableActivity) {
        this.context = context;
        this.artists = arts;
        this.refreshableActivity = refreshableActivity;
        canDelete = true;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(ApiHelper.URL + ApiHelper.IMAGES + artists.get(position).picture)
                .into(holder.imageView);
        holder.textView.setText(artists.get(position).name);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            (itemView.findViewById(R.id.cardView)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Art Page coming soon!", Toast.LENGTH_SHORT).show();
                }
            });
            if (canDelete) {
                cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog.Builder(context)
                                .setTitle("Delete Art?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new DeleteArtWork().execute(String.valueOf(artists.get(getAdapterPosition()).id));
                                    }
                                })
                                .setNegativeButton("No", null)
                                .create()
                                .show();
                        return false;
                    }
                });
            }
        }
    }

    private class DeleteArtWork extends AsyncTask <String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                ApiHelper.deleteArt(params[0]);
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshableActivity.refresh();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
