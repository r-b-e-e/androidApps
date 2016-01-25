package com.parse.starter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Priyanka on 12/9/2015.
 */
public class AlbumAdapter extends ArrayAdapter<ParseObject> {

    List<ParseObject> data;
    Context context;
    int resource;
    AlbumFragment obj = null;
    AlbumDetailFragment obj2 = null;
    MessageComposeFragment obj3 = null;
    MessageAdapter obj4;

    public AlbumAdapter(Context context, int resource, List<ParseObject> objects,AlbumFragment obj) {
        super(context, resource, objects); // should b there
        this.context = context;
        this.resource =resource;
        this.data = objects;
        this.obj = obj;
    }

    public AlbumAdapter(Context context, int resource, List<ParseObject> objects,AlbumDetailFragment obj2) {
        super(context, resource, objects); // should b there
        this.context = context;
        this.resource =resource;
        this.data = objects;
        this.obj2 = obj2;
    }
    public AlbumAdapter(Context context, int resource, List<ParseObject> objects,MessageComposeFragment obj3) {
        super(context, resource, objects); // should b there
        this.context = context;
        this.resource =resource;
        this.data = objects;
        this.obj3 = obj3;
    }

    public AlbumAdapter(Context context, int resource, List<ParseObject> objects,MessageAdapter obj4) {
        super(context, resource, objects); // should b there
        this.context = context;
        this.resource =resource;
        this.data = objects;
        this.obj4 = obj4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            // i.e. no recycle resource in pool , so we have to inflate resource
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,parent,false);
        }

        final ParseObject image = data.get(position);
        ImageView imageview = (ImageView) convertView.findViewById(R.id.imageID);
        imageview.setImageURI(Uri.parse(image.getString("imageUrl")));
        CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.RemoveID);
            checkbox.setTextColor(Color.DKGRAY);
            /*checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // delete photo from album
                        if(obj != null) {
                            image.deleteInBackground();
                            obj.refreshPhotos();
                        }
                        //refresh the adapter
                    }
                }
            });*/

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // delete photo from album
                    if(obj != null) {
                        image.deleteInBackground(new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    data.remove(image);
                                    obj.refreshPhotos();
                                }else
                                {
                                    Log.d("demo","image deleted error"+ e.getMessage());
                                }
                            }
                        });

                    }
                    //refresh the adapter
                }
            }
        });

        if(obj2 != null || obj3 != null||obj4 != null){
            checkbox.setVisibility(View.INVISIBLE);
        }

        if(obj2 != null) {
            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obj2.viewPhoto(image.getString("imageUrl"), image.getString("albumName"));
                }
            });
        }
        return convertView;
    }
}
