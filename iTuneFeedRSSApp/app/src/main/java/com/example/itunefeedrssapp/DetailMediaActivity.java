package com.priyanka.itunefeedrssapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;
/**
 * Created by Rakesh Balan on 9/30/2015.
 */
public class DetailMediaActivity extends AppCompatActivity {
    static final String MEDIA_DETAIL = "MEDIA_DETAIL";
    static final String MEDIA_TITLE = "    MEDIA_TITLE";
    Media media;
    LinearLayout linear;
    String title;
    String mediaTitle;
    TextView summary = null;
    TextView duration = null;
    TextView summary1 = null;
    TextView link = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_media);
        // setting the App logo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.media_icon);
        getSupportActionBar().setTitle("       " + getResources().getString(R.string.title_activity_detail_media));


        linear = (LinearLayout) findViewById(R.id.LinearLayoutDetailID);
        Random random = new Random();
        if (getIntent().getExtras() != null) {
            media = (Media) getIntent().getExtras().get(MEDIA_DETAIL);
            mediaTitle = getIntent().getExtras().getString(MEDIA_TITLE);
        }
        if (media != null) {

            LinearLayout.LayoutParams options = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView appTitle = new TextView(this);
            appTitle.setText(media.getTitle());
            appTitle.setId(random.nextInt());
            appTitle.setTextColor(Color.BLACK);
            appTitle.setTypeface(null, Typeface.BOLD);
            TextView appReleaseDt = new TextView(this);
            appReleaseDt.setText(media.getReleaseDate());
            appReleaseDt.setId(random.nextInt());
            appReleaseDt.setTextColor(Color.BLACK);
            appReleaseDt.setTypeface(null, Typeface.BOLD);

            ImageView largeImage = new ImageView(this);
            Picasso.with(getApplicationContext()).load(media.getLarge_image()).into(largeImage);

            TextView artist = new TextView(this);
            artist.setText("By: " + media.getArtist());
            artist.setId(random.nextInt());
            artist.setTextColor(Color.BLACK);
            artist.setTypeface(null, Typeface.BOLD);

            TextView price = new TextView(this);
            if (media.getCurrency().equals("USD")) {
                price.setText("Price: $" + media.getPrice());
            } else {
                price.setText("Price: " + media.getPrice() + " " + media.getCurrency());
            }
            price.setId(random.nextInt());
            price.setTextColor(Color.BLACK);
            price.setTypeface(null, Typeface.BOLD);

            TextView category = new TextView(this);
            category.setText("Category: " + media.getCategory());
            category.setId(random.nextInt());
            category.setTextColor(Color.BLACK);
            category.setTypeface(null, Typeface.BOLD);

            if (media.getDuration() != null && !media.getDuration().isEmpty()) {
                duration = new TextView(this);
                duration.setText("Duration: " + media.getDuration());
                duration.setId(random.nextInt());
                duration.setTextColor(Color.BLACK);
                duration.setTypeface(null, Typeface.BOLD);
                duration.setLayoutParams(options);
                duration.setPadding(0, 0, 0, 20);
            }

            if (media.getSummary() != null && !media.getSummary().isEmpty()) {
                summary = new TextView(this);
                summary.setText("Summary: ");
                summary.setId(random.nextInt());
                summary.setTextColor(Color.BLACK);
                summary.setTypeface(null, Typeface.BOLD);
                summary.setLayoutParams(options);
                summary.setPadding(0, 0, 0, 10);
                summary1 = new TextView(this);
                summary1.setText(media.getSummary());
                summary1.setId(random.nextInt());
                summary1.setTextColor(Color.BLACK);
                summary1.setLayoutParams(options);
                summary1.setPadding(0, 0, 0, 40);
            }

            TextView appLink = new TextView(this);
            appLink.setText("App Link In Store:");
            appLink.setId(random.nextInt());
            appLink.setTextColor(Color.BLACK);
            appLink.setTypeface(null, Typeface.BOLD);

            link = new TextView(this);
            link.setText(media.getLink_to_media());
            link.setId(random.nextInt());
            link.setTextColor(Color.BLUE);
            link.setLinksClickable(true);
            link.setClickable(true);

            options.setMargins(10, 10, 10, 10);
            appTitle.setLayoutParams(options);
            appTitle.setPadding(250, 0, 20, 20);
            // appTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            appReleaseDt.setLayoutParams(options);
            // appReleaseDt.setGravity(Gravity.CENTER_HORIZONTAL);
            appReleaseDt.setPadding(250, 0, 20, 20);
            largeImage.setLayoutParams(options);
            largeImage.setMinimumHeight(800);
            largeImage.setMinimumWidth(1000);
            // largeImage.setPadding(350, 20, 100, 100);
            largeImage.setPadding(20, 0, 0, 40);
            artist.setLayoutParams(options);
            artist.setPadding(0, 0, 0, 40);
            price.setLayoutParams(options);
            price.setPadding(0, 0, 0, 40);
            category.setLayoutParams(options);
            category.setPadding(0, 0, 0, 40);
            appLink.setLayoutParams(options);
            link.setLayoutParams(options);
            appLink.setPadding(0, 0, 0, 10);
            link.setPadding(0, 0, 0, 20);

            linear.addView(appTitle);
            if (!(mediaTitle.equals("iTunes U") || mediaTitle.equals("Pod Cast") || mediaTitle.equals("Movies"))) {
                linear.addView(appReleaseDt);
            }
            linear.addView(largeImage);
            linear.addView(artist);
            if (!mediaTitle.equals("Audio Book")) {
                linear.addView(price);
            }
            linear.addView(category);
            if (duration != null) {
                if (!mediaTitle.equals("Movies")) {
                    linear.addView(duration);
                }
            }
            if (summary1 != null) {
                if (!mediaTitle.equals("Movies")) {
                    linear.addView(summary);
                    linear.addView(summary1);
                }
            }
            linear.addView(appLink);
            linear.addView(link);
        }
        // on click
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent linIntent = new Intent(DetailMediaActivity.this, PreviewActivity.class);
                linIntent.putExtra(PreviewActivity.URL, media.getLink_to_media());
                startActivity(linIntent);
            }
        });

    }


}
