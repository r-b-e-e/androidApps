package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Rakesh Balan on 11/2/2015.
 */


public class ComposeActivity extends AppCompatActivity {
    String test = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Compose Message");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        Button btn = (Button) findViewById(R.id.send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed = (EditText) findViewById(R.id.bigText);
                test = ed.getText().toString();
                if (test.equals("")) {
                    Toast.makeText(ComposeActivity.this, "Enter a Value", Toast.LENGTH_SHORT).show();
                } else {
                    ParseObject parseObject = new ParseObject("message");
                    parseObject.put("messages", test);
                    parseObject.put("createdBy",ParseUser.getCurrentUser().getUsername() );
                    parseObject.put("userVal",ParseUser.getCurrentUser().getUsername() );
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(ComposeActivity.this, "Message Stored Successfully", Toast.LENGTH_SHORT).show();
                                Log.d("as", "Success");
                                Intent i= new Intent(ComposeActivity.this,MessagesActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                // something went wrong
                                Toast.makeText(ComposeActivity.this, "Message Save Failure", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

    }
