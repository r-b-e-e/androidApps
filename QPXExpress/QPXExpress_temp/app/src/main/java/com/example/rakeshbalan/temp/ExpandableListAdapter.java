package com.example.rakeshbalan.temp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rakeshbalan on 1/3/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private HashMap<String, Airport> hashMapAirport;
    private HashMap<String, String> hashMapCity;
    private HashMap<String, String> hashMapCarrier;
    private List<Option> optionList;

    public ExpandableListAdapter(Context context, HashMap<String, Airport> hashMapAirport, HashMap<String, String> hashMapCity, HashMap<String, String> hashMapCarrier, List<Option> optionList) {
        this.context = context;
        this.hashMapAirport = hashMapAirport;
        this.hashMapCity = hashMapCity;
        this.hashMapCarrier = hashMapCarrier;
        this.optionList = optionList;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.flight_travel, null);
        }

        TextView textViewPrice = (TextView) convertView.findViewById(R.id.textViewPrice);
        textViewPrice.setText(optionList.get(groupPosition).getSaleTotal());



        TextView textViewTotalDuration = (TextView) convertView.findViewById(R.id.textViewTotalDuration);
        textViewTotalDuration.setText(optionList.get(groupPosition).getTotalFlightDuration());



        ArrayList<String> intermediatiesStops = new ArrayList<String>();
        List<Flight> flightList = new ArrayList<Flight>();
        flightList = optionList.get(groupPosition).getFlightList();

        intermediatiesStops.add(flightList.get(1).getOrigin());
        for(Flight flight: flightList)
        {
            intermediatiesStops.add(flight.getDestination());
        }
        String listString = "";

        for (String s : intermediatiesStops)
        {
            listString += s + "\t";
        }

        TextView textViewIntermediateStops = (TextView) convertView.findViewById(R.id.textViewIntermediateStops);
        textViewIntermediateStops.setText(listString);

        TextView textViewStopsCount = (TextView) convertView.findViewById(R.id.textViewStopsCount);
        textViewStopsCount.setText(String.valueOf(intermediatiesStops.size()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.flight_list, null);
        }

        TextView textView2 = (TextView) convertView.findViewById(R.id.textView2);
        textView2.setText("Chumma");

        return convertView;
    }


}
