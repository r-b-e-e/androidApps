package com.rakeshbalancompany.mad_midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MediaAdapter extends ArrayAdapter<News> {


    List<News> list;
    Context context;
    int resource;
    DatabaseDataManager manager;
    public MediaAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);

        this.list = objects;
        this.resource = resource;
        this.context = context;
//        manager = new DatabaseDataManager(this.context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        ImageView  imageView =(ImageView)convertView.findViewById(R.id.newsicon);
        TextView titleName= (TextView)convertView.findViewById(R.id.title);
        TextView relDate=(TextView)convertView.findViewById(R.id.pubdate);

        ImageView block=(ImageView)convertView.findViewById(R.id.blockbuttonimage);

        Picasso.with(context).load(list.get(position).getThumb_image()).into(imageView);
        titleName.setText(list.get(position).getTitle());
        relDate.setText(list.get(position).getPub_date());


        block.setImageResource(R.drawable.close_red);

//        if(manager.mediaExists(list.get(position).getTitle())){
//            fav.setImageResource(R.drawable.images);
//        }
//        else{
//            fav.setImageResource(R.drawable.ic_star_border_black_24dp);
//        }

        return convertView;
    }
}
