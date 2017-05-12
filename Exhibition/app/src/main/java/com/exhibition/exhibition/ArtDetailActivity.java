package com.exhibition.exhibition;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exhibition.exhibition.models.Art;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;

public class ArtDetailActivity extends AppCompatActivity {
    TextView title;
    TextView traits;
    EditText editText;
    Button button;
    Art art;
    String traitsHeader = "Traits:\n";
    private String reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        art = getIntent().getParcelableExtra("art");
        getSupportActionBar().setTitle(art.name);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this)
                .load(ApiHelper.URL + ApiHelper.IMAGES + art.picture)
                .into(imageView);
        title = ((TextView) findViewById(R.id.artTitle));
        traits = ((TextView) findViewById(R.id.artTraits));
        if (!TextUtils.isEmpty(art.traits)) {
            traits.setText(traitsHeader + art.traits);
        }
        final EditText editText = (EditText) findViewById(R.id.editText);
        button = ((Button) findViewById(R.id.button));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length() != 0) {
                    new AddTrait().execute(String.valueOf(art.id), editText.getText().toString());
                    editText.setText("");
                } else {
                    Toast.makeText(ArtDetailActivity.this, "Invalid trait", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateUI();
    }

    private class AddTrait extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                final String s = ApiHelper.addTrait(params[0], params[1]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ArtDetailActivity.this, s, Toast.LENGTH_SHORT).show();
                        updateUI();
                        new UpdateArt().execute();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_art, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.action_report_art) {
            promptReport();
        }
        return super.onOptionsItemSelected(item);
    }

    private void promptReport() {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this)
                .setView(editText)
                .setTitle("Reason for Reporting")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reason = editText.getText().toString();
                        if (!TextUtils.isEmpty(reason)) {
                            new ReportArt().execute();
                        } else {
                            Toast.makeText(ArtDetailActivity.this, "Cannot leave field blank", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private class UpdateArt extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                art = ApiHelper.getArtDetails(art.id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
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

    private void updateUI() {
        title.setText(art.name);
        if (!TextUtils.isEmpty(art.traits)) {
            traits.setText(traitsHeader + art.traits);
        }
    }

    private class ReportArt extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                ApiHelper.report(ApiHelper.ReportType.ART, art.id, reason);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
