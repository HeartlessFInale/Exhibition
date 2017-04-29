package com.exhibition.exhibition;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exhibition.exhibition.models.Art;

import org.json.JSONException;

import java.io.IOException;

public class ArtDetailActivity extends AppCompatActivity {
    TextView title;
    TextView traits;
    EditText editText;
    Button button;
    Art art;
    String traitsTxt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_detail);
        art = getIntent().getParcelableExtra("art");
        traitsTxt += art.traits;
        title = ((TextView) findViewById(R.id.artTitle));
        traits = ((TextView) findViewById(R.id.artTraits));
        final EditText editText = (EditText) findViewById(R.id.editText);
        button = ((Button) findViewById(R.id.button));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length() != 0) {
                    traitsTxt += editText.getText().toString() + ", ";
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
//                        new UpdateArt().execute();
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

    private class UpdateArt extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ApiHelper.getArtDetails(art.id);
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
        if (!art.traits.equals("null")) {
            traits.setText("Traits\n\n" + traitsTxt);
        }
    }
}
