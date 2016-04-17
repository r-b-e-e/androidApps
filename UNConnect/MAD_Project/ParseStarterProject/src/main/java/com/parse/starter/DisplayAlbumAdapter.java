package com.parse.starter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Rakesh Balan on 12/9/2015.
 */
public class DisplayAlbumAdapter extends ArrayAdapter<ParseObject> {

    List<ParseObject> data;
    Context context;
    int resource;
    ProfileFragment obj;
    String albumimage;

    public DisplayAlbumAdapter(Context context, int resource, List<ParseObject> objects,ProfileFragment obj) {
        super(context, resource, objects); // should b there
        this.context = context;
        this.resource =resource;
        this.data = objects;
        this.obj = obj;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            // i.e. no recycle resource in pool , so we have to inflate resource
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,parent,false);
        }
        final ParseObject album = data.get(position);
        TextView albumname = (TextView)convertView.findViewById(R.id.albumTitleID);
        albumname.setText(album.getString("albumTitle"));
        albumname.setTextColor(Color.DKGRAY);
        final ImageView albumImage = (ImageView)convertView.findViewById(R.id.albumImageID);
        JSONArray albumImagelist = album.getJSONArray("images");
        if(albumImagelist.optJSONObject(0)!= null){
            try {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("AlbumImage");
                query.getInBackground(albumImagelist.getJSONObject(0).get("objectId").toString(), new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            albumimage = object.getString("imageUrl");
                            albumImage.setImageURI(Uri.parse(albumimage));
                        } else {
                            Log.d("demo", "Album Image error: " + e.getMessage());
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("demo","Album Image error: "+ e);
            }
        }
        albumImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.viewAlbumDetail(album);
            }
        });
        return convertView;
    }

}
