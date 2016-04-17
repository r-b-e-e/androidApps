package com.example.inclass4;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

// Created by Rakesh Balan Lingakumar

public class MainActivity extends AppCompatActivity {
    ArrayList<String> photoArray = new ArrayList<String>();
    private int counter = 0;
    ProgressDialog pd;
    ImageButton btnFirst;
    ImageButton btnLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFirst = (ImageButton) findViewById(R.id.imageButtonFirst);
        btnLast = (ImageButton) findViewById(R.id.imageButtonFirst);
        if (isConnected()) {
            Toast.makeText(getApplicationContext(), "Connection Success", Toast.LENGTH_SHORT).show();
            new getHttpCon().execute("http://dev.theappsdr.com/lectures/inclass_photos/index.php");


        } else {
            Toast.makeText(getApplicationContext(), "Internet Connection Failure", Toast.LENGTH_SHORT).show();
            btnFirst.setEnabled(false);
            btnLast.setEnabled(false);
        }

    }


    public void getfirstClick(View v) {
        if (isConnected()) {
            if (counter == 0) {
                counter = photoArray.size() - 1;
            } else {
                counter = counter - 1;
            }

            new getHttpConImage().execute("http://dev.theappsdr.com/lectures/inclass_photos/index.php?+pid=" + photoArray.get(counter));
        } else {
            Toast.makeText(getApplicationContext(), "Internet Connection Failure", Toast.LENGTH_SHORT).show();
            btnFirst.setEnabled(false);
            btnLast.setEnabled(false);
        }
    }

    public void getlastClick(View v) {
        if (isConnected()) {
            if (counter == photoArray.size() - 1) {
                counter = 0;
            } else {
                counter = counter + 1;
            }
            new getHttpConImage().execute("http://dev.theappsdr.com/lectures/inclass_photos/index.php?+pid=" + photoArray.get(counter));
        } else {
            Toast.makeText(getApplicationContext(), "Internet Connection Failure", Toast.LENGTH_SHORT).show();
            btnFirst.setEnabled(false);
            btnLast.setEnabled(false);
        }
    }


    private class getHttpCon extends AsyncTask<String, Void, ArrayList<String>> {
        BufferedReader reader = null;

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    photoArray.add(line);
                }
                return photoArray;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
            new getHttpConImage().execute("http://dev.theappsdr.com/lectures/inclass_photos/index.php?+pid=" + photoArray.get(counter));
        }
    }


    private class getHttpConImage extends AsyncTask<String, Void, Bitmap> {
        BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Loading Image");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
                return image;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            ImageView iv = (ImageView) findViewById(R.id.imageView);
            iv.setImageBitmap(s);
            pd.dismiss();
        }
    }


    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}