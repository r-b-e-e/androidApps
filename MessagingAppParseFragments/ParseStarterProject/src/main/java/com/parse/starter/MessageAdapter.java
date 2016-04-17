package com.parse.starter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by rakeshbalan on 11/6/2015.
 */
public class MessageAdapter extends ArrayAdapter<Message> {

    List<Message> mList;
    Context context;
    int resource;
    MessageFragment messageFragment;

    public interface update{
        public void refreshList();
    }

    public MessageAdapter(MessageFragment messageFragment, Context context, int resource, List<Message> objects) {
        super(context, resource, objects);

        this.mList = objects;
        this.resource = resource;
        this.context = context;
        this.messageFragment =messageFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

//        *******************************************************
        final Message mess = mList.get(position);
//        *******************************************************


        TextView textViewName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView textViewMessage = (TextView) convertView.findViewById(R.id.textViewMessage);
        TextView textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);

        textViewName.setText(mList.get(position).getName());
        textViewMessage.setText(mList.get(position).getMsg());
        textViewDate.setText(mList.get(position).getDate());

        ImageView imgBtn = (ImageView) convertView.findViewById(R.id.imageViewDelete);

        if (mList.get(position).getUserName().equals(ParseUser.getCurrentUser().getUsername()))
        {
            imgBtn.setImageResource(R.drawable.delete_icon);
        }
        else
        {
            imgBtn.setImageResource(0);
        }

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Messages");
                //        *******************************************************
                query.getInBackground(mess.getObjectId(), new GetCallback<ParseObject>() {
                //        *******************************************************
                    @Override
                    public void done(ParseObject object, com.parse.ParseException e) {
                        if (e == null) {
                            object.deleteInBackground();

                            mList.remove(mess);


                            messageFragment.refreshList();


                        } else {
                            Log.d("demo", "Error: " + e.getMessage());
                        }
                    }
                });

            }
        });

        return convertView;
    }
}
