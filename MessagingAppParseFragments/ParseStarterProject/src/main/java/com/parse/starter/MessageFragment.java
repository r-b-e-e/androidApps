package com.parse.starter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MessageFragment extends Fragment {
    String name = "";
    String formattedDate = "";
    String msg = "";
    String userName = "";
    Date createdAt;
    List<Message> aList;
    List<Message> tempList;
    MessageAdapter mAdapter;
    MessageAdapter mAdapter1;
    String objectId = "";

    private OnFragmentInteractionListener mListener;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        aList = new ArrayList<Message>();


        //        aList = new ArrayList<Message>();
        ParseQuery<ParseObject> queryMessage = ParseQuery.getQuery("Messages");
        queryMessage.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> messageList, ParseException e) {
                if (e == null) {
                    for (ParseObject eachMessage : messageList) {
                        name = (String) eachMessage.get("postedByName");
                        msg = (String) eachMessage.get("message");
                        createdAt = eachMessage.getCreatedAt(); //Fri Nov 06 02:50:45 EST 2015
                        formattedDate = (String) new SimpleDateFormat("MM/dd/yyyy HH:MM").format(createdAt);
                        objectId = (String) eachMessage.getObjectId();
                        userName = (String) eachMessage.get("postedByUsername");
//                        Message msgObj = new Message();
//                        msgObj.setName(eachMessage.getString("postedByName"));
//                        msgObj.setMsg(eachMessage.getString("message"));
//                        msgObj.setDate((String) new SimpleDateFormat("MM/dd/yyyy HH:MM").format(eachMessage.getCreatedAt()));


//                        Log.d("demo", name + " " + msg + " " + formattedDate);

                        Message msgObj = new Message(name, msg, formattedDate, objectId, userName);
                        aList.add(msgObj);
                    }
                    ListView listView = (ListView) getActivity().findViewById(R.id.listViewMessage);
                    mAdapter = new MessageAdapter(MessageFragment.this ,getActivity(), R.layout.activity_message_item, aList);
                    listView.setAdapter(mAdapter);

                } else {
                    Log.d("demo", "Error: " + e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

//        ListView listView = (ListView) getActivity().findViewById(R.id.listViewMessage);
//        mAdapter = new MessageAdapter(getActivity(), R.layout.activity_message_item, aList);
//        listView.setAdapter(mAdapter);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void refreshList()
    {
        mAdapter.notifyDataSetChanged();
        //mAdapter.setNotifyOnChange(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        public void composeMessage();
        public void goToLoginA();
        public void refresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_message, menu);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_message, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {

            //to refresh an Activity from within itself.
//            Intent intent = getIntent();
//            startActivity(intent);
//            finish();

            mListener.refresh();

            return true;
        }

        if (id == R.id.compose) {
//            Intent i = new Intent(getBaseContext(), ComposeActivity.class);
//            startActivity(i);

            mListener.composeMessage();
            return true;
        }

        if (id == R.id.logout) {
            ParseUser.logOut();
            Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();
            mListener.goToLoginA();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
