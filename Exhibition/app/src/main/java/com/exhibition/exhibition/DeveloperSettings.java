package com.exhibition.exhibition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.exhibition.exhibition.models.FileManager;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DeveloperSettings extends AppCompatActivity {

    private String addArtistServerUri = ApiHelper.URL + "addNewArtist";

    private Button chooseArtistPicBtn;
    private Button addArtistBtn;
    private ImageView imageView;
    private EditText artistName;
    private EditText artistDescription;
    private static final int PICK_ARTIST_IMAGE = 1;
    private String artistImagepath;

    private Button chooseGalleryPicBtn;
    private Button addGalleryBtn;
    private ImageView imageView2;
    private EditText galleryName;
    private EditText galleryDescription;
    private static final int PICK_GALLERY_IMAGE = 2;
    private String galleryImagepath;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE2 = 2;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Uri selectedArtistImageUri;
    private Uri selectedGalleryImageUri;
    private String addGalleryServerUri = ApiHelper.URL + "addNewGallery";
    private EditText galleryYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_settings);
        getSupportActionBar().setTitle("Developer Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chooseArtistPicBtn = (Button) findViewById(R.id.choosePicBtn);
        chooseGalleryPicBtn = (Button) findViewById(R.id.chooseGalleryPicBtn);
        addArtistBtn = (Button) findViewById(R.id.addArtistBtn);
        addGalleryBtn = (Button) findViewById(R.id.addGalleryBtn);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView = (ImageView) findViewById(R.id.imageView);
        artistName = (EditText) findViewById(R.id.artistName);
        galleryName = (EditText) findViewById(R.id.galleryName);
        galleryYear = (EditText) findViewById(R.id.galleryYear);
        galleryDescription = (EditText) findViewById(R.id.galleryDescription);
        artistDescription = (EditText) findViewById(R.id.artistDescription);
        chooseArtistPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permission = ActivityCompat.checkSelfPermission(DeveloperSettings.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(
                            DeveloperSettings.this,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/jpeg");
                    startActivityForResult(intent, PICK_ARTIST_IMAGE);
                }
            }
        });
        chooseGalleryPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permission = ActivityCompat.checkSelfPermission(DeveloperSettings.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(
                            DeveloperSettings.this,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE2
                    );
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/jpeg");
                    startActivityForResult(intent, PICK_GALLERY_IMAGE);
                }
            }
        });
        addArtistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(artistImagepath) && artistName.getText().length() != 0 && artistDescription.getText().length() != 0) {
                    new Thread(new Runnable() {
                        public void run() {
                            new UploadArtistPhotoTask().execute();
                        }
                    }).start();
                } else if (artistName.getText().length() == 0 || artistDescription.getText().length() == 0) {
                    Toast.makeText(DeveloperSettings.this, "Cannot leave fields blank!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(artistImagepath)) {
                    Toast.makeText(DeveloperSettings.this, "Must choose photo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(galleryImagepath) && galleryName.getText().length() != 0 && galleryDescription.getText().length() != 0) {
                    new Thread(new Runnable() {
                        public void run() {
                            new UploadGalleryPhotoTask().execute();
                        }
                    }).start();
                } else if (galleryName.getText().length() == 0 || galleryDescription.getText().length() == 0) {
                    Toast.makeText(DeveloperSettings.this, "Cannot leave fields blank!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(galleryImagepath)) {
                    Toast.makeText(DeveloperSettings.this, "Must choose photo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("DeveloperSettings", "onActivityResult");
        if (requestCode == PICK_ARTIST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                final String result = data.getDataString();
                Log.d("ArtistProfile", result);
                Bitmap bitmap = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    Log.d("DeveloperSettings", "KITKAT");
                    selectedArtistImageUri = data.getData();
                    artistImagepath = FileManager.getPath(this, selectedArtistImageUri);
                    Log.d("ArtistProfile", artistImagepath.split("/")[artistImagepath.split("/").length-1]);
                    bitmap = BitmapFactory.decodeFile(artistImagepath);
                }
                imageView.setImageBitmap(bitmap);
            }
        } else if (requestCode == PICK_GALLERY_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                final String result = data.getDataString();
                Log.d("ArtistProfile", result);
                Bitmap bitmap = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    Log.d("DeveloperSettings", "KITKAT");
                    selectedGalleryImageUri = data.getData();
                    galleryImagepath = FileManager.getPath(this, selectedGalleryImageUri);
                    Log.d("ArtistProfile", galleryImagepath.split("/")[galleryImagepath.split("/").length-1]);
                    bitmap = BitmapFactory.decodeFile(galleryImagepath);
                }
                imageView2.setImageBitmap(bitmap);
            }
        }
    }

    private void uploadArtistPhoto() throws IOException {

        if(isNetworkAvailable()){
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.JPEG, 30, stream);
            final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpg");
            InputStream iStream =   getContentResolver().openInputStream(selectedArtistImageUri);
            byte[] byteArray = IOUtils.toByteArray(iStream);

            HttpUrl.Builder urlBuilder = HttpUrl.parse(addArtistServerUri).newBuilder();
            urlBuilder.addQueryParameter("artist_name", artistName.getText().toString());
            urlBuilder.addQueryParameter("desc", artistDescription.getText().toString());
//            urlBuilder.addQueryParameter("img_bytes", String.valueOf(byteArray));
            urlBuilder.addQueryParameter("file_name", artistImagepath.split("/")[artistImagepath.split("/").length-1]);
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JPEG, byteArray);
            String url = urlBuilder.build().toString();
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error: Could not upload, try again!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final Response resp = response;
                    //Log.v(TAG, resp);
                    if (response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s = resp.code() + " (" + resp.message() + ")";
                                Log.d("Upload", s);
                                Toast.makeText(getApplicationContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s = resp.code() + " (" + resp.message() + ")";
                                Log.d("Upload", s);
                                Toast.makeText(getApplicationContext(), "Error: Failure", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
        }else{
            Toast.makeText(this, "Network Unavailable", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadGalleryPhoto() throws IOException {

        if(isNetworkAvailable()){
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.JPEG, 30, stream);
            final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpg");
            InputStream iStream =   getContentResolver().openInputStream(selectedGalleryImageUri);
            byte[] byteArray = IOUtils.toByteArray(iStream);

            HttpUrl.Builder urlBuilder = HttpUrl.parse(addGalleryServerUri).newBuilder();
            urlBuilder.addQueryParameter("gallery_name", galleryName.getText().toString());
            urlBuilder.addQueryParameter("year", galleryYear.getText().toString());
            urlBuilder.addQueryParameter("desc", galleryDescription.getText().toString());
            urlBuilder.addQueryParameter("latit", "0.0");
            urlBuilder.addQueryParameter("longit", "0.0");
//            urlBuilder.addQueryParameter("img_bytes", String.valueOf(byteArray));
            urlBuilder.addQueryParameter("file_name", galleryImagepath.split("/")[galleryImagepath.split("/").length-1]);
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JPEG, byteArray);
            String url = urlBuilder.build().toString();
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error: Could not upload, try again!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final Response resp = response;
                    //Log.v(TAG, resp);
                    if (response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s = resp.code() + " (" + resp.message() + ")";
                                Log.d("Upload", s);
                                Toast.makeText(getApplicationContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s = resp.code() + " (" + resp.message() + ")";
                                Log.d("Upload", s);
                                Toast.makeText(getApplicationContext(), "Error: Failure", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
        }else{
            Toast.makeText(this, "Network Unavailable", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/jpeg");
                    startActivityForResult(intent, PICK_ARTIST_IMAGE);

                }
                return;
            }
            case REQUEST_EXTERNAL_STORAGE2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/jpeg");
                    startActivityForResult(intent, PICK_GALLERY_IMAGE);

                }
                return;
            }
        }
    }

    private class UploadArtistPhotoTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                uploadArtistPhoto();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class UploadGalleryPhotoTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                uploadGalleryPhoto();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
