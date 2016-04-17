package com.rakeshbalancompany.inclass06;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by rakeshbalan on 10/5/2015.
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

    ArrayAdapter<App> photoAdap;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        if(isConnectedOnline())
        {
            new getIosAppsAsyncTask(this).execute("http://itunes.apple.com/us/rss/topgrossingapplications/limit=25/json");
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App a = (App) parent.getItemAtPosition(position);

                Intent i = new Intent(MainActivity.this,PreviewActivity.class);
                i.putExtra("url", a);
                startActivity(i);
            }
        });


        }

    public void getArrayList(ArrayList<App> apps)
    {
        photoAdap = new AppAdapter(MainActivity.this, R.layout.listviewxml, apps);
        listView.setAdapter(photoAdap);
        photoAdap.setNotifyOnChange(true);
        photoAdap.notifyDataSetChanged();
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
