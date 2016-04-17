package com.parse.starter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Rakesh Balan on 11/2/2015.
 */
public class MessageAdapter extends ArrayAdapter<Message> {

    private Context mcontext;
    List<Message> mData;
    int mresource;

    public MessageAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);

        this.mcontext = context;
        this.mresource = resource;
        this.mData = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mresource, parent, false);
        }
        final Message msg = mData.get(position);
        TextView fName = (TextView) convertView.findViewById(R.id.fname);
        fName.setText(msg.getfName());
        TextView msgVal = (TextView) convertView.findViewById(R.id.content);
        msgVal.setText(msg.getMessageVal());
        TextView dateTime = (TextView) convertView.findViewById(R.id.dateTime);
        dateTime.setText(msg.getDate());
        final ImageView delImage = (ImageView) convertView.findViewById(R.id.delImage);
        //Picasso.with(mcontext).load(R.drawable.delete_icon).resize(50,50).into(delImage);
        Log.d("demo1",msg.getfName());
        Log.d("demo1a",ParseUser.getCurrentUser()+"");
        if(msg.getfName().equals(ParseUser.getCurrentUser().getUsername())){
            delImage.setImageURI(Uri.parse("android.resource://" + "com.parse.starter/" + R.drawable.delete_icon));
        }
        /*ParseQuery<ParseObject> query = ParseQuery.getQuery("message");
        query.whereEqualTo("userVal", ParseUser.getCurrentUser().getEmail());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> message, ParseException e) {
                if (e == null) {
                    if (message.size() > 0) {
                        for (ParseObject a : message) {
                            delImage.setImageURI(Uri.parse("android.resource://" + "com.parse.starter/" + R.drawable.delete_icon));
                        }
                    }
                } else {
                    Log.d(" demo Error: ", e.getMessage());
                }
            }
        });*/


        delImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("message");
                query.getInBackground(msg.getObjectID(), new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            object.deleteInBackground();
                            mData.remove(msg);
                            ((MessagesActivity) mcontext).refreshList();

                        } else {
                            Toast.makeText(mcontext, "Message not deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return convertView;
    }
}

