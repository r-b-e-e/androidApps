package android_apps.rakeshbalan.githubrailsopenissues;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private List<RailsRepo> railsRepoList;
    private ListView listViewRailsRepo;
    private RailsRepoAdapter railsRepoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checking for connectivity
        if (isConnectedOnline())
        {
            new GetRailRepoOpenIssues().execute("https://api.github.com/repos/rails/rails/issues");
        }
        else
        {
            Toast.makeText(MainActivity.this, "No Internet. Please check your network connection.", Toast.LENGTH_SHORT).show();
        }
    }

    // using AsyncTask to Minimize network bandwidth.
    public class GetRailRepoOpenIssues extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Intent splashScreenIntent = new Intent(MainActivity.this, SplashScreen.class);
            startActivity(splashScreenIntent);
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();

            try{
                URL url = new URL(params[0]);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(false);

                httpsURLConnection.connect();

                int httpResponseCode = httpsURLConnection.getResponseCode();

                //checking for valid API
                if(httpResponseCode == 200)
                {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                    String eachLine = null;
                    while((eachLine = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(eachLine);
                    }
                    bufferedReader.close();
                }
                else if (httpResponseCode == 404)
                {
                    Toast.makeText(MainActivity.this, "API is not reachable!", Toast.LENGTH_SHORT).show();
                }

                httpsURLConnection.disconnect();

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d("debug", s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                railsRepoList = new ArrayList<RailsRepo>();
                railsRepoList = JSONUtility.parseJsonRailsRepo(jsonArray); //parse JSON

//                for (int i = 0; i < railsRepoList.size(); i++)
//                {
//                    Log.d("debug", railsRepoList.get(i).getIssueUpdatedDate().toString());
//                }


                listViewRailsRepo  = (ListView) findViewById(R.id.listViewRailsRepo);
                railsRepoAdapter = new RailsRepoAdapter(MainActivity.this, R.layout.issue_list, railsRepoList);
                listViewRailsRepo.setAdapter(railsRepoAdapter);

            }catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public boolean isConnectedOnline()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if ((networkInfo != null) && (networkInfo.isConnected())){
            return true;
        }
        return false;
    }
}
