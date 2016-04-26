package com.example.rakeshbalan.temp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    HashMap<String, Airport> hashMapAirport;
    HashMap<String, String> hashMapCity;
    HashMap<String, String> hashMapCarrier;
    List<Option> optionList;

    private String origin;
    private String destination;
    private String travelDate;
    private boolean refundable;
    private Integer maxNoOfStops, noOfAdults, noOfChild;


    final static String AIRPORT_KEY = "airport_key";
    final static String CITY_KEY = "city_key";
    final static String CARRIER_KEY = "carrier_key";
    final static String OPTION_LIST_KEY = "option_list_key";
    final static String RETRIEVED_KEY = "retrieved_key";

    Retrieved retrieved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());

            hashMapAirport = new HashMap<String, Airport>();
            hashMapAirport = JSONUtility.JSONFunctionClass.getAirport(jsonObject);

            hashMapCity = new HashMap<String, String>();
            hashMapCity = JSONUtility.JSONFunctionClass.getCity(jsonObject);

            hashMapCarrier = new HashMap<String, String>();
            hashMapCarrier = JSONUtility.JSONFunctionClass.getCarrier(jsonObject);


//            List<String> tempList = new ArrayList<String>(hashMapCarrier.keySet());
//            for(int i=0; i<tempList.size(); i++)
//            {
//                Log.d("demo", tempList.get(i) + " " + hashMapCarrier.get(tempList.get(i)));
//            }

            origin = "CLT";
            destination = "MAA";
            travelDate = "2016-01-31";
            refundable = true;
            maxNoOfStops = 2;
            noOfAdults = 2;
            noOfChild = 2;

            retrieved = new Retrieved(origin, destination, travelDate, refundable, maxNoOfStops, noOfAdults, noOfChild);


            optionList = new ArrayList<Option>();
            optionList = JSONUtility.JSONFunctionClass.getOptionList(jsonObject, noOfAdults, noOfChild);


            Intent i = new Intent(MainActivity.this, AirportActivity.class);
            i.putExtra(AIRPORT_KEY, hashMapAirport);
            i.putExtra(CITY_KEY, hashMapCity);
            i.putExtra(CARRIER_KEY, hashMapCarrier);
            i.putExtra(OPTION_LIST_KEY, new OptionListWrapper(optionList));
            i.putExtra(RETRIEVED_KEY, retrieved);
            startActivity(i);



//            for (int i=0; i<optionList.size();i++)
//            {
//                Log.d("demo", optionList.get(i).getOptionId() + " " +
//                        optionList.get(i).getSaleTotal() + " " +
//                        optionList.get(i).getPrice().getSaleFareTotalAdult()
//                    + " " + optionList.get(i).getPrice().getSaleFareTotalChild()
//                        + " " + optionList.get(i).getPrice().getSaleTaxTotalAdult()
//                        + " " + optionList.get(i).getPrice().getSaleTaxTotalChild()
//                        + " " + optionList.get(i).getPrice().getSaleTotalAdult()
//                        + " " + optionList.get(i).getPrice().getSaleTotalChild()
//                        + " " + optionList.get(i).getNoOfAdults()
//                        + " " + optionList.get(i).getNoOfChild()
//                + " " + optionList.get(i).getTotalFlightDuration());
//
//                List<Flight> tList = new ArrayList<Flight>(optionList.get(i).getFlightList());
//                for (int j=0; j<tList.size();j++)
//                {
//                    Log.d("demo", tList.get(j).getFlightDuration()
//                    + " " + tList.get(j).getFlightCarrier()
//                            + " " + tList.get(j).getFlightNumber()
//                            + " " + tList.get(j).getConnectionDuration()
//                            + " " + tList.get(j).getArrivalTime()
//                            + " " + tList.get(j).getDepartureTime()
//                            + " " + tList.get(j).getOrigin()
//                            + " " + tList.get(j).getDestination());
//                }
//            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("json.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
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
}
