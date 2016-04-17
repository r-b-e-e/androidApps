package com.rakeshbalancompany.mad_midterm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog;
    ArrayList<News> tempNewsList;
    MediaAdapter adapter;
    DatabaseDataManager manager;

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar lActionBar = getSupportActionBar();
        lActionBar.setDisplayShowHomeEnabled(true);
        lActionBar.setIcon(R.drawable.bbc);
        lActionBar.setTitle(R.string.app_name);
        lActionBar.show();

        //manager = new DatabaseDataManager(this);

        if(isConnectedOnline()) {
            new bbcFeedTask().execute("http://feeds.bbci.co.uk/news/video_and_audio/technology/rss.xml");
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Connectivity", Toast.LENGTH_SHORT).show();
        }
    }


    private class bbcFeedTask extends AsyncTask<String, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con= (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int source=con.getResponseCode();
                if(source==HttpURLConnection.HTTP_OK)
                {
                    InputStream in =con.getInputStream();
                    return NewsUtility.MediaSAXParser.mediaParser(in);
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading News...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<News> newsList) {
            super.onPostExecute(newsList);
            dialog.dismiss();
            tempNewsList=newsList;
//            Log.d("demo", tempNewsList.toString());

            Collections.sort(tempNewsList, new Comparator<News>() {
                @Override
                public int compare(News o1, News o2) {
                    return o1.getPub_date().compareTo(o2.getPub_date());
                }
            });


            ListView listView = (ListView) findViewById(R.id.listView);
            adapter = new MediaAdapter(MainActivity.this, R.layout.listrow, tempNewsList);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                    Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                    intent.putExtra("Link", tempNewsList.get(position).getNews_link());
                    startActivity(intent);


//                    ImageButton blockButton = (ImageButton) findViewById(R.id.blockbuttonimage);
//
//                    blockButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            News media = tempNewsList.get(pos);
//                            manager.saveNote(media);
//                            Toast.makeText(MainActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
//
//                            ArrayList<News> anotherList = tempNewsList;
//                            anotherList.remove(media);
//
//
//                            ListView listView = (ListView) findViewById(R.id.listView);
//                            adapter = new MediaAdapter(MainActivity.this, R.layout.listrow, anotherList);
//                            listView.setAdapter(adapter);
//                            adapter.setNotifyOnChange(true);
//                        }
//                    });
                }
            });









        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


//        if (id == R.id.action_settings) {
//
//            fav = manager.getAll();
//            ListView listView = (ListView) findViewById(R.id.listView);
//
//            adapter1 = new MediaAdapter(MainActivity.this, R.layout.favrow, fav);
//            listView.setAdapter(adapter1);
//            return true;
//        }
//
//        if(id==R.id.action_settings1){
//            ListView listView = (ListView) findViewById(R.id.listView);
//            adapter = new MediaAdapter(MainActivity.this, R.layout.listrow, mediaList);
//            listView.setAdapter(adapter);
//            return true;
//        }
//        if(id==R.id.action_settings1){
//            ListView listView = (ListView) findViewById(R.id.listView);
//            adapter = new MediaAdapter(MainActivity.this, R.layout.listrow, mediaList);
//            listView.setAdapter(adapter);
//            return true;
//        }
        if(id==R.id.action_settings3){

            ArrayList<News> sortList = tempNewsList;




            Collections.sort(sortList, new Comparator<News>() {
                @Override
                public int compare(News o1, News o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });


            ListView listView = (ListView) findViewById(R.id.listView);
            adapter = new MediaAdapter(MainActivity.this, R.layout.listrow, sortList);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);


            return true;
        }
        if(id==R.id.action_settings4){
            ArrayList<News> sortList = tempNewsList;




            Collections.sort(sortList, new Comparator<News>() {
                @Override
                public int compare(News o1, News o2) {
                    return o2.getTitle().compareTo(o1.getTitle());
                }
            });


            ListView listView = (ListView) findViewById(R.id.listView);
            adapter = new MediaAdapter(MainActivity.this, R.layout.listrow, sortList);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if((networkInfo != null) && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

}
