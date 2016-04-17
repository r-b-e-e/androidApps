package com.rakeshbalancompany.mad_midterm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by rakeshbalan on 10/26/2015.
 */
public class WebViewActivity extends AppCompatActivity{

//    TextView title;
//    ImageView image;
//    ImageView img;
        WebView webview;

 //   DatabaseDataManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
//        manager = new DatabaseDataManager(this);
//        title =(TextView)findViewById(R.id.title);
//        image=(ImageView)findViewById(R.id.image);
//        img=(ImageView)findViewById(R.id.fav);
//
//        title.setText(getIntent().getExtras().getString("Title"));
//
//        if(manager.mediaExists(getIntent().getExtras().getString("Title"))){
//            img.setImageResource(R.drawable.images);
//        }
//        else{
//            img.setImageResource(R.drawable.ic_star_border_black_24dp);
//        }
//        Picasso.with(getApplicationContext()).load(getIntent().getExtras().getString("Image")).into(image);


        webview = (WebView) findViewById(R.id.webView);
          String url = getIntent().getExtras().getString("Link");


        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
