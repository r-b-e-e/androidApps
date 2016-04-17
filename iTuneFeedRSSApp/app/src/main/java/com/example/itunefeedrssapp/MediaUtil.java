package com.priyanka.itunefeedrssapp;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rakesh Balan on 9/30/2015.
 */
public class MediaUtil {

    static public class MediaJSONParser {
        static ArrayList<Media> parseJSON(String input, String mediaType) throws JSONException {
            ArrayList<Media> medialist = new ArrayList<Media>();
            JSONObject root = new JSONObject(input);
            JSONObject feed = root.getJSONObject("feed");
            JSONArray entryArray = feed.getJSONArray("entry");
            for (int i = 0; i < entryArray.length(); i++) {

                JSONObject singleEntry = entryArray.getJSONObject(i);
                Media media = new Media();

                if(singleEntry.optJSONObject("im:name") != null && singleEntry.optJSONObject("im:name").has("label")){
                    media.setTitle(singleEntry.getJSONObject("im:name").getString("label"));
                }
                if(singleEntry.optJSONObject("im:price") != null &&
                        singleEntry.optJSONObject("im:price").optJSONObject("attributes")!= null) {
                    if (singleEntry.getJSONObject("im:price").getJSONObject("attributes").has("amount")) {
                        media.setPrice(singleEntry.getJSONObject("im:price").getJSONObject("attributes").getString("amount"));
                    }
                    if(singleEntry.getJSONObject("im:price").getJSONObject("attributes").has("currency"))
                    {
                        media.setCurrency(singleEntry.getJSONObject("im:price").getJSONObject("attributes").getString("currency"));
                    }
                }

                if(singleEntry.optJSONArray("im:image") != null)
                {
                    if(!singleEntry.optJSONArray("im:image").isNull(0) && singleEntry.getJSONArray("im:image").getJSONObject(0).has("label"))
                    {
                        media.setSmall_image(singleEntry.getJSONArray("im:image").getJSONObject(0).getString("label"));
                    }
                    if(!singleEntry.optJSONArray("im:image").isNull(2) && singleEntry.getJSONArray("im:image").getJSONObject(2).has("label"))
                    {
                        media.setLarge_image(singleEntry.getJSONArray("im:image").getJSONObject(2).getString("label"));
                    }
                }
                if(singleEntry.optJSONObject("im:artist") != null && singleEntry.getJSONObject("im:artist").has("label"))
                {
                    media.setArtist((singleEntry.getJSONObject("im:artist").getString("label")));
                }

                if(singleEntry.optJSONObject("category") != null &&
                        singleEntry.getJSONObject("category").optJSONObject("attributes") != null &&
                        singleEntry.getJSONObject("category").getJSONObject("attributes").has("label"))
                {
                    media.setCategory(singleEntry.getJSONObject("category").getJSONObject("attributes").getString("label"));
                }
                if(singleEntry.optJSONObject("im:releaseDate") != null &&
                        singleEntry.getJSONObject("im:releaseDate").optJSONObject("attributes") != null &&
                        singleEntry.getJSONObject("im:releaseDate").getJSONObject("attributes").has("label"))
                {
                    media.setReleaseDate(singleEntry.getJSONObject("im:releaseDate").getJSONObject("attributes").getString("label"));
                }
                if (singleEntry.optJSONArray("link") != null ) {
                    if( !singleEntry.getJSONArray("link").isNull(1)){
                        if (singleEntry.getJSONArray("link").getJSONObject(1).has("im:duration")) {
                            media.setDuration(singleEntry.getJSONArray("link").getJSONObject(1)
                                    .getJSONObject("im:duration").getString("label"));
                        }}
                    if( !singleEntry.getJSONArray("link").isNull(0)){
                        if (singleEntry.getJSONArray("link").getJSONObject(0).has("attributes")) {
                            media.setLink_to_media(singleEntry.getJSONArray("link").getJSONObject(0).
                                    getJSONObject("attributes").getString("href"));
                        }}
                }
                else if (singleEntry.optJSONObject("link") != null
                        && singleEntry.getJSONObject("link").has("attributes")) {
                    media.setLink_to_media(singleEntry.getJSONObject("link").getJSONObject("attributes").getString("href"));
                }
                if (singleEntry.has("summary")) {
                    media.setSummary(singleEntry.getJSONObject("summary").getString("label"));
                }
                medialist.add(media);
            }

            return medialist;
        }

    }
}


