package com.example.rakeshbalan.qpxexpress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;


public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView autoCompleteFrom;
    private ArrayAdapter<String> adapterFrom;
    private AutoCompleteTextView autoCompleteTo;
    private ArrayAdapter<String> adapterTo;

    private AssetsPropertyReader assetsPropertyReader;
    private Context context;
    private Properties properties;

    private List<String> airportCodesList;
    private String[] airportCodes;

    private String origin;
    private String destination;
    private String travelDate;
    private String maxNoOfStopsString;
    private String noOfAdultsString;
    private String noOfChildString;
    private boolean refundable;

    ProgressDialog pDialog;
    Button buttonDate;
    private Integer maxNoOfStops, noOfAdults, noOfChild;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Search Flights");
        getSupportActionBar().setIcon(R.mipmap.app_icon);

        context = this;
        assetsPropertyReader = new AssetsPropertyReader(context);
        properties = assetsPropertyReader.getProperties("airportcodes.properties");

//        Log.d("demo", properties.getProperty("Aarhus - AAR - Tirstrup - Denmark - DK").toString() + properties.getProperty("Abaiang - ABF - Abaiang - Kiribati - KI").toString());

        airportCodesList = new ArrayList<String>();
        for (String property : properties.stringPropertyNames()) {
            airportCodesList.add(property);
        }

        airportCodes = new String[airportCodesList.size()];
        airportCodes = airportCodesList.toArray(airportCodes);


        adapterFrom = new ArrayAdapter<String>(this,R.layout.autocompletelist,airportCodes);
        autoCompleteFrom = (AutoCompleteTextView) findViewById(R.id.editTextFrom);
        autoCompleteFrom.setAdapter(adapterFrom);
        autoCompleteFrom.setThreshold(1);

        adapterTo = new ArrayAdapter<String>(this,R.layout.autocompletelist,airportCodes);
        autoCompleteTo = (AutoCompleteTextView) findViewById(R.id.editTextTo);
        autoCompleteTo.setAdapter(adapterTo);
        autoCompleteTo.setThreshold(1);

        buttonDate = (Button) findViewById(R.id.buttonDate);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, CalenderActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        checkBox = (CheckBox) findViewById(R.id.checkBoxRefundable);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    refundable = true;
                }
                else
                {
                    refundable = false;
                }
            }
        });

        Button flightSearchButton = (Button) findViewById(R.id.flightSearch);
        flightSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                origin = properties.getProperty(String.valueOf(autoCompleteFrom.getText().toString()));
                destination = properties.getProperty(String.valueOf(autoCompleteTo.getText().toString()));

                maxNoOfStopsString = ((EditText) findViewById(R.id.editTextNoOfStops)).getText().toString();
                noOfAdultsString = ((EditText) findViewById(R.id.editTextNoOfAdults)).getText().toString();
                noOfChildString = ((EditText) findViewById(R.id.editTextNoOfChilds)).getText().toString();

                if(origin == null || destination == null || travelDate == null || maxNoOfStopsString.equals("") || noOfAdultsString.equals("") || noOfChildString.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    maxNoOfStops = Integer.parseInt(maxNoOfStopsString);
                    noOfAdults = Integer.parseInt(noOfAdultsString);
                    noOfChild = Integer.parseInt(noOfChildString);

                    try {
                        JSONObject jsonObject = new JSONObject();
                        JSONObject requestObject = new JSONObject();
                        JSONObject passengersObject = new JSONObject();
                        JSONObject sliceZeroObject = new JSONObject();
                        sliceZeroObject.put("origin", origin);
                        sliceZeroObject.put("destination", destination);
                        sliceZeroObject.put("date", travelDate);
                        sliceZeroObject.put("maxStops", maxNoOfStops);
                        JSONArray sliceArray = new JSONArray();
                        sliceArray.put(sliceZeroObject);
                        requestObject.put("slice", sliceArray);
                        passengersObject.put("adultCount", noOfAdults);
                        passengersObject.put("childCount", noOfChild);
                        requestObject.put("passengers", passengersObject);
                        requestObject.put("solutions", 20);
                        requestObject.put("refundable", refundable);
                        jsonObject.put("request", requestObject);

                        new GetFlights(jsonObject).execute("https://www.googleapis.com/qpxExpress/v1/trips/search?key=AIzaSyCei3D5NO-_bC9Qcnims3i7hDA_N3amYec");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

           }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                travelDate = data.getStringExtra("TRAVEL_DATE");
                buttonDate.setText("TRAVEL DATE: " + travelDate);
            }
        }
    }

    public class GetFlights extends AsyncTask<String, Void, JSONObject> {

        JSONObject jsonObject;

        public GetFlights(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setTitle("Loading...");
            pDialog.setMessage("Searching for Flights...");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            pDialog.dismiss();
            if(jsonObject == null)
            {
                Toast.makeText(getApplicationContext(), "No Flights Found", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Flights Found", Toast.LENGTH_SHORT).show();

                try {


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.connect();
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
                out.write(jsonObject.toString());
                out.close();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null)
                    sb.append(line);
                br.close();

                JSONObject jsonObject = new JSONObject(sb.toString());
                return jsonObject;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("demo", e.getMessage());
            }
            return null;
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
