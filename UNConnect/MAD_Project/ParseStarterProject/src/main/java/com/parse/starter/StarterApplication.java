/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;


/**
 * Created by Rakesh Balan on 12/9/2015.
 */
public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "IsXkaix7cpakLHOplvicprhGBTP8ewPsRXjaj37h", "VKPsP6NLIs5zj794no438ZiFd9UFIbkezS920D8w");
    ParseInstallation.getCurrentInstallation().saveInBackground();
    ParseTwitterUtils.initialize("xh8JKpZdi32I6TzMboHcMHEmA", "qAqXtUn3MRXcqyHfhdLgDDbvKYIpBOEt6EeCLZelD3DYfBpu0V");
    ParseFacebookUtils.initialize(this);
    //ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    defaultACL.setPublicReadAccess(true);
    defaultACL.setPublicWriteAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
