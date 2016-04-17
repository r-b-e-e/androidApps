package com.parse.starter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


// Created by Rakesh Balan Lingakumar

public class AlbumDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    TextView albumTitle;
    TextView albumOwner;
    TextView accessLevel;
    GridView photGrid;
    GridView shareGrid;
    GridView inviteGrid;
    Button delete;
    Button cancel;
    Album album;
    ParseObject parseAlbum;
    ParseUser owner;
    List<ParseObject> imageList = new ArrayList<>();

    public AlbumDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_detail, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Album Detail OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(AlbumDataHolder.getInstance().getAlbum() != null) {
            // initializing all the views
            albumTitle = (TextView) getActivity().findViewById(R.id.TitleAlbumID);
            albumOwner = (TextView) getActivity().findViewById(R.id.OwnerNameID);
            accessLevel = (TextView) getActivity().findViewById(R.id.AccessAlbumID);
            photGrid = (GridView) getActivity().findViewById(R.id.GridPhotoID);
            shareGrid = (GridView) getActivity().findViewById(R.id.GridShareUserID);
            inviteGrid = (GridView) getActivity().findViewById(R.id.GridInviteID);
            delete = (Button) getActivity().findViewById(R.id.DeleteBtnID);
            cancel = (Button) getActivity().findViewById(R.id.CancelBtnID);
            final Button addImage = (Button)getActivity().findViewById(R.id.btnAddPicsID);
            addImage.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
            album = new Album();
            parseAlbum = AlbumDataHolder.getInstance().getAlbum();
            // setting all the values in page
            albumTitle.setText(parseAlbum.getString("albumTitle"));
            accessLevel.setText(parseAlbum.getString("accessLevel"));
            // Parse query to get owner name fromm User classString fullName
            parseAlbum.getParseObject("albumOwner").fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    String fullName = null;
                    if(e == null) {
                        fullName = object.getString("UserFullName");
                        if(fullName != null) {
                            String names[] = fullName.split(" ");
                            albumOwner.setText(names[0] + " " + names[1]);
                        }
                    }else {
                        Log.d("demo", "Fetching Album Image Error: " + e.getMessage());
                    }
                }
            });
            // SAVING ADDED IMAGE
            if(ImageURLHolderClass.getInstance().getImageURL() != null){
                // saving image for album
                ParseObject parseimage = new ParseObject("AlbumImage");
                parseimage.put("albumName", parseAlbum.getString("albumTitle"));
                parseimage.put("imageUrl", ImageURLHolderClass.getInstance().getImageURL());
                parseimage.put("useremail", ParseUser.getCurrentUser().getUsername());
                parseimage.saveInBackground();
                ImageURLHolderClass.setDataobject(null);
                AlbumDataHolder.getInstance().setAlbum(null);
            }
            //Parse query to get all photos
            ParseQuery<ParseObject> queryPhoto = ParseQuery.getQuery("AlbumImage");
            queryPhoto.whereEqualTo("albumName", parseAlbum.getString("albumTitle"));
            queryPhoto.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> images, ParseException e) {
                    if(e == null){
                        for(ParseObject img : images){
                            imageList.add(img);
                        }
                        AlbumAdapter pAdapter = new AlbumAdapter(getActivity().getApplicationContext(),
                                R.layout.album_image_layout,images,AlbumDetailFragment.this);
                        pAdapter.setNotifyOnChange(true);
                        photGrid.setAdapter(pAdapter);
                    }else
                    {
                        Log.d("demo", "Fetching Album Image Error: " + e.getMessage());
                    }
                }
            });
            // Parse query to get all shared user list
            final List<User> sharedUsers = new ArrayList<>();
            JSONArray sharedUser = parseAlbum.getJSONArray("sharedUsers");
            List<String> shareuserObjectIDs = new ArrayList<>() ;
            for(int i = 0; i < sharedUser.length();i++) {
                try {
                    shareuserObjectIDs.add(sharedUser.getJSONObject(i).getString("objectId"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("demo","Object Id error:"+ e.getMessage());
                }
            }
            ParseQuery<ParseUser> querySuser = ParseQuery.getQuery(ParseUser.class);
            querySuser.whereContainedIn("objectId", shareuserObjectIDs);
            querySuser.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> users, ParseException e) {
                    if (e == null) {
                        for (ParseUser usr : users) {
                            User user = new User();
                            String fullName = usr.getString("UserFullName");
                            if(fullName == null){
                                user.setFname(usr.getUsername());
                                user.setLname(" ");
                            }else {
                                String names[] = fullName.split(" ");
                                user.setFname(names[0]);
                                user.setLname(names[1]);
                            }
                            user.setEmail(usr.getEmail());
                            if(usr.getString("gender") == null){
                                user.setGender("Male");
                            }else {
                                user.setGender(usr.getString("gender"));
                            }
                            if(usr.getString("imageURL")== null) {
                                user.setPhotoUrl("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.avatar);
                            }else
                            {
                                user.setPhotoUrl(usr.getString("imageURL"));
                            }
                            user.setDatetime((String) usr.get("createdAt"));
                            user.setPassword(usr.getString("password"));
                            sharedUsers.add(user);
                            }
                        UserAdapter adapter = new UserAdapter(getActivity().getApplicationContext(),
                                R.layout.profile_item_layout, sharedUsers, AlbumDetailFragment.this);
                        adapter.setNotifyOnChange(true);
                        shareGrid.setAdapter(adapter);

                    } else {
                        Log.d("demo", " share user Error: " + e.getMessage());
                    }
                }
            });

            // parse user to get all invited user list
            final List<User> invitedUser = new ArrayList<>();
            final JSONArray inviteduser = parseAlbum.getJSONArray("invitedUsers");
            List<String> inviteuserObjectIDs = new ArrayList<>() ;
            for(int i = 0; i < inviteduser.length();i++) {
                try {
                    inviteuserObjectIDs.add(inviteduser.getJSONObject(i).getString("objectId"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("demo","Invited User Object Id error:"+ e.getMessage());
                }
            }
            ParseQuery<ParseUser> queryIuser = ParseQuery.getQuery(ParseUser.class);
            queryIuser.whereContainedIn("objectId", inviteuserObjectIDs);
            queryIuser.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> users, ParseException e) {
                    if (e == null) {
                        for (ParseUser usr : users) {
                            User user = new User();
                            String fullName = usr.getString("UserFullName");
                            String names[] = fullName.split(" ");
                            user.setFname(names[0]);
                            user.setLname(names[1]);
                            user.setEmail(usr.getUsername());
                            user.setGender(usr.getString("gender"));
                            user.setPhotoUrl(usr.getString("imageURL"));
                            user.setDatetime((String) usr.get("createdAt"));
                            user.setPassword(usr.getString("password"));
                            invitedUser.add(user);
                        }
                        UserAdapter adapter = new UserAdapter(getActivity().getApplicationContext(),
                                R.layout.profile_item_layout, invitedUser, AlbumDetailFragment.this);
                        adapter.setNotifyOnChange(true);
                        inviteGrid.setAdapter(adapter);

                    } else {
                        Log.d("demo", " share user Error: " + e.getMessage());
                    }
                }
            });
            // delete button processing
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false).setTitle("Are you sure you want to delete?").
                    setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            parseAlbum.deleteInBackground();
                            for(ParseObject img: imageList){
                                img.deleteInBackground();
                            }
                            Toast.makeText(getActivity().getApplicationContext(),"Album Deleted Successfully",
                                    Toast.LENGTH_SHORT).show();
                            getActivity().getFragmentManager().popBackStack();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity().getApplicationContext(),"Album Not Deleted",Toast.LENGTH_SHORT).show();
                }
            });
            // getting album owner
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Album");
            query.include("albumOwner");
            query.whereEqualTo("albumOwner",parseAlbum.get("albumOwner"));
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null){
                        if(objects == null){
                            Log.d("demo","error in getting album owner");
                        }else
                        {
                            for(ParseObject object:objects) {
                                owner = object.getParseUser("albumOwner");
                                if (owner == ParseUser.getCurrentUser()) {
                                    delete.setVisibility(View.VISIBLE);
                                    addImage.setVisibility(View.VISIBLE);

                                } else {
                                    delete.setVisibility(View.INVISIBLE);
                                }
                            }
                            for(User usr: invitedUser){
                                if(usr.getEmail() == ParseUser.getCurrentUser().getUsername()){
                                    addImage.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });


            //add image button processing
            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.goToOpenForm();
                }
            });
            //cancel button processing;
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.goToProfile();
                }
            });

        }else
        {
            Toast.makeText(getActivity().getApplicationContext(),"Album uploading error",Toast.LENGTH_SHORT).show();
           getActivity().getFragmentManager().popBackStack();
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            ParseUser.logOut();
            ImageURLHolderClass.setDataobject(null);
            Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();
            mListener.goToLoginFragment();
            return true;
        }

        if (id == R.id.action_profile) {
            ImageURLHolderClass.setDataobject(null);
            AlbumDataHolder.setDataobject(null);
            mListener.goToProfile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewPhoto(String imageUrl, String albumName) {
        ImageURLHolderClass.getInstance().setImageURL(imageUrl);
        ImageURLHolderClass.getInstance().setTitle(albumName);
        mListener.goToViewPhoto();
    }

    public interface OnFragmentInteractionListener {

        public void goToLoginFragment();
        public void goToViewPhoto();
        public void goToProfile();

        public void goToOpenForm();
    }

}
