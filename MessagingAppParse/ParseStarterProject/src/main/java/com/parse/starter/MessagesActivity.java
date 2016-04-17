package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakesh Balan on 11/2/2015.
 */

public class MessagesActivity extends AppCompatActivity {
    EditText enterText;
    ArrayList<Message> items = new ArrayList<Message>();
    MessageAdapter adapter;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("MessageMe");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        lv = (ListView) findViewById(R.id.listView);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("message");
        //query.include("createdBy");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    for (ParseObject a : scoreList) {
                        Log.d("demo",scoreList.size()+"");
                        Message m = new Message(a.get("userVal") + "", a.get("messages") + "", a.getCreatedAt() + "", a.get("email") + "",a.getObjectId());
                        items.add(m);
                    }
                } else {
                    Log.d("demo",e.toString());
                    Toast.makeText(getApplicationContext(), "No message is Parse API", Toast.LENGTH_SHORT).show();
            }
        }
    });
            adapterMethod(items);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
                    // Handle action bar item clicks here. The action bar will
                    // automatically handle clicks on the Home/Up button, so long
                    // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
                    //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            adapterMethod(items);
        }
        if (id == R.id.compose) {
            Intent iCompose = new Intent(MessagesActivity.this, ComposeActivity.class);
            startActivity(iCompose);
        }
        if (id == R.id.logout) {
            ParseUser.logOut();
            Intent i = new Intent(MessagesActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void refreshList() {
        adapter.notifyDataSetChanged();
    }

    private void adapterMethod(ArrayList<Message> items) {
        adapter = new MessageAdapter(MessagesActivity.this, R.layout.test_layout, items);
        lv.setAdapter(adapter);

    }
}