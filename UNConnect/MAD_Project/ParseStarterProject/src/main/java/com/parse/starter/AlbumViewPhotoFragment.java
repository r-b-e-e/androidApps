package com.parse.starter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


/**
 * Created by Rakesh Balan on 12/9/2015.
 */
public class AlbumViewPhotoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    ImageView photo;
    ImageButton close;
    TextView title;

    public AlbumViewPhotoFragment() {
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
        return inflater.inflate(R.layout.fragment_album_view_photo, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Album - View Photo - OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        photo = (ImageView)getActivity().findViewById(R.id.photoID);
        close = (ImageButton)getActivity().findViewById(R.id.closeID);
        title = (TextView)getActivity().findViewById(R.id.albumtitleID);

        if(ImageURLHolderClass.getInstance().getImageURL() != null){
            
            photo.setImageURI(Uri.parse(ImageURLHolderClass.getInstance().getImageURL()));
            if(ImageURLHolderClass.getInstance().getTitle() != null){
                title.setText(ImageURLHolderClass.getInstance().getTitle());
            }else
            {
                title.setText(" ");
            }
            ImageURLHolderClass.getInstance().setImageURL(null);
        }else
        {
            photo.setImageURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.avatar));
            Toast.makeText(getActivity().getApplicationContext(),"Photo upload error",Toast.LENGTH_SHORT).show();
        }
        
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Album");
                query.whereEqualTo("albumTitle", ImageURLHolderClass.getInstance().getTitle());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 1 && objects.size() == 0) {
                                getActivity().getFragmentManager().popBackStack();
                            }else
                            {
                                AlbumDataHolder.getInstance().setAlbum(objects.get(0));
                                mListener.goToAlbumFragment();
                            }

                        }
                    }
                });

            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void goToLoginFragment();

        public void goToAlbumFragment();

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
