package com.rakeshbalancompany.inclass06;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by rakeshbalan on 10/5/2015.
 */

public class AppAdapter extends ArrayAdapter<App> {

    Context mContext;
    int mResource;
    List<App> appList;


    public AppAdapter(Context context, int resource, List<App> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.appList = objects;
    }


    @Override
    public int getCount() {

        return appList.size();
    }


    @Override
    public App getItem(int position) {

        return appList.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        App listItem = getItem(position);

        if (convertView == null) {

            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();

            convertView = inflater.inflate(mResource, parent, false);

            // Creates a ViewHolder and store references to the two children views we want to bind data to.

            holder = new ViewHolder();

            holder.appName = (TextView) convertView.findViewById(R.id.appName);
            holder.developerName = (TextView) convertView.findViewById(R.id.developerName);
            holder.releaseDate = (TextView) convertView.findViewById(R.id.releaseDate);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.category = (TextView) convertView.findViewById(R.id.category);
            holder.currency = (TextView) convertView.findViewById(R.id.currency);
            holder.appImageView = (ImageView) convertView.findViewById(R.id.appImageView);

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView and the ImageView.

            holder = (ViewHolder) convertView.getTag();
        }

        // Bind the data efficiently with the holder.
        holder.appName.setText(listItem.getAppName());
        holder.developerName.setText(listItem.getDeveloperName());
        holder.price.setText(listItem.getPrice() + listItem.getPrice());
        holder.releaseDate.setText(listItem.getReleaseDate());
        holder.category.setText(listItem.getCategory());
        holder.currency.setText(listItem.getCurrency());
        Picasso.with(mContext).load(listItem.getImageUrl()).error(R.drawable.ic_launcher).into(holder.appImageView);



        return convertView;
    }


    static class ViewHolder {
        private TextView appName,developerName,releaseDate,price, category, currency ;
        private ImageView appImageView;
    }


}
