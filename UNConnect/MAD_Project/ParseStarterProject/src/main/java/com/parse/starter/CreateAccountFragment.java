package com.parse.starter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CreateAccountFragment extends Fragment {
    String email ="";
    String cPWd ="";
    String pwdVal ="";
    String firstVal ="";
    String lastVal = "";
    String gender = "Male";
    String imageURL= "";
    ImageView imageView;
    boolean  isProfilePrivate;
    boolean isNotify;
    boolean isMessage;

    private OnFragmentInteractionListener mListener;

    public CreateAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView = (ImageView)getActivity().findViewById(R.id.ProfilePicID);
        if(ImageURLHolderClass.getInstance().getImageURL() != null){
            imageURL = ImageURLHolderClass.getInstance().getImageURL();
            imageView.setImageURI(Uri.parse(imageURL));
            ImageURLHolderClass.getInstance().setImageURL(null);

        }

        // cancel button processing
        getActivity().findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        // profile image processing
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToOpenform();

            }
        });
        //radio buttons processing
        RadioGroup rg = (RadioGroup)getActivity().findViewById(R.id.RadioGroupID);
        int id = rg.getCheckedRadioButtonId();
        if(id == R.id.MaleID){
            gender = "Male";
        } else {
            gender = "Female";
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.MaleID) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
            }
        });
        // Profile privacy
        RadioGroup rgPrivacy = (RadioGroup)getActivity().findViewById(R.id.RadioGrpPrivacyID);
        int id1 = rgPrivacy.getCheckedRadioButtonId();
        if(id1 == R.id.PPublicID){
            isProfilePrivate = false;
        } else {
            isProfilePrivate = true;
        }
        rgPrivacy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.PPublicID) {
                    isProfilePrivate = false;
                } else {
                    isProfilePrivate = true;
                }
            }
        });
        // receive message
        RadioGroup rgMessage = (RadioGroup)getActivity().findViewById(R.id.RadioGrpMesgID);
        int id2 = rgMessage.getCheckedRadioButtonId();
        if(id2 == R.id.radioMsgYesID){
            isMessage = true;
        } else {
            isMessage = false;
        }
        rgMessage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioMsgYesID) {
                    isMessage = true;
                } else {
                    isMessage = false;
                }
            }
        });
        // notify user
        RadioGroup rgNotify = (RadioGroup)getActivity().findViewById(R.id.RadioGrpNotifyID);
        int id3 = rgNotify.getCheckedRadioButtonId();
        if(id3 == R.id.radioNotifyYesID){
            isNotify = true;
        } else {
            isNotify = false;
        }
        rgNotify.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioNotifyYesID) {
                    isNotify = true;
                } else {
                    isNotify = false;
                }
            }
        });
        //  sign up button processing
        getActivity().findViewById(R.id.sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText first = (EditText) getActivity().findViewById(R.id.fNameID);
                EditText lastname = (EditText) getActivity().findViewById(R.id.LNameID);
                firstVal = first.getText().toString();
                lastVal = lastname.getText().toString();

                EditText pwd = (EditText) getActivity().findViewById(R.id.PasswordID);
                pwdVal = pwd.getText().toString();
                EditText Cpwd = (EditText) getActivity().findViewById(R.id.CPasswordID);
                cPWd = Cpwd.getText().toString();

                EditText emails = (EditText) getActivity().findViewById(R.id.EmailID);
                email = emails.getText().toString();


                if (firstVal.equals("") || email.equals("") || pwdVal.equals("") || cPWd.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Value is empty", Toast.LENGTH_SHORT).show();

                } else if (!pwdVal.equals(cPWd)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Pwds do not match", Toast.LENGTH_SHORT).show();
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                    query.whereEqualTo("email", email);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> scoreList, ParseException e) {
                            if (e == null) {
                                Log.d("user", "Retrieved " + scoreList.size() + " users");

                                if (scoreList.size() > 0) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();

                                } else {
                                    ParseUser user = new ParseUser();
                                    user.setUsername(email);
                                    user.setEmail(email);
                                    user.setPassword(pwdVal);
                                    user.put("UserFullName", firstVal + " " + lastVal);
                                    user.put("gender", gender);
                                    user.put("imageURL", imageURL);
                                    user.put("isProfilePrivate",isProfilePrivate);
                                    user.put("getMessage",isMessage);
                                    user.put("getNotification",isNotify);
                                    user.signUpInBackground(new SignUpCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                Toast.makeText(getActivity().getApplicationContext(), "Sign up Successfully", Toast.LENGTH_SHORT).show();
                                                mListener.goToLogin();

                                            } else {
                                                Toast.makeText(getActivity().getApplicationContext(), "Signup Error", Toast.LENGTH_SHORT).show();
                                                Log.d("Signup", "Failed");
                                            }
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Signup Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

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
                    + " must implement Create New Fragment - OnFragmentInteractionListener");
        }
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
//        public void onFragmentInteraction(Uri uri);
        public void goToLogin();
        public void goToOpenform();

    }

}
