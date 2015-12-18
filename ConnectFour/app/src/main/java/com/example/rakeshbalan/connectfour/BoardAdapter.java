package com.example.rakeshbalan.connectfour;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by rakeshbalan on 11/11/2015.
 */
public class BoardAdapter extends BaseAdapter {

    Context context;
    int resource;
    HashMap<Integer,String> keyValuePair;

    public BoardAdapter(Context context, int resource, HashMap<Integer,String> keyValuePair) {
        this.context = context;
        this.resource = resource;
        this.keyValuePair = keyValuePair;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewBoard);

        String color = null;

        color = keyValuePair.get(position);

        if (color == null)
        {
            imageView.setImageResource(R.drawable.before_coin);
        }
        else
        {
            if (color.equals("GREEN"))
            {
                imageView.setImageResource(R.drawable.green);
            }
            else if (color.equals("RED"))
            {
                imageView.setImageResource(R.drawable.red);
            }
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return 42;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
