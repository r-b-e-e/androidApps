package com.example.group10b_hw03;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Ganesh Ramamoorthy, Rakesh Balan
 */
public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button createButton;
    private Button deleteButton;
    private Button exitButton;
    AlertDialog.Builder builder;
    AlertDialog alert;
    ProgressDialog dialog;
    private final static int START_REQ_CODE = 100;
    private final static int CREATE_REQ_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isConnected()) {

            assignVariables();


            builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Questions")
                    .setMessage("Are you sure you want to delete all \nyour questions?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new deleteWork().execute("http://dev.theappsdr.com/apis/trivia_fall15/deleteAll.php");
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            alert = builder.create();
        } else {
            Toast.makeText(getApplicationContext(), "Internet Connection Failure", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Assigns the proper views to variables
     */
    private void assignVariables() {
        startButton = (Button) findViewById(R.id.startTrivia);
        createButton = (Button) findViewById(R.id.createQuiz);
        deleteButton = (Button) findViewById(R.id.deleteAll);
        exitButton = (Button) findViewById(R.id.exit);
        setListeners();
    }

    /**
     * Sets listeners for buttons in Main Activity
     */
    private void setListeners() {

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), TriviaActivity.class);
                startActivityForResult(i, START_REQ_CODE);
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CreateQuestionActivity.class);
                startActivityForResult(i, CREATE_REQ_CODE);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();

            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


    private class deleteWork extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                String encodedURL = "gid=" + URLEncoder.encode("0b766a6018aed23562e8e34383439f92", "UTF-8");
                Log.d("DemoURL", encodedURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
                out.write(encodedURL);
                out.flush();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = reader.readLine();
                Log.d("demoreturned", line);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Deleting Questions");
            dialog.setMessage("    Deleting ...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }

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