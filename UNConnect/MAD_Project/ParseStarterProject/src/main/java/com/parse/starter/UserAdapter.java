package com.parse.starter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Priyanka on 12/6/2015
 */
public class UserAdapter extends ArrayAdapter<User> {

    List<User> data;
    Context context;
    int resource;
    ProfileFragment obj;
    AlbumDetailFragment obj2;

    public UserAdapter(Context context, int resource, List<User> objects,ProfileFragment obj) {
        super(context, resource, objects); // should b there
        this.context = context;
        this.resource =resource;
        this.data = objects;
        this.obj = obj;
    }
    public UserAdapter(Context context, int resource, List<User> objects,AlbumDetailFragment obj2) {
        super(context, resource, objects); // should b there
        this.context = context;
        this.resource =resource;
        this.data = objects;
        this.obj2 = obj2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            // i.e. no recycle resource in pool , so we have to inflate resource
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,parent,false);
        }
        final User user = data.get(position);
        TextView username = (TextView) convertView.findViewById(R.id.textView);
        ImageView userImage = (ImageView) convertView.findViewById(R.id.imageView);
        username.setText(user.getFname() + " " + user.getLname());
        username.setTextColor(Color.DKGRAY);
        userImage.setImageURI(Uri.parse(user.getPhotoUrl()));
        // to see other user profile
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (obj != null) {
                    obj.viewUserProfile(user);
                }
            }
        });
        return convertView;
    }
}