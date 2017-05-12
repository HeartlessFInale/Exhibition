package com.exhibition.exhibition;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exhibition.exhibition.models.Art;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AcceptRejectArtworkActivity extends AppCompatActivity {

    Art art;
    TextView title;
    Button accept;
    Button reject;
    String reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_reject_artwork);
        art = getIntent().getParcelableExtra("art");
        getSupportActionBar().setTitle(art.name);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this)
                .load(ApiHelper.URL + ApiHelper.IMAGES + art.picture)
                .into(imageView);
        title = ((TextView) findViewById(R.id.artTitle));
        title.setText(art.name);
        accept = (Button) findViewById(R.id.accept);
        reject = (Button) findViewById(R.id.reject);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptReason(true);
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptReason(false);
            }
        });
    }

    private void promptReason(final boolean isAccepted) {
        final EditText editText = new EditText(this);
        String prompt = isAccepted ? "Reason for Acceptance" : "Reason for Rejection";
        new AlertDialog.Builder(this)
                .setView(editText)
                .setTitle(prompt)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reason = editText.getText().toString();
                        if (!TextUtils.isEmpty(reason)) {
                            new AcceptRejectSubmission().execute(isAccepted);
                        } else {
                            Toast.makeText(AcceptRejectArtworkActivity.this, "Cannot leave field blank", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private class AcceptRejectSubmission extends AsyncTask<Boolean, Void, Void> {

        @Override
        protected Void doInBackground(Boolean... params) {
            try {
                ApiHelper.acceptRejectArt(art.submissionId, params[0], reason);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
