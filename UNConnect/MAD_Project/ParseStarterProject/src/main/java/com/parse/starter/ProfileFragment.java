package com.parse.starter;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    TextView username;
    TextView gender;
    ImageView profilepic;
    ArrayList<User> users = new ArrayList<User>();
    GridView gridView;
    Button editProfile;
    GridView albumGridView;
    Button checkmessages;

    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        username = (TextView)getActivity().findViewById(R.id.UserNameID);
        gender = (TextView)getActivity().findViewById(R.id.GenderID);
        profilepic = (ImageView)getActivity().findViewById(R.id.profilePicID);
        users = new ArrayList<User>();
        gridView = (GridView)getActivity().findViewById(R.id.UsersGridID);
        editProfile = (Button)getActivity().findViewById(R.id.btneditID);
        albumGridView = (GridView)getActivity().findViewById(R.id.AlbumGridID);
        checkmessages = (Button)getActivity().findViewById(R.id.BtnMesgID);
        // get current user details
        ParseUser user = ParseUser.getCurrentUser();
        username.setText(user.getString("UserFullName"));
        gender.setText(user.getString("gender"));
        if(null!=user.getString("imageURL")){
        profilepic.setImageURI(Uri.parse(user.getString("imageURL")));}
        // populating user list
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("isProfilePrivate",false);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    for (ParseUser usr : objects) {
                        User user = new User();
                        String fullName = usr.getString("UserFullName");
                        String names[] = fullName.split(" ");
                        user.setFname(names[0]);
                        user.setLname(names[1]);
                        user.setEmail(usr.getUsername());
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
                        if (!user.getEmail().equals(ParseUser.getCurrentUser().getUsername())) {
                            users.add(user);
                        }
                    }
                    UserAdapter adapter = new UserAdapter(getActivity().getApplicationContext(),
                            R.layout.profile_item_layout, users, ProfileFragment.this);
                    adapter.setNotifyOnChange(true);
                    gridView.setAdapter(adapter);
                } else {
                    Log.d("Users ", "Error: " + e.getMessage());
                }
            }
        });

        // Populate the user's Album  in gridview
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Album");
        query1.whereEqualTo("albumOwner", ParseUser.getCurrentUser());
        // to get other users album with share
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Album");
        query2.whereEqualTo("sharedUsers",ParseUser.getCurrentUser());
        // to get invited users
        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Album");
        query3.whereEqualTo("invitedUsers",ParseUser.getCurrentUser());
        // to view all public albums
        ParseQuery<ParseObject> query4 = ParseQuery.getQuery("Album");
        query4.whereEqualTo("accessLevel", "Public");
       // final execution
        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);
        queries.add(query3);
        queries.add(query4);
        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    DisplayAlbumAdapter dAdapter = new DisplayAlbumAdapter(getActivity().getApplicationContext(),
                            R.layout.display_album_layout,objects,ProfileFragment.this);
                    dAdapter.setNotifyOnChange(true);
                    albumGridView.setAdapter(dAdapter);
                }else
                {
                    Log.d("demo","Fetching Album Error: "+ e);
                }
            }
        });

        // edit user profile
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser usr = ParseUser.getCurrentUser();
                User user = new  User();
                String  fullName = usr.getString("UserFullName");
                if(fullName == null) {
                    user.setFname(usr.getUsername());
                    user.setLname(" ");
                }else {
                    String names[] = fullName.split(" ");
                    user.setFname(names[0]);
                    user.setLname(names[1]);
                }
                user.setEmail(usr.getUsername());
                user.setGender(usr.getString("gender"));
                user.setPhotoUrl(usr.getString("imageURL"));
                user.setDatetime((String) usr.get("createdAt"));
                user.setPassword(usr.getString("password"));
                ImageURLHolderClass.getInstance().setUser(user);
                mListener.goToEditProfile();
            }
        });

        // Add album button processing
        ImageView addAlbum = (ImageView)getActivity().findViewById(R.id.addAlbum);
        addAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToAlbum();
            }
        });
        // check Message button processing
        checkmessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToMesgInbox();
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Profile Fragment - OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void viewUserProfile(User user){

        username = (TextView)getActivity().findViewById(R.id.UserNameID);
        gender = (TextView)getActivity().findViewById(R.id.GenderID);
        profilepic = (ImageView)getActivity().findViewById(R.id.profilePicID);
        gridView = (GridView)getActivity().findViewById(R.id.UsersGridID);
        TextView usertag = (TextView)getActivity().findViewById(R.id.UsersID);
        Button edit = (Button)getActivity().findViewById(R.id.btneditID);
        checkmessages = (Button)getActivity().findViewById(R.id.BtnMesgID);
        ImageView addAlbum = (ImageView)getActivity().findViewById(R.id.addAlbum);
        username.setText(user.getFname()+ " "+ user.getLname());
        gender.setText(user.getGender());
        profilepic.setImageURI(Uri.parse(user.getPhotoUrl()));
        //makign editable elements invisible
        gridView.setVisibility(View.INVISIBLE);
        usertag.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);
        checkmessages.setVisibility(View.INVISIBLE);
        addAlbum.setVisibility(View.INVISIBLE);
        // get Parse user object
        ParseUser newUser = new ParseUser() ;
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("username",user.getEmail());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (objects.size() > 1 || objects.size() == 0) {
                    Log.d("demo","Users retreived:"+ objects.size());
                    Toast.makeText(getActivity().getApplicationContext(), "Error in loading User profile", Toast.LENGTH_SHORT).show();
                } else {
                    //populating album grid
                    Log.d("demo","New User is:"+ objects.get(0).getUsername());
                    ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Album");
                    query1.whereEqualTo("albumOwner", objects.get(0));
                    query1.whereEqualTo("accessLevel", "Public");
                    //populating all public albums
                    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Album");
                    query2.whereEqualTo("albumOwner", objects.get(0));
                    query2.whereEqualTo("invitedUsers", ParseUser.getCurrentUser());
                    // final execution
                    List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
                    queries.add(query1);
                    queries.add(query2);
                    ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
                    mainQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                DisplayAlbumAdapter dAdapter = new DisplayAlbumAdapter(getActivity().getApplicationContext(),
                                        R.layout.display_album_layout, objects, ProfileFragment.this);
                                dAdapter.setNotifyOnChange(true);
                                albumGridView.setAdapter(dAdapter);
                            } else {
                                Log.d("demo", "Fetching Album Error: " + e);
                            }
                        }
                    });
                }
            }
        });

    }

    public void viewAlbumDetail(ParseObject album){
        AlbumDataHolder.getInstance().setAlbum(album);
        mListener.goToDetailAlbumPage();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

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
        // TODO: Update argument type and name
        public void goToLoginFragment();
        public void goToEditProfile();
        public void goToAlbum();
        public void goToDetailAlbumPage();
        public void goToMesgInbox();
        public void goToProfile();
    }

}
