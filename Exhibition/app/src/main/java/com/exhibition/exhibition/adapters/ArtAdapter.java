package com.exhibition.exhibition.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.exhibition.exhibition.ArtDetailActivity;
import com.exhibition.exhibition.R;
import com.exhibition.exhibition.models.Art;
import com.exhibition.exhibition.models.RefreshableActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by yatinkaushal on 4/1/17.
 */

public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ViewHolder> {
    private Context context;
    private List<Art> artists;
    private RefreshableActivity refreshableActivity;
    private int action;
    private int galleryId;

    public final static int ACTION_VIEW = 1;
    public final static int ACTION_DELETE = 2;
    public final static int ACTION_SUBMIT = 3;
    private AlertDialog alertDialog;

    public ArtAdapter(Context context, List<Art> artists, int action) {
        this.context = context;
        this.artists = artists;
        this.action = action;
        if (action == ACTION_DELETE || action == ACTION_SUBMIT) {
            this.refreshableActivity = (RefreshableActivity) context;
        }
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

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
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
            if (action != ACTION_SUBMIT) {
                (itemView.findViewById(R.id.cardView)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(context, "Art Page coming soon!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ArtDetailActivity.class);
                        intent.putExtra("art", artists.get(getAdapterPosition()));
                        context.startActivity(intent);
                    }
                });
            } else {
                (itemView.findViewById(R.id.cardView)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(context, SubmitArtActivity.class);
//                        SubmissionModel submissionModel = new SubmissionModel(String.valueOf(galleryId), artists.get(getAdapterPosition()).picture, String.valueOf(artists.get(getAdapterPosition()).id), String.valueOf(artists.get(getAdapterPosition()).artist_id));
//                        intent.putExtra("submission_model", submissionModel);
//                        context.startActivity(intent);
                        alertDialog.dismiss();
                        showConfirmationDialog(getAdapterPosition());
                    }
                });
            }
            if (action == ACTION_DELETE) {
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

    private void showConfirmationDialog(final int position) {
        ImageView imageView = new ImageView(context);
        Picasso.with(context)
                .load(ApiHelper.URL + ApiHelper.IMAGES + artists.get(position).picture)
                .into(imageView);
        alertDialog = new AlertDialog.Builder(context)
                .setTitle("Submit This Artwork?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SubmitArtWork().execute(String.valueOf(artists.get(position).id), String.valueOf(artists.get(position).artist_id), String.valueOf(galleryId));
                    }
                })
                .setNegativeButton("No", null)
                .setView(imageView)
                .create();
        alertDialog.show();
    }

    private class DeleteArtWork extends AsyncTask <String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                ApiHelper.deleteArt(params[0]);
//                alertDialog.dismiss();
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

    private class SubmitArtWork extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                final String status = ApiHelper.submitArt(params[0], params[1], params[2]);
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
                        refreshableActivity.refresh();
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
}
