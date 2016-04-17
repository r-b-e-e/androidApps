/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


/**
 * Created by Rakesh Balan on 11/2/2015.
 */
public class MainActivity extends AppCompatActivity {

    String emailVal = "";
    String passVal = "";
    String userName = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

          getSupportActionBar().setDisplayShowHomeEnabled(true);
          getSupportActionBar().setTitle("MessageMe (Login)");
          getSupportActionBar().setIcon(R.mipmap.ic_launcher);

          //emailVal =email.getText().toString();
          ParseUser currentUser = ParseUser.getCurrentUser();
          if (currentUser != null) {
              Intent i = new Intent(MainActivity.this, MessagesActivity.class);
              startActivity(i);
              finish();
          } else {

              Button btn = (Button) findViewById(R.id.login);
              btn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      emailVal = ((EditText) findViewById(R.id.emailT)).getText().toString();
                      EditText pwd = (EditText) findViewById(R.id.passwordT);
                      passVal = pwd.getText().toString();
                      Log.d("test", emailVal);
                      Log.d("demo", passVal);
                      if (passVal.equals("") || emailVal.equals("")) {
                          Toast.makeText(MainActivity.this, "Value is empty", Toast.LENGTH_SHORT).show();
                      } else {
                          ParseUser.logInInBackground(emailVal, passVal, new LogInCallback() {
                              public void done(ParseUser user, ParseException e) {
                                  if (user != null) {
                                      Toast.makeText(MainActivity.this, "Logged in Success", Toast.LENGTH_SHORT).show();
                                      Log.d("as", "Successful");
                                      Intent i = new Intent(MainActivity.this, MessagesActivity.class);
                                      startActivity(i);
                                  } else {
                                      Toast.makeText(MainActivity.this, "Logged in failed", Toast.LENGTH_SHORT).show();
                                  }
                              }
                          });
                      }
                  }
              });


              Button btn1 = (Button) findViewById(R.id.createNew);
              btn1.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent newi = new Intent(MainActivity.this, SignUpActivity.class);
                      startActivity(newi);
                  }
              });

          }

          ParseAnalytics.trackAppOpenedInBackground(getIntent());
      }
  }

