package com.example.group10b_hw05;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
/**
 * Ganesh Ramamoorthy
 * Rakesh Balan
 * Priyanka
 */
public class AsyncTaskParser extends AsyncTask<String,Void,ArrayList<TedShow>> {
    IFeeds activity;
    ProgressDialog progress;
    String PROGRESS_MESSAGE = "Loading Episodes";

    public AsyncTaskParser(IFeeds activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog((Context) activity);
        progress.setMessage(PROGRESS_MESSAGE);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    @Override
    protected ArrayList<TedShow> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();

                return XMLParser.ParseFeeds.parsePodcasts(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<TedShow> podcasts) {
        super.onPostExecute(podcasts);
        progress.dismiss();
        activity.putList(podcasts);
    }

    public static interface IFeeds{
        public void putList(ArrayList<TedShow> feeds);
    }
}
