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
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageInboxFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MessageInboxFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    MessageAdapter mAdapter;
    ListView mesgList;

    public MessageInboxFragment() {
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
        return inflater.inflate(R.layout.fragment_message_inbox, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MessageInbox OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // setting up all the views
        mesgList = (ListView)getActivity().findViewById(R.id.listView);
        //Parse query to fetch all the messages of login user
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
        // Retrieve the most recent ones
        query.orderByDescending("createdAt");
       // Include the user data with each comment
        query.include("receiver");
        query.whereEqualTo("receiver",ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> commentList, ParseException e) {
                if (e == null) {
                    Log.d("demo", "Message fetched : " + commentList.size() + "");
                    mAdapter = new MessageAdapter(MessageInboxFragment.this, getActivity(), R.layout.message_item_layout, commentList);
                    mAdapter.setNotifyOnChange(true);
                    mesgList.setAdapter(mAdapter);

                } else {
                    Log.d("demo", "Message fetched error : " + e.getMessage());
                }
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_message, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_compose){
            mListener.goTocomposeMessage();
            return true;
        }

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

    public void composeMessage() {
        mListener.goTocomposeMessage();
    }

    public void refreshList() {
        mAdapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        public void goTocomposeMessage();
        public void goToLoginFragment();

        void goToProfile();
    }

}
