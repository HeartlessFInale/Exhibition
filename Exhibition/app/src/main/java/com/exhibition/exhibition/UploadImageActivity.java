package com.exhibition.exhibition;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
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

import com.exhibition.exhibition.models.Artist;
import com.exhibition.exhibition.models.FileManager;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadImageActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private String imagepath;
    private String uploadServerUri = ApiHelper.URL + "uploadArtwork";
    private int serverResponseCode;

    private Artist artist;
    private ProgressDialog progressDialog;

    Button choosePicBtn;
    Button uploadPicBtn;
    ImageView imageView;
    EditText editText;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Uri selectedImageUri;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        artist = getIntent().getParcelableExtra("artist");
        getSupportActionBar().setTitle("Upload Photo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        choosePicBtn = (Button) findViewById(R.id.choosePicBtn);
        uploadPicBtn = (Button) findViewById(R.id.uploadPicBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        choosePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permission = ActivityCompat.checkSelfPermission(UploadImageActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(
                            UploadImageActivity.this,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/jpeg");
                    startActivityForResult(intent, PICK_IMAGE);
                }
            }
        });
        uploadPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(imagepath) && editText.getText().length() != 0 && editText2.getText().length() != 0) {
//                    progressDialog.show();
                    new Thread(new Runnable() {
                        public void run() {
                            new UploadPhotoTask().execute();
                        }
                    }).start();
                } else if (editText.getText().length() == 0 || editText2.getText().length() == 0) {
                    Toast.makeText(UploadImageActivity.this, "Cannot leave fields blank!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(imagepath)) {
                    Toast.makeText(UploadImageActivity.this, "Must choose photo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        progressDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("UploadImageActivity", "onActivityResult");
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                final String result = data.getDataString();
                Log.d("ArtistProfile", result);
                Bitmap bitmap = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    Log.d("UploadImageActivity", "KITKAT");
                    selectedImageUri = data.getData();
                    imagepath = FileManager.getPath(this, selectedImageUri);
                    Log.d("ArtistProfile", imagepath.split("/")[imagepath.split("/").length-1]);
                    bitmap = BitmapFactory.decodeFile(imagepath);
                }
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private void uploadPhoto() throws IOException {

        if(isNetworkAvailable()){
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.JPEG, 30, stream);
            final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpg");
            InputStream iStream =   getContentResolver().openInputStream(selectedImageUri);
            byte[] byteArray = IOUtils.toByteArray(iStream);

            HttpUrl.Builder urlBuilder = HttpUrl.parse(uploadServerUri).newBuilder();
            urlBuilder.addQueryParameter("artist_id", String.valueOf(artist.id));
            urlBuilder.addQueryParameter("art_name", editText.getText().toString());
            urlBuilder.addQueryParameter("desc", editText2.getText().toString());
//            urlBuilder.addQueryParameter("gallery_id", "1");
            urlBuilder.addQueryParameter("file_name", imagepath.split("/")[imagepath.split("/").length-1]);
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
                        progressDialog.hide();
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
                                progressDialog.hide();
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
                                progressDialog.hide();
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

//    public byte[] getBytes(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];
//
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            byteBuffer.write(buffer, 0, len);
//        }
//        return byteBuffer.toByteArray();
//    }

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
                    startActivityForResult(intent, PICK_IMAGE);

                }
                return;
            }
        }
    }

    private class UploadPhotoTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                uploadPhoto();
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
