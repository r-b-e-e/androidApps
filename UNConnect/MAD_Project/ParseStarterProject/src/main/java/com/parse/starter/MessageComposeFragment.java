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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageComposeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MessageComposeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    Spinner dropdown ;
    EditText subject;
    EditText message;
    Button addImage;
    Button send;
    Button cancel;
    GridView imagegrid;
    List<User> users ;
    ParseUser sender;
    MessageImage image = new MessageImage();
    Message messageobj;
    AlbumAdapter adapter;
    ArrayList<ParseObject> mesgImages;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public MessageComposeFragment() {
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
        return inflater.inflate(R.layout.fragment_message_compose, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Compose Message - OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // initailizing all the views
        messageobj = new Message();
        dropdown = (Spinner)getActivity().findViewById(R.id.SpinnerUserID);
        subject = (EditText)getActivity().findViewById(R.id.editTextComposeSubject);
        message = (EditText)getActivity().findViewById(R.id.editTextMessage);
        imagegrid = (GridView)getActivity().findViewById(R.id.ImageGridID);
        send = (Button)getActivity().findViewById(R.id.buttonSendMessage);
        addImage = (Button)getActivity().findViewById(R.id.buttonAddImage);
        cancel  = (Button)getActivity().findViewById(R.id.CancelMesgID);
        users = new ArrayList<>();
        //populating the drop down with users
            ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        for (ParseUser usr : objects) {
                            User user = new User();
                            String fullName = usr.getString("UserFullName");
                            if(fullName!=null){
                            String names[] = fullName.split(" ");
                            user.setFname(names[0]);
                            user.setLname(names[1]);}
                            else
                            {
                                user.setFname( usr.getUsername());
                                user.setLname(" ");
                            }
                            user.setEmail(usr.getUsername());
                            user.setGender(usr.getString("gender"));
                            user.setPhotoUrl(usr.getString("imageURL"));
                            user.setDatetime((String) usr.get("createdAt"));
                            user.setPassword(usr.getString("password"));
                            if (!user.getEmail().equals(ParseUser.getCurrentUser().getUsername())) {
                                users.add(user);
                            }
                        }
                        String[] userNames = new String[users.size()];
                        for (int i = 0; i < users.size(); i++) {
                            userNames[i] = users.get(i).getFname() + " " + users.get(i).getLname();
                        }
                        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(),
                                android.R.layout.simple_spinner_dropdown_item, userNames);
                        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        adapter.setNotifyOnChange(true);
                        dropdown.setAdapter(adapter);
                        Log.d("demo","ImageURlposition"+ ImageURLHolderClass.getInstance().getPosition() );
                        if(ImageURLHolderClass.getInstance().getPosition() > -1) {
                            dropdown.setSelection(ImageURLHolderClass.getInstance().getPosition());
                        }
                    } else {
                        Log.d("demo ", "Error retrieving users: " + e.getMessage());
                    }
                }
            });
            dropdown.setOnItemSelectedListener(new MySpinnerItemClick(MessageComposeFragment.this));
        // image processing
        if(ImageURLHolderClass.getInstance().getImageURL() != null){
            image.setImageUrl(ImageURLHolderClass.getInstance().getImageURL());
            subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    image.setMessage_subject(subject.getText().toString());
                }
            });
            image.setSender(ParseUser.getCurrentUser());
            // saving image for message
            ParseObject parseimage = new ParseObject("MessageImage");
            parseimage.put("imageUrl", image.getImageUrl());
            parseimage.put("subject", image.getMessage_subject());
            parseimage.put("sender", image.getSender());
            parseimage.put("receiver", image.getReceiver());
            parseimage.saveInBackground();

            ImageURLHolderClass.setDataobject(null);
            // retrieving all the images
            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("MessageImage");
            query1.whereEqualTo("subject", image.getMessage_subject());
            query1.whereEqualTo("sender", ParseUser.getCurrentUser());
            query1.whereEqualTo("receiver", image.getReceiver());
            query1.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> imagelist, ParseException e) {
                    if (e == null) {
                        Log.d("image", "Retrieved " + imagelist.size() + " images");
                        adapter = new AlbumAdapter(getActivity().getApplicationContext(),
                                R.layout.album_image_layout,imagelist,MessageComposeFragment.this);
                        adapter.setNotifyOnChange(true);
                        imagegrid.setAdapter(adapter);
                    } else {
                        Log.d("image", "Error: " + e.getMessage());
                    }
                }
            });
        }
        //add image button processing
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setMessage_subject(subject.getText().toString());
                image.setSender(ParseUser.getCurrentUser());
                if(image.getMessage_subject() == null || image.getMessage_subject().equals(" ")|| image.getMessage_subject().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Please give message subject",Toast.LENGTH_SHORT).show();
                }else if(image.getReceiver() == null){
                    Toast.makeText(getActivity().getApplicationContext(), "Please give valid receiver",Toast.LENGTH_SHORT).show();
                }else {
                    mListener.goToOpenForm();
                }

            }
        });
        // button send processing
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image.getMessage_subject() == null || image.getMessage_subject().equals(" ") || image.getMessage_subject().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please give a valid subject ", Toast.LENGTH_SHORT).show();
                } else {
                    // retrieving all the images
                    mesgImages = new ArrayList<ParseObject>();
                    ParseQuery<ParseObject> query1 = ParseQuery.getQuery("MessageImage");
                    query1.whereEqualTo("subject", image.getMessage_subject());
                    query1.whereEqualTo("sender", ParseUser.getCurrentUser());
                    query1.whereEqualTo("receiver", image.getReceiver());
                    query1.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> imagelist, ParseException e) {
                            if (e == null) {
                                for (ParseObject image : imagelist) {
                                    mesgImages.add(image);
                                }
                                //saving message in parse
                                Date now = new Date();
                                String strDate = sdf.format(now);
                                ParseObject pmessage = new ParseObject("Message");
                                pmessage.put("subject", subject.getText().toString());
                                pmessage.put("receiver", messageobj.getToUser());
                                pmessage.put("sender", ParseUser.getCurrentUser());
                                pmessage.put("receivername", messageobj.getToUser().getUsername());
                                pmessage.put("sendername", ParseUser.getCurrentUser().getUsername());
                                pmessage.put("body", message.getText().toString());
                                pmessage.put("isRead", false);
                                pmessage.put("sentAt", strDate);
                                pmessage.addAll("images", mesgImages);
                                pmessage.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            mListener.goToMessageInbox();
                                            ImageURLHolderClass.getInstance().setPosition(-1);
                                            Toast.makeText(getActivity().getApplicationContext(), "Message sent successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.d("demo", "Error saving message: " + e);
                                            Toast.makeText(getActivity().getApplicationContext(), "Message not saved", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Log.d("demo", "Message Image retrieving Error: " + e.getMessage());
                            }
                        }
                    });
                }
            }
        });


        // button cancel processing
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

    public void getReceiver(String selectedItem, int position) {
        ImageURLHolderClass.getInstance().setPosition(position);
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("UserFullName", selectedItem);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> parseusers, ParseException e) {
                if (e == null) {
                    if(parseusers.size() > 0) {
                        Log.d("demo", "Receiver Retrieved :" + parseusers.get(0).getUsername() );
                        for (ParseUser usr : parseusers) {
                            messageobj.setToUser(usr);
                            image.setReceiver(usr);
                        }
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "No such Receiver exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("demo", "Sender Retrieved error: " + e.getMessage());
                }
            }
        });
    }

    public interface OnFragmentInteractionListener {

        public void goToLoginFragment();

        public void goToOpenForm();

        void goToMessageInbox();

        void goToProfile();
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

}
