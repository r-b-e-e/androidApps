package com.priyanka.itunefeedrssapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
/**
 * Created by Rakesh Balan on 9/30/2015.
 */
public class MediaListActivity extends AppCompatActivity {

    static final String URL = "URL";
    static final String MEDIA_TYPE = "MEDIA_TYPE";
    static final String iTUNES = "iTunesJSON";
    static final String JSONValue = "JSONValue";
    static final String JSONTime = "JSONTime";
    ArrayList<Media> mediaList;
    String title;
    String jsonURL;
    ProgressDialog dialog;
    LinearLayout llHorizontal;
    LinearLayout llVertical;
    ArrayList<ImageView> imageViewList;
    ArrayList<TextView> txtViewList;
    TextView txtView;
    ImageView imgview;
    AlertDialog alert;
    Intent intent;
    AlertDialog.Builder builder;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);
        // pre processing of view components
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Apps ...");
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        llVertical = (LinearLayout) findViewById(R.id.LinearlayoutVerticalID);
                builder = new AlertDialog.Builder(this).
                setTitle("Delete Media").
                setMessage("Are you sure you want to delete this item").
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaList.remove(index);
                        savetoSharedPreferences(mediaList, getSharedPreferences(JSONValue+title,0));
                        generateMedialist(mediaList);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Item not deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        // setting the App logo and title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.media_icon);
        if (getIntent().getExtras() != null) {
            title = getIntent().getExtras().getString(MEDIA_TYPE);
            if (title != null) {
                getSupportActionBar().setTitle("       "+title);
            }
            // getting the JSON URL
            jsonURL = getIntent().getExtras().getString(URL);
            // httpConnection to parse JSON string
            new GetAsyncTask().execute(jsonURL, title);

            }
        }

    //inner class for JSON processing
    private class GetAsyncTask extends AsyncTask<String, Void, ArrayList<Media>> {

        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected ArrayList<Media> doInBackground(String... params) {
            ArrayList<Media> medias;
            try {
                // setting the shared preferences
                SharedPreferences preferences = getSharedPreferences(JSONValue + params[1], 0);
                String jsonString = preferences.getString(iTUNES, "");
                long storedTime = preferences.getLong(JSONTime, 0);
                Time currentTime = new Time();
                currentTime.setToNow();
                // checking for same JSON string and 2 min time window in milliseconds
                if (jsonString.isEmpty() || (currentTime.toMillis(false) - storedTime) > 120000) {

                    URL url = new URL(params[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.connect();
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = reader.readLine();
                        while (line != null) {
                            sb.append(line);
                            line = reader.readLine();
                        }
                        jsonString = sb.toString();
                        // putting value in shared preferences
                        currentTime = new Time();
                        currentTime.setToNow();

                    }

                    medias = MediaUtil.MediaJSONParser.parseJSON(jsonString, title);
                    savetoSharedPreferences(medias,preferences);
                }
                else{
                    Gson gson = new Gson();
                    Type t = new TypeToken<ArrayList<Media>>(){}.getType();
                    medias = gson.fromJson(preferences.getString(iTUNES,""), t);
                }
                return medias;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Media> media) {
            dialog.dismiss();
            if(media!=null){
            mediaList = (ArrayList<Media>) media.clone();
            generateMedialist(media);}
        }
    }

    private void generateMedialist(ArrayList<Media> media) {
        if( llVertical.getChildCount() > 0){
            llVertical.removeAllViews();
        }
        for (int i = 0; i < media.size(); i++) {
            // Creating dynamic layout for media image and title
            final ImageView thumbImage = new ImageView(MediaListActivity.this);
            final TextView txtTitle = new TextView(MediaListActivity.this);
            txtTitle.setText((CharSequence) media.get(i).getTitle());
            txtTitle.setId(i);
            thumbImage.setId(i);
            llHorizontal = new LinearLayout(MediaListActivity.this);
            Picasso.with(getApplicationContext()).load(media.get(i).getSmall_image()).resize(250, 250).into(thumbImage);
            LinearLayout.LayoutParams optionLayoutParm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            thumbImage.setLayoutParams(optionLayoutParm);
            txtTitle.setLayoutParams(optionLayoutParm);
            txtTitle.setTextColor(Color.BLACK);
            txtTitle.setPadding(10, 10, 5, 10);
            thumbImage.setPadding(5, 10, 5, 10);
            txtTitle.setClickable(true);
            thumbImage.setClickable(true);
            thumbImage.setLongClickable(true);
            txtTitle.setLongClickable(true);
            // On click & Long click processing of Image and title
            final Media mediaitem = media.get(i);
            thumbImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(MediaListActivity.this, DetailMediaActivity.class);
                    intent.putExtra(DetailMediaActivity.MEDIA_DETAIL, mediaitem);
                    intent.putExtra(DetailMediaActivity.MEDIA_TITLE, title);
                    startActivity(intent);
                }
            });
            thumbImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    index = thumbImage.getId();
                    alert = builder.create();
                    alert.show();
                    return true;
                }
            });

            txtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(MediaListActivity.this, DetailMediaActivity.class);
                    intent.putExtra(DetailMediaActivity.MEDIA_DETAIL, mediaitem);
                    intent.putExtra(DetailMediaActivity.MEDIA_TITLE, title);
                    startActivity(intent);
                }
            });

            txtTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    index = txtTitle.getId();
                    alert = builder.create();
                    alert.show();
                    return true;
                }
            });

            if (i % 2 == 0) {
                llHorizontal.setBackgroundColor(Color.LTGRAY);
            }
            llHorizontal.addView(thumbImage);
            llHorizontal.addView(txtTitle);
            llVertical.addView(llHorizontal);

        }
    }

    private void savetoSharedPreferences(ArrayList<Media> medias, SharedPreferences preferences){
        Gson gson = new Gson();
        Time currentTime = new Time();
        currentTime.setToNow();
        String serializedStr = gson.toJson(medias);
        preferences.edit().clear();
        preferences.edit()
                .putString(iTUNES, serializedStr).putLong(JSONTime, currentTime.toMillis(false))
                .commit();
    }

}
