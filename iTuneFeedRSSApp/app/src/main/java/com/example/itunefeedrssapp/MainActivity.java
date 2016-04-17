package com.priyanka.itunefeedrssapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rakesh Balan on 9/30/2015.
 */
public class MainActivity extends AppCompatActivity {

    TextView audioBook;
    TextView books;
    TextView iOSApps;
    TextView macApps;
    TextView movies;
    TextView iTunesU;
    TextView musicVideos;
    TextView podcast;
    TextView tvShows;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isConnectedOnline()) {
            // setting the App logo
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.media_icon);
            getSupportActionBar().setTitle("       "+getResources().getString(R.string.main_name));
            // Audio book processing
            audioBook = (TextView) findViewById(R.id.txtAudioBookID);
            audioBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processMedia("https://itunes.apple.com/us/rss/topaudiobooks/limit=25/json", getResources().getString(R.string.txtAudioBook));
                }
            });
            // Books processing
            books = (TextView) findViewById(R.id.txtBooksID);
            books.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processMedia("https://itunes.apple.com/us/rss/topfreeebooks/limit=25/json", getResources().getString(R.string.txtBooks));
                }
            });
            // iOS Apps processing
            iOSApps = (TextView) findViewById(R.id.txtiOSAppsID);
            iOSApps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processMedia("https://itunes.apple.com/us/rss/newapplications/limit=25/json", getResources().getString(R.string.txtiOSApps));
                }
            });
            // iTunes U Apps processing
            iTunesU = (TextView) findViewById(R.id.txtiTunesUID);
            iTunesU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processMedia("https://itunes.apple.com/us/rss/topitunesucollections/limit=25/json", getResources().getString(R.string.txtiTunesU));
                }
            });
            // Mac Apps processing
            macApps = (TextView) findViewById(R.id.txtMACAppsID);
            macApps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processMedia("https://itunes.apple.com/us/rss/topfreemacapps/limit=25/json", getResources().getString(R.string.txtMACApps));
                }
            });
            // movies processing
            movies = (TextView) findViewById(R.id.txtMoviesID);
            movies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processMedia("https://itunes.apple.com/us/rss/topmovies/limit=25/json", getResources().getString(R.string.txtMovies));
                }
            });
            // music videos processing
            musicVideos = (TextView) findViewById(R.id.txtMusicVideoID);
            musicVideos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Not Supported in this Project", Toast.LENGTH_SHORT).show();
                }
            });
            // podcast processing
            podcast = (TextView) findViewById(R.id.txtPodCastsID);
            podcast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processMedia("https://itunes.apple.com/us/rss/toppodcasts/limit=25/json", getResources().getString(R.string.txtPodCasts));
                }
            });
            // TV Shows Apps processing
            tvShows = (TextView) findViewById(R.id.txtTvShowsID);
            tvShows.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processMedia("https://itunes.apple.com/us/rss/toptvepisodes/limit=25/json", getResources().getString(R.string.txtTVShows));
                }
            });
        }
        else {

            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
}

    private void processMedia(String url, String mediaType) {
        intent = new Intent(MainActivity.this,MediaListActivity.class);
        intent.putExtra(MediaListActivity.URL,url);
        intent.putExtra(MediaListActivity.MEDIA_TYPE,mediaType);
        startActivity(intent);
    }

    // method to check internet connection
    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
