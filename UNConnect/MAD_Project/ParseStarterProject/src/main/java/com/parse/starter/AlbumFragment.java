package com.parse.starter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlbumFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AlbumFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText albumTitle;
    RadioGroup rg;
    Button addPhotos;
    Button inviteUsers;
    Button shareUsers;
    Button save;
    Button cancel;
    GridView gridViewlayout;
    Album album;
    AlbumImage albumImage;
    String albumName = " ";
    ArrayList<ParseUser> shareUserlist = new ArrayList<>();
    ArrayList<ParseUser> invitedUserlist = new ArrayList<>();
    ArrayList<ParseUser>users ;
    String [] userNames = null;
    AlertDialog alert;
    AlertDialog alert1;
    String accessLevel;
    ArrayList<ParseObject> albumimages;
    AlbumAdapter adapter;

    public AlbumFragment() {
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
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Album Fragment OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        album = new Album();
        albumImage = new AlbumImage();
        albumTitle = (EditText)getActivity().findViewById(R.id.AlbumTitleID);
        save = (Button)getActivity().findViewById(R.id.btnAlbumSaveID);
        cancel=(Button)getActivity().findViewById(R.id.btnAlbumCancelID);
        addPhotos = (Button)getActivity().findViewById(R.id.AddPhotos);
        shareUsers = (Button)getActivity().findViewById(R.id.btnShareID);
        inviteUsers = (Button)getActivity().findViewById(R.id.btnInviteUsersID);
        gridViewlayout = (GridView)getActivity().findViewById(R.id.gridViewID);
        rg = (RadioGroup)getActivity().findViewById(R.id.radioGroupID);
        //fetch all the users from Parse
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                   users = new ArrayList<ParseUser>();
                    for(ParseUser usr: objects){
                        if(!usr.getUsername().equals(ParseUser.getCurrentUser().getUsername())) {
                            users.add(usr);
                        }
                    }
                    //creating alert dialog to pick users for sharing the Album
                    userNames = new String[users.size()];
                    for (int i = 0; i < users.size(); i++) {
                        if(users.get(i).getString("UserFullName") == null){
                            userNames[i] = users.get(i).getUsername();
                        }else {
                            String fullName = users.get(i).getString("UserFullName");
                            String names[] = fullName.split(" ");
                            userNames[i] = names[0] + " " + names[1];
                        }
                    }

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Select Users to Share Album").setMultiChoiceItems(userNames, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                shareUserlist.add(users.get(which));
                            }else if((shareUserlist.contains(users.get(which)))){
                                shareUserlist.remove(users.get(which));
                            }
                        }
                    }).setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //album.setSharedUsers(shareUserlist);

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(shareUserlist.size() > 0) {
                                shareUserlist.clear();
                            }
                        }
                    }).setCancelable(false);
                    alert1 = builder.create();

                    // Creating Alert dialog to invite users
                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setTitle("Pick Users to send Album invite").setMultiChoiceItems(userNames, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                invitedUserlist.add(users.get(which));
                            }else if((invitedUserlist.contains(users.get(which)))){
                                invitedUserlist.remove(users.get(which));
                            }
                        }
                    }).setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //album.setInvitedUsers(invitedUserlist);

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(invitedUserlist.size() > 0) {
                                invitedUserlist.clear();
                            }
                        }
                    }).setCancelable(false);
                    alert = builder1.create();
                } else {
                    Log.d("Users ", "Error: " + e.getMessage());
                }
            }
        });

        // populate album images
        if(ImageURLHolderClass.getInstance().getImageURL()!= null){
            albumImage.setImageUrl(ImageURLHolderClass.getInstance().getImageURL());
            albumImage.setAlbumName(albumName);
            albumImage.setUserEmail(ParseUser.getCurrentUser().getUsername());
            // saving image for album
            ParseObject image = new ParseObject("AlbumImage");
            image.put("imageUrl", albumImage.getImageUrl());
            image.put("albumName", albumImage.getAlbumName());
            image.put("useremail", albumImage.getUserEmail());
            image.saveInBackground();

            ImageURLHolderClass.setDataobject(null);

            // retrieving all the images
            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("AlbumImage");
            query1.whereEqualTo("albumName", albumName);
            query1.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> imagelist, ParseException e) {
                    if (e == null) {
                        Log.d("image", "Retrieved " + imagelist.size() + " images");
                        adapter = new AlbumAdapter(getActivity().getApplicationContext(),
                                R.layout.album_image_layout,imagelist,AlbumFragment.this);
                        adapter.setNotifyOnChange(true);
                        gridViewlayout.setAdapter(adapter);
                    } else {
                        Log.d("image", "Error: " + e.getMessage());
                    }
                }
            });
        }
        // album access level processing
        int id = rg.getCheckedRadioButtonId();
        if(id == R.id.PublicRadioID){
            accessLevel = "Public";
        }else
        {
            accessLevel = "Private";
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.PublicRadioID){
                    accessLevel = "Public";
                }else
                {
                    accessLevel = "Private";
                }
            }
        });
        //add photo processing
        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumName = albumTitle.getText().toString();
                if(albumName == null||albumName.equals(" ")|| albumName.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Please give album title",Toast.LENGTH_SHORT).show();
                }else {
                    mListener.goToOpenForm();
                }
            }
        });
        // share with other user processing
        shareUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users.size() == 0){
                    Toast.makeText(getActivity().getApplicationContext(),"No Users found",Toast.LENGTH_SHORT).show();
                }else {
                    alert1.show();
                }
            }
        });
        // invite other user processing
        inviteUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users.size() == 0){
                    Toast.makeText(getActivity().getApplicationContext(),"No Users found",Toast.LENGTH_SHORT).show();
                }else {
                    alert.show();
                }
            }
        });
        // save button processing
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumName = albumTitle.getText().toString();
                if(albumName == null||albumName.equals(" ")|| albumName.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Please give album title",Toast.LENGTH_SHORT).show();
                }else {
                    // retrieving all the images
                    albumimages = new ArrayList<ParseObject>();
                    ParseQuery<ParseObject> query1 = ParseQuery.getQuery("AlbumImage");
                    query1.whereEqualTo("albumName", albumName);
                    query1.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> imagelist, ParseException e) {
                            if (e == null) {
                                for (ParseObject image : imagelist) {
                                    albumimages.add(image);
                                }
                                    // saving album data in Parse
                                    ParseObject album = new ParseObject("Album");
                                    album.put("albumTitle", albumName);
                                    album.put("accessLevel", accessLevel);
                                    album.put("albumOwner", ParseUser.getCurrentUser());
                                    album.addAll("images", albumimages);
                                    album.addAll("sharedUsers", shareUserlist);
                                    album.addAll("invitedUsers", invitedUserlist);
                                    album.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                mListener.goToProfile();
                                                Toast.makeText(getActivity().getApplicationContext(), "Album saved successfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.d("demo", "Error saving album: " + e);
                                                Toast.makeText(getActivity().getApplicationContext(), "Album not saved", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                            } else {
                                Log.d("Retrieving image ", "Error: " + e.getMessage());
                            }
                        }
                    });

                }

            }
        });
        // cancel button processing
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStack();
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void refreshPhotos(){
        gridViewlayout.invalidateViews();
        gridViewlayout.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
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


    public interface OnFragmentInteractionListener {
        public void goToOpenForm();
        public void goToProfile();
        public void goToLoginFragment();
    }

}
