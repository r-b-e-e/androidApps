package com.rakeshbalancompany.inclass06;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rakeshbalan on 10/5/2015.
 */
public class AppUtil {
    static public class AppJSONParser {
        static ArrayList<App> parseJSON(String input) throws JSONException {
            ArrayList<App> appList = new ArrayList<App>();

            JSONObject root = new JSONObject(input);
            JSONObject feed = root.getJSONObject("feed");
            JSONArray entryArray = feed.getJSONArray("entry");
            for (int i = 0; i < entryArray.length(); i++) {

                JSONObject singleEntry = entryArray.getJSONObject(i);
                App app = new App();

                if(singleEntry.optJSONObject("im:name") != null && singleEntry.optJSONObject("im:name").has("label")){
                    app.setAppName(singleEntry.getJSONObject("im:name").getString("label"));
                }

                if(singleEntry.optJSONObject("im:artist") != null && singleEntry.getJSONObject("im:artist").has("label"))
                {
                    app.setDeveloperName((singleEntry.getJSONObject("im:artist").getString("label")));
                }

                if(singleEntry.optJSONObject("im:releaseDate") != null &&
                        singleEntry.getJSONObject("im:releaseDate").optJSONObject("attributes") != null &&
                        singleEntry.getJSONObject("im:releaseDate").getJSONObject("attributes").has("label"))
                {
                    app.setReleaseDate(singleEntry.getJSONObject("im:releaseDate").getJSONObject("attributes").getString("label"));
                }



                if(singleEntry.optJSONObject("im:price") != null &&
                        singleEntry.optJSONObject("im:price").optJSONObject("attributes") != null) {
                    if (singleEntry.getJSONObject("im:price").getJSONObject("attributes").has("amount")) {
                        app.setPrice(singleEntry.getJSONObject("im:price").getJSONObject("attributes").getString("amount"));
                    }
                    if(singleEntry.getJSONObject("im:price").getJSONObject("attributes").has("currency"))
                    {
                        app.setCurrency(singleEntry.getJSONObject("im:price").getJSONObject("attributes").getString("currency"));
                    }
                }

                if(singleEntry.optJSONArray("im:image") != null)
                {
                    if(!singleEntry.optJSONArray("im:image").isNull(0) && singleEntry.getJSONArray("im:image").getJSONObject(0).has("label"))
                    {
                        app.setImageUrl(singleEntry.getJSONArray("im:image").getJSONObject(0).getString("label"));
                    }
                    if(!singleEntry.optJSONArray("im:image").isNull(2) && singleEntry.getJSONArray("im:image").getJSONObject(2).has("label"))
                    {
                        app.setImageUrl(singleEntry.getJSONArray("im:image").getJSONObject(2).getString("label"));
                    }
                }


                if(singleEntry.optJSONObject("category") != null &&
                        singleEntry.getJSONObject("category").optJSONObject("attributes") != null &&
                        singleEntry.getJSONObject("category").getJSONObject("attributes").has("label"))
                {
                    app.setCategory(singleEntry.getJSONObject("category").getJSONObject("attributes").getString("label"));
                }


                appList.add(app);
            }
            return appList;
        }

    }
}
