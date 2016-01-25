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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EditProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private User user ;
    String firstVal ="";
    String lastVal = "";
    String gender = " ";
    String imageURL= "";

    public EditProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_editprofile, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Edit Profile - OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(ImageURLHolderClass.getInstance().getUser() != null) {
            user = ImageURLHolderClass.getInstance().getUser();
            final EditText firstname = (EditText) getActivity().findViewById(R.id.fNameEditID);
            firstname.setText(user.getFname());
            final EditText lastname = (EditText) getActivity().findViewById(R.id.LNameEditID);
            lastname.setText(user.getLname());
            RadioGroup rg = (RadioGroup) getActivity().findViewById(R.id.RadioGroupEditID);
            if (user.getGender().equals("Male")) {
                rg.check(R.id.MaleEditID);
            } else {
                rg.check(R.id.FemaleEditID);
            }
            ImageView image = (ImageView) getActivity().findViewById(R.id.ProfilePicEditID);
            if(ImageURLHolderClass.getInstance().getImageURL() != null){
                image.setImageURI(Uri.parse(ImageURLHolderClass.getInstance().getImageURL()));
            }else {
                image.setImageURI(Uri.parse(user.getPhotoUrl()));
            }
            //radiogroup change
            gender = user.getGender();
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.MaleEditID) {
                        gender = "Male";
                    } else {
                        gender = "Female";
                    }
                }
            });
            //image change processing
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.goToOpenForm();
                }
            });
            // button edit processing
            Button btnEdit = (Button) getActivity().findViewById(R.id.editBtnID);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ImageURLHolderClass.getInstance().getImageURL() == null) {
                        imageURL = user.getPhotoUrl();
                    } else {
                        imageURL = ImageURLHolderClass.getInstance().getImageURL();
                        ImageURLHolderClass.getInstance().setImageURL(null);
                    }
                    ParseUser user = ParseUser.getCurrentUser();
                    user.put("UserFullName", firstname.getText().toString() + " " + lastname.getText().toString());
                    user.put("gender", gender);
                    user.put("imageURL", imageURL);
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                mListener.goToProfileFragment();
                            }
                        }
                    });

                }
            });
        }else{
            Toast.makeText(getActivity().getApplicationContext(),
                    "Profile uploading error", Toast.LENGTH_SHORT).show();
            Log.d("demo", "edit profile error");
        }

        // button cancel processing
        Button btnCancel = (Button)getActivity().findViewById(R.id.cancelBtnID);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mListener.goToProfile();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void goToOpenForm();
        public void goToProfileFragment();
        public void goToLoginFragment();

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
