/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity implements LoginFragment.OnFragmentInteractionListener, ComposeFragment.OnFragmentInteractionListener, CreateAccountFragment.OnFragmentInteractionListener, MessageFragment.OnFragmentInteractionListener{
  String emailL="";
  String passwordL="";

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setTitle(R.string.login_page);
    getSupportActionBar().setIcon(R.mipmap.ic_launcher);

  ParseUser currentUser = ParseUser.getCurrentUser();
    if (currentUser != null)
    {
      getFragmentManager().beginTransaction().add(R.id.container, new MessageFragment(), "message").commit();
    }
    else {
      getFragmentManager().beginTransaction().add(R.id.container, new LoginFragment(), "login").commit();
    }


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }







//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu.menu_main, menu);
//    return true;
//  }
//
//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//    // Handle action bar item clicks here. The action bar will
//    // automatically handle clicks on the Home/Up button, so long
//    // as you specify a parent activity in AndroidManifest.xml.
//    int id = item.getItemId();
//
//    //noinspection SimplifiableIfStatement
//    if (id == R.id.action_settings) {
//      return true;
//    }
//
//    return super.onOptionsItemSelected(item);
//  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void createNewAccount() {
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setTitle(R.string.signup_page);
    getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    getFragmentManager().beginTransaction().replace(R.id.container, new CreateAccountFragment(), "create_new").
            addToBackStack("login").commit();
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void goToMessagePage() {
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setTitle(R.string.listview_page);
    getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    getFragmentManager().beginTransaction().replace(R.id.container, new MessageFragment(), "message").
            addToBackStack("login").commit();
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void goToLogin() {
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setTitle(R.string.login_page);
    getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), "login").
            addToBackStack(null).commit();
  }



  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void onBackPressed() {

    if(getFragmentManager().getBackStackEntryCount() > 0)
    {
      getFragmentManager().popBackStack();
    }else
    {
      super.onBackPressed();
    }
  }


  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void composeMessage() {
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setTitle(R.string.compose_page);
    getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    getFragmentManager().beginTransaction().replace(R.id.container, new ComposeFragment(), "compose").
            addToBackStack("message").commit();
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void goToLoginA() {
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setTitle(R.string.login_page);
    getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), "login").
            addToBackStack(null).commit();
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void refresh() {
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setTitle(R.string.listview_page);
    getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    getFragmentManager().beginTransaction().replace(R.id.container, new MessageFragment(), "message").
            addToBackStack("login").commit();
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void discard() {
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setTitle(R.string.listview_page);
    getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    getFragmentManager().beginTransaction().replace(R.id.container, new MessageFragment(), "message").
            addToBackStack("login").commit();
  }


}
