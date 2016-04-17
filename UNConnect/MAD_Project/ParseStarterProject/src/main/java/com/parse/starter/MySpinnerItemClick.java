package com.parse.starter;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Priyanka, Rakesh Balan on 12/10/2015.
 */
public class MySpinnerItemClick implements AdapterView.OnItemSelectedListener {
    MessageComposeFragment obj;

    public MySpinnerItemClick(MessageComposeFragment obj) {
        this.obj = obj;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final AdapterView<?> parent1 = parent;
        final String selectedItem = parent.getItemAtPosition(position).toString();
        final int pos = position;
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("UserFullName",selectedItem);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(objects == null || objects.size() > 1 || objects.size() ==0){
                    Log.d("demo", "error in finding user");
                    Toast.makeText(parent1.getContext(),"Not a vlaid user",Toast.LENGTH_SHORT).show();
                }else
                {
                    if(objects.get(0).getBoolean("getMessage")){
                        obj.getReceiver(selectedItem,pos);
                    }else
                    {
                        Toast.makeText(parent1.getContext(),
                                "User not avialable for Messaging.Please select other User",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast.makeText(parent.getContext(),"Please select valid user",Toast.LENGTH_SHORT).show();

    }


}
