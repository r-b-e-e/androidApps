package com.parse.starter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Rakesh Balan on 12/9/2015.
 */
public class MessageAdapter  extends ArrayAdapter<ParseObject> {

    List<ParseObject> data;
    Context context;
    int resource;
    MessageInboxFragment obj = null;
    AlbumAdapter adapter;

    public MessageAdapter(MessageInboxFragment obj,Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects); // should b there
        this.context = context;
        this.resource = resource;
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

        final ParseObject mesg = data.get(position);
        TextView msgSubject = (TextView)convertView.findViewById(R.id.txtSubjectID);
        TextView msgbody = (TextView)convertView.findViewById(R.id.txtMessageID);
        TextView msgDatetime = (TextView)convertView.findViewById(R.id.txtDateID);
        final TextView msgSender = (TextView)convertView.findViewById(R.id.txtSenderID);
        Button deletemsg = (Button)convertView.findViewById(R.id.btnDeleteID);
        Button reply = (Button)convertView.findViewById(R.id.btnReplyID);
        final GridView gridview = (GridView)convertView.findViewById(R.id.imageGridID);
        RelativeLayout rel = (RelativeLayout)convertView.findViewById(R.id.RelativeMesgID);
        // populating all the views
        msgSubject.setText("Subject: "+ mesg.getString("subject"));
        msgbody.setText("Message: "+mesg.getString("body"));
        msgDatetime.setText("Sent at: "+ mesg.getString("sentAt"));
        mesg.getParseObject("sender").fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                String fullName = null;
                if (e == null) {
                    fullName = object.getString("UserFullName");
                    if (fullName != null) {
                        String names[] = fullName.split(" ");
                        msgSender.setText("From: " + names[0] + " " + names[1]);
                    }
                } else {
                    Log.d("demo", "Fetching Mesg sender Error: " + e.getMessage());
                }
            }
        });
        // getting all the message in gridview
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("MessageImage");
        query1.whereEqualTo("subject", mesg.getString("subject"));
        query1.whereEqualTo("sender", mesg.get("sender"));
        query1.whereEqualTo("receiver", ParseUser.getCurrentUser());
        query1.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> imagelist, ParseException e) {
                if (e == null) {
                    if (imagelist.size() > 0) {
                        adapter = new AlbumAdapter(getContext(), R.layout.album_image_layout, imagelist, MessageAdapter.this);
                        gridview.setAdapter(adapter);
                        adapter.setNotifyOnChange(true);
                    } else {
                        gridview.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Log.d("image", "Error: " + e.getMessage());
                }
            }
        });
        deletemsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                ParseQuery query = ParseQuery.getQuery("MessageImage");
                query.whereEqualTo("subject", mesg.getString("subject"));
                query.whereEqualTo("receiver", ParseUser.getCurrentUser());
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> commentList, ParseException e) {
                        Log.d("demo","Message Image fetched:"+ commentList.size());
                        if (e == null) {
                            for (ParseObject com : commentList) {
                                com.deleteInBackground(new DeleteCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Toast.makeText(view.getContext(), "Message Images deleted successfully",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d("demo", "Message fetched error : " + e.getMessage());
                        }
                    }
                });
                mesg.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            data.remove(mesg);
                            obj.refreshList();
                            Toast.makeText(view.getContext(), "Message deleted successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.composeMessage();
            }
        });

        if(mesg.getBoolean("isRead")){
            convertView.setAlpha(.5f);
        }

        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                // update the mesg isRead parameter
                mesg.put("isRead", true);
                mesg.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(view.getContext(),
                                    "Message marked as Read", Toast.LENGTH_SHORT).show();
                            view.setAlpha(.5f);
                        }
                    }
                });
            }
        });

        return convertView;
    }
}

