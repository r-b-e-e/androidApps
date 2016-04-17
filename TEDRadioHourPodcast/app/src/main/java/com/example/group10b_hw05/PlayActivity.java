package com.example.group10b_hw05;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
/**
 * Ganesh Ramamoorthy
 * Rakesh Balan
 * Priyanka
 */
public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    TextView episodeTitle, description, date, duration;
    ImageView episodeIcon, playButton, pauseButton;
    TedShow podcastData;
    String audioFile;
    MediaPlayer mediaPlayer;
    Boolean isPlayed;
    ProgressBar episodeProgress;
    final String PODCASTREF = "PodcastRef";
    static Handler handler;
    int episodeDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mediaPlayer = new MediaPlayer();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("     " + getString(R.string.activity_play));
        actionBar.setIcon(R.mipmap.ted_icon);
        findItems();
        populateData(podcastData);
        pauseButton.setVisibility(View.INVISIBLE);
        isPlayed = false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateData(TedShow podcast) {
        episodeTitle.setText(podcast.getTitle());
        description.setText("Description: " + podcast.getDescription());
        date.setText("Publication Date: " + podcast.getPublicationDate());
        String d = podcast.getDuration();
        if (podcast.getDuration() == null) {
            d = "1";
        }
        duration.setText("Duration: " + Math.round(Double.parseDouble(d) / 60 * 100.0) / 100.0 + " minutes");
        audioFile = podcast.getAudio();
       // Picasso.with(this).load(podcast.getImage()).into(episodeIcon);

        String img = podcast.getImage();
        if (img != null) {
            Picasso.with(this).load(img)
                    .into(episodeIcon);
        }
        else {Picasso.with(this).load(R.drawable.no_image)
                .into(episodeIcon);}
        episodeDuration = Integer.parseInt(d) * 1000;
        episodeProgress.setMax(100);
    }

    public void findItems() {
        episodeTitle = (TextView) findViewById(R.id.textViewEpisodeTitle);
        description = (TextView) findViewById(R.id.textViewEpisodeDescription);
        date = (TextView) findViewById(R.id.textViewDescripDate);
        duration = (TextView) findViewById(R.id.textViewDuration);
        episodeIcon = (ImageView) findViewById(R.id.imageViewEpisodeIcon);
        podcastData = (TedShow) getIntent().getSerializableExtra(PODCASTREF);
        playButton = (ImageView) findViewById(R.id.playButton);
        pauseButton = (ImageView) findViewById(R.id.pauseButton);
        episodeProgress = (ProgressBar) findViewById(R.id.progressBarLength);
    }

    public void playClicked(View aView) {
        if (!isPlayed) {
            mediaPlayer.setOnPreparedListener(this);
            try {
                mediaPlayer.setDataSource(audioFile);
                mediaPlayer.prepare();
                mediaPlayer.start();
                isPlayed = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mediaPlayer.start();
        }

        handler = new Handler();
        PlayActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int lCurrentPosition = ((mediaPlayer.getCurrentPosition()) * 100) / episodeDuration;
                    episodeProgress.setProgress(lCurrentPosition);
                }

                handler.postDelayed(this, 1000);
            }
        });
        playButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.VISIBLE);
    }

    public void pauseClicked(View aView) {
        mediaPlayer.pause();
        pauseButton.setVisibility(View.INVISIBLE);
        playButton.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        this.finish();
    }
}
