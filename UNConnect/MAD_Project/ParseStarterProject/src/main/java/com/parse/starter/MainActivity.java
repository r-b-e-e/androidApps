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
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseAnalytics;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity implements LoginFragment.OnFragmentInteractionListener,
        CreateAccountFragment.OnFragmentInteractionListener, OpenFormFragment.OnFragmentInteractionListener,
        GalleryFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener,
        EditProfileFragment.OnFragmentInteractionListener, AlbumFragment.OnFragmentInteractionListener,
        AlbumDetailFragment.OnFragmentInteractionListener,MessageInboxFragment.OnFragmentInteractionListener,
        MessageComposeFragment.OnFragmentInteractionListener,ImagesFragment.OnFragmentInteractionListener,
        AlbumViewPhotoFragment.OnFragmentInteractionListener{

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
      ParseUser currentUser = ParseUser.getCurrentUser();
      if (currentUser != null)
      {
          getSupportActionBar().setDisplayShowHomeEnabled(true);
          getSupportActionBar().setTitle(" UNConnect(Profile");
          getSupportActionBar().setIcon(R.mipmap.ic_launcher);
          getFragmentManager().beginTransaction().add(R.id.container, new ProfileFragment(), "Profile").commit();
      }
      else {
          getSupportActionBar().setDisplayShowHomeEnabled(true);
          getSupportActionBar().setTitle(" UNConnect(Login)");
          getSupportActionBar().setIcon(R.mipmap.ic_launcher);
          getFragmentManager().beginTransaction().add(R.id.container, new LoginFragment(), "login").commit();
      }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  @Override
  public void createNewAccount() {
      getSupportActionBar().setDisplayShowHomeEnabled(true);
      getSupportActionBar().setTitle(" UNConnect(Signup)");
      getSupportActionBar().setIcon(R.mipmap.ic_launcher);
      getFragmentManager().beginTransaction().replace(R.id.container, new CreateAccountFragment(), "create_new").commit();

  }

  @Override
  public void goToProfilePage() {
      getSupportActionBar().setDisplayShowHomeEnabled(true);
      getSupportActionBar().setTitle(" UNConnect(Profile)");
      getSupportActionBar().setIcon(R.mipmap.ic_launcher);

      getFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment(), "Profile").
              addToBackStack("login").commit();

  }

    @Override
    public void goToGallery() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" UNConnect(Gallery)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new GalleryFragment(), "Gallery").
                addToBackStack("OpenForm").commit();
    }

    @Override
    public void goToImages() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" UNConnect(Images)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new ImagesFragment(), "Images").
                addToBackStack("OpenForm").commit();

    }

    @Override
    public void goToLogin() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("  UNConnect(Login)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), "login").commit();

    }

    @Override
    public void goToOpenform() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("  UNConnect(Folders)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new OpenFormFragment(), "OpenForm").
                addToBackStack("Profile").commit();

    }

    @Override
    public void goTocomposeMessage() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("UNConnect(Compose Message)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new MessageComposeFragment(), "MessageCompose").
                addToBackStack("Profile").commit();

    }

    @Override
    public void goToLoginFragment() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("  UNConnect(Login)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), "login").commit();

    }

    @Override
    public void goToAlbumFragment() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("UNConnect(Album Detail)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new AlbumDetailFragment(), "AlbumDetail").commit();

    }

    @Override
    public void goToViewPhoto() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("UNConnect(Photo)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new AlbumViewPhotoFragment(), "view_photo")
        .addToBackStack("Profile").commit();
    }

    @Override
    public void goToEditProfile() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("UNConnect(Edit Profile)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new EditProfileFragment(),"editProfile")
                .addToBackStack("Profile").commit();
    }

    @Override
    public void goToAlbum() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("UNConnect(Album)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new AlbumFragment(), "Album")
                .addToBackStack("Profile").commit();
    }

    @Override
    public void goToDetailAlbumPage() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("UNConnect(Album Detail)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new AlbumDetailFragment(),"AlbumDetail")
                .addToBackStack("Profile").commit();
    }

    @Override
    public void goToMesgInbox() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("UNConnect(Inbox)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new MessageInboxFragment(), "MessageInbox").commit();

    }

    @Override
    public void goToOpenForm() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("UNConnect(Folders)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new OpenFormFragment(), "OpenForm").
                addToBackStack("editProfile").commit();

    }

    @Override
    public void goToMessageInbox() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("  UNConnect(Inbox)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getFragmentManager().beginTransaction().replace(R.id.container, new MessageInboxFragment(), "MessageInbox").commit();
    }

    @Override
    public void goToProfile() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("UNConnect(Profile)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        getFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment(), "Profile").commit();

    }

    @Override
    public void goToProfileFragment() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" UNConnect(Profile)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        getFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment(), "Profile").commit();

    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }
}
