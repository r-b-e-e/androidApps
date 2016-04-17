package com.example.group10b_hw05;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Ganesh Ramamoorthy
 * Rakesh Balan
 * Priyanka
 */
public class MainActivity extends AppCompatActivity implements AsyncTaskParser.IFeeds {


    RecyclerView recycler;
    LinearLayoutManager recyclerLayout;
    static Boolean isPlaying = false;
    static Handler handler;
    ArrayList<TedShow> podcastList;
    String currentView = "Linear";
    public static ImageView playButton;
    public static ImageView pauseButton;
    public static ProgressBar playProgress;

    private final String PODCAST = "http://www.npr.org/rss/podcast.php?id=510298";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        recycler = (RecyclerView) findViewById(R.id.RecyclerView);
        playButton = (ImageView) findViewById(R.id.playButton);
        pauseButton = (ImageView) findViewById(R.id.pauseButton);
        playProgress = (ProgressBar) findViewById(R.id.progressBarLength);

        playButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        playProgress.setVisibility(View.INVISIBLE);
        if(isConnected())
        {
            new AsyncTaskParser(this).execute(PODCAST);
        }else
        {
            Toast.makeText(getApplicationContext(), "Connection Not Success", Toast.LENGTH_SHORT).show();
        }


        //displaying app icon
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ted_icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.RecycleViews:
                if (currentView.equals("Linear")) {
                    currentView = "Grid";
                } else {
                    currentView = "Linear";
                }

                playButton.setVisibility(View.INVISIBLE);
                pauseButton.setVisibility(View.INVISIBLE);
                playProgress.setVisibility(View.INVISIBLE);


                setRecyclerView();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void putList(ArrayList<TedShow> feeds) {
        podcastList = new ArrayList<TedShow>();
        podcastList = feeds;
        setRecyclerView();
    }

    public void setRecyclerView() {
        if (currentView.equals("Linear")) {
            recyclerLayout = new LinearLayoutManager(this);
            recycler.setLayoutManager(recyclerLayout);
            RecyclerAdapterList lRecyclerAdapter = new RecyclerAdapterList(podcastList, MainActivity.this);
            recycler.setAdapter(lRecyclerAdapter);
            if (isPlaying) {
                RecyclerAdapterGrid.getMediaPlayer().stop();
                isPlaying = false;
            }

        } else {
            recyclerLayout = new GridLayoutManager(this, 2);
            recycler.setLayoutManager(recyclerLayout);
            RecyclerAdapterGrid lRecyclerAdapter = new RecyclerAdapterGrid(podcastList, MainActivity.this);
            recycler.setAdapter(lRecyclerAdapter);
            if (isPlaying) {
                RecyclerAdapterList.getMediaPlayer().stop();
                isPlaying = false;
            }

        }
    }

    public void playing() {
        pauseButton.setVisibility(View.VISIBLE);
        playButton.setVisibility(View.INVISIBLE);
        playProgress.setVisibility(View.VISIBLE);
        isPlaying = true;

        handler = new Handler();
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int lCurrentPosition = 0;
                if (currentView.equals("Linear")) {
                    if (RecyclerAdapterList.getMediaPlayer() != null) {
                        lCurrentPosition = ((RecyclerAdapterList.getMediaPlayer().getCurrentPosition()) * 100) / RecyclerAdapterList.getMediaPlayer().getDuration();
                    }
                } else {
                    if (RecyclerAdapterGrid.getMediaPlayer() != null) {
                        lCurrentPosition = ((RecyclerAdapterGrid.getMediaPlayer().getCurrentPosition()) * 100) / RecyclerAdapterGrid.getMediaPlayer().getDuration();
                    }
                }

                playProgress.setProgress(lCurrentPosition);
                handler.postDelayed(this, 1000);
            }
        });


    }

    public void playClicked(View aView) {
        if (currentView.equals("Linear"))
            RecyclerAdapterList.getMediaPlayer().start();
        else
            RecyclerAdapterGrid.getMediaPlayer().start();

        pauseButton.setVisibility(View.VISIBLE);
        playButton.setVisibility(View.INVISIBLE);
    }

    public void pauseClicked(View aView) {
        if (currentView.equals("Linear"))
            RecyclerAdapterList.getMediaPlayer().pause();
        else
            RecyclerAdapterGrid.getMediaPlayer().pause();

        pauseButton.setVisibility(View.INVISIBLE);
        playButton.setVisibility(View.VISIBLE);
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
