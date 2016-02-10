package com.example.rakeshbalan.temp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rakeshbalan on 1/2/2016.
 */
public class AirportActivity extends AppCompatActivity {
    HashMap<String, Airport> hashMapAirport;
    HashMap<String, String> hashMapCity;
    HashMap<String, String> hashMapCarrier;
    List<Option> optionList;
    OptionListWrapper optionListWrapper;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    Retrieved retrieved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);

        if(getIntent().getExtras()!=null){
            if (getIntent().getExtras().containsKey(MainActivity.AIRPORT_KEY)) {
                hashMapAirport = (HashMap<String, Airport>) getIntent().getExtras().getSerializable(MainActivity.AIRPORT_KEY);
            }
            if (getIntent().getExtras().containsKey(MainActivity.CITY_KEY)) {
                hashMapCity = (HashMap<String, String>) getIntent().getExtras().getSerializable(MainActivity.CITY_KEY);
            }
            if (getIntent().getExtras().containsKey(MainActivity.CARRIER_KEY)) {
                hashMapCarrier = (HashMap<String, String>) getIntent().getExtras().getSerializable(MainActivity.CARRIER_KEY);
            }
            if (getIntent().getExtras().containsKey(MainActivity.OPTION_LIST_KEY)) {
                optionListWrapper = (OptionListWrapper) getIntent().getExtras().getSerializable(MainActivity.OPTION_LIST_KEY);
            }
            if (getIntent().getExtras().containsKey(MainActivity.RETRIEVED_KEY)) {
                retrieved = (Retrieved) getIntent().getExtras().getSerializable(MainActivity.RETRIEVED_KEY);
            }
        }

        optionList = optionListWrapper.getOptionList();


        TextView originShortForm = (TextView) findViewById(R.id.originShortForm);
        TextView destinationShortForm = (TextView) findViewById(R.id.destinationShortForm);
        TextView textViewOriginFullForm = (TextView) findViewById(R.id.textViewOriginFullForm);
        TextView textViewDestinationFullForm = (TextView) findViewById(R.id.textViewDestinationFullForm);
        TextView textViewTravelDate = (TextView) findViewById(R.id.textViewTravelDate);
        TextView textViewAdultCount = (TextView) findViewById(R.id.textViewAdultCount);
        TextView textViewChildCount = (TextView) findViewById(R.id.textViewChildCount);
        TextView textViewMaxStops = (TextView) findViewById(R.id.textViewMaxStops);
        ImageView imageViewRefundable = (ImageView) findViewById(R.id.imageViewRefundable);

        originShortForm.setText(retrieved.getOrigin());
        destinationShortForm.setText(retrieved.getDestination());
        textViewOriginFullForm.setText(hashMapAirport.get(retrieved.getOrigin()).getAirportName() + " Airport");
        textViewDestinationFullForm.setText(hashMapAirport.get(retrieved.getDestination()).getAirportName() + " Airport");
        textViewTravelDate.setText(retrieved.getTravelDate());
        textViewAdultCount.setText(String.valueOf(retrieved.getNoOfAdults()));
        textViewChildCount.setText(String.valueOf(retrieved.getNoOfChild()));
        textViewMaxStops.setText(String.valueOf(retrieved.getMaxNoOfStops()));

        if(retrieved.isRefundable())
        {
            imageViewRefundable.setImageResource(R.drawable.yes);
        }
        else
        {
            imageViewRefundable.setImageResource(R.drawable.no);
        }

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListAdapter = new ExpandableListAdapter(this, hashMapAirport, hashMapCity, hashMapCarrier, optionList);
        expandableListView.setAdapter(expandableListAdapter);



//        List<String> tempList = new ArrayList<String>(hashMapAirport.keySet());
//        for(int i=0; i<tempList.size(); i++)
//        {
//            Log.d("demo", tempList.get(i) + " " + hashMapAirport.get(tempList.get(i)).getAirportName() + " " + hashMapAirport.get(tempList.get(i)).getAirportCity());
//        }

//        tempList = new ArrayList<String>(hashMapCity.keySet());
//        for(int i=0; i<tempList.size(); i++)
//        {
//            Log.d("demo", tempList.get(i) + " " + hashMapCity.get(tempList.get(i)));
//        }
//
//        tempList = new ArrayList<String>(hashMapCarrier.keySet());
//        for(int i=0; i<tempList.size(); i++)
//        {
//            Log.d("demo", tempList.get(i) + " " + hashMapCarrier.get(tempList.get(i)));
//        }

//        Log.d("demo","1");
//        for (int i=0; i< optionList.size();i++)
//        {
//            Log.d("demo", optionList.get(i).getOptionId() + " " +
//                    optionList.get(i).getSaleTotal() + " " +
//                    optionList.get(i).getPrice().getSaleFareTotalAdult()
//                    + " " + optionList.get(i).getPrice().getSaleFareTotalChild()
//                    + " " + optionList.get(i).getPrice().getSaleTaxTotalAdult()
//                    + " " + optionList.get(i).getPrice().getSaleTaxTotalChild()
//                    + " " + optionList.get(i).getPrice().getSaleTotalAdult()
//                    + " " + optionList.get(i).getPrice().getSaleTotalChild()
//                    + " " + optionList.get(i).getNoOfAdults()
//                    + " " + optionList.get(i).getNoOfChild()
//                    + " " + optionList.get(i).getTotalFlightDuration());
//
//            List<Flight> tList = new ArrayList<Flight>(optionList.get(i).getFlightList());
//            for (int j=0; j<tList.size();j++)
//            {
//                Log.d("demo", tList.get(j).getFlightDuration()
//                + " " + tList.get(j).getFlightCarrier()
//                        + " " + tList.get(j).getFlightNumber()
//                        + " " + tList.get(j).getConnectionDuration()
//                        + " " + tList.get(j).getArrivalTime()
//                        + " " + tList.get(j).getDepartureTime()
//                        + " " + tList.get(j).getOrigin()
//                        + " " + tList.get(j).getDestination());
//            }
//        }
//        Log.d("demo","2");

    }
}
