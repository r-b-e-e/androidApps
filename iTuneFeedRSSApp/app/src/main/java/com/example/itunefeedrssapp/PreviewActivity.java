package com.priyanka.itunefeedrssapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
/**
 * Created by Rakesh Balan on 9/30/2015.
 */
public class PreviewActivity extends AppCompatActivity {
    final static String URL = "URL";
    String url;
    WebView webVew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        // setting the App logo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.media_icon);
        getSupportActionBar().setTitle("       " + getResources().getString(R.string.title_activity_preview));
        if(getIntent().getExtras() != null){
            url = getIntent().getExtras().getString(URL);
            webVew = (WebView)findViewById(R.id.webView);
            webVew.getSettings().setJavaScriptEnabled(true);
            webVew.loadUrl(url);
            finish();
        }


    }

}
