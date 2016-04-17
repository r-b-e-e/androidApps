package com.parse.starter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

//import android.support.v4.app.Fragment;

/**
 * Created by Rakesh Balan on 12/9/2015.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment{
    ParseUser parseUser;
    List<String> permissions;
    ImageView imageViewFacebook, imageViewTwitter;
    String emailL="";
    String passwordL="";
    String email, name;
    private OnFragmentInteractionListener mListener;
User user= new User();
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageViewFacebook = (ImageView) getView().findViewById(R.id.imageViewFacebook);
        imageViewTwitter = (ImageView) getView().findViewById(R.id.imageViewTwitter);
        getActivity().findViewById(R.id.button_create_new_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createNewAccount();
            }
        });


        getActivity().findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailL = ((EditText) getActivity().findViewById(R.id.textEmail)).getText().toString();
                passwordL = ((EditText) getActivity().findViewById(R.id.textPassword)).getText().toString();

                if (emailL.equals("") || passwordL.equals("")) {
                    Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    ParseUser.logInInBackground(emailL, passwordL, new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
                                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                Log.d("demo", "OK");
                                ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                                installation.put("username", ParseUser.getCurrentUser().getUsername());
                                installation.saveInBackground();
                                mListener.goToProfilePage();
                                //((EditText) getActivity().findViewById(R.id.textEmail)).setText("");
                               // ((EditText) getActivity().findViewById(R.id.textPassword)).setText("");
                            } else {
                                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        imageViewTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ParseTwitterUtils.logIn(getActivity(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("loginTw", "Uh oh. The user cancelled the Twitter login.");
                        } else if (user.isNew()) {
                            Log.d("loginTw", "User signed up and logged in through Twitter!");
                            getTwitterUsername();
                            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                            installation.put("username", ParseUser.getCurrentUser().getUsername());
                            installation.put("privacy", "no");

                            installation.saveInBackground();
//                    Log.d("loginTw", ParseTwitterUtils.getTwitter().getScreenName().toString());
                            mListener.goToProfilePage();

                        } else {
                            Log.d("loginTw", "User logged in through Twitter!");
                            getTwitterUsername();
                            Log.d("loginTw", ParseTwitterUtils.getTwitter().getScreenName().toString());

                            mListener.goToProfilePage();

                            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                            installation.put("username", ParseUser.getCurrentUser().getUsername());


                            installation.saveInBackground();

                        }
                    }
                });
            }
        });

        /**
         * FB Auth
         */

        imageViewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("loginFB", "Inside Button Click");
//                matcher = pattern.matcher(email);

                ParseFacebookUtils.logInWithReadPermissionsInBackground(getActivity(),
                        permissions = Arrays.asList("public_profile", "email"),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException err) {
                                Log.d("loginFB", "Testing FB login");
                                if (err != null) {
                                    Log.d("loginFB", "This is an error: " + err.toString());
                                }
                                if (user == null) {
                                    Log.d("loginFB", "Uh oh. The user cancelled the Facebook login.");
                                } else if (user.isNew()) {
                                    Log.d("loginFB", "User signed up and logged in through Facebook!" + ParseUser.getCurrentUser());
                                    fbLoadUserDetails();
                                    mListener.goToProfilePage();

                                } else {
                                    Log.d("loginFB", "User logged in through Facebook!" + ParseUser.getCurrentUser());

                                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();

                                    installation.put("username", ParseUser.getCurrentUser().getUsername());
                                    installation.saveInBackground();

                                    mListener.goToProfilePage();
                                }

                            }
                        });

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
                    + " must implement Login Fragment - OnFragmentInteractionListener");
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private void fbLoadUserDetails() {
        Log.d("loginfbsave", "inside user det");
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            Log.d("loginfbsave", "Accessing from id: " + profile.getFirstName());
        } else {
            return;
        }
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        Log.d("loginfbsave", "saving to parse with details");

                        try {
                            String[] test = {};
                            name = graphResponse.getJSONObject().getString("name");
                            user.setFname(name);
                            email = graphResponse.getJSONObject().getString("email");
                            user.setEmail(email);
                            Log.d("loginfbsave", "user--" + user.toString() + "--email--" + email.toString());
                            /**
                             * Saving to Parse
                             */
                            Log.d("loginFBload", "updating parse site with details");
                            parseUser = ParseUser.getCurrentUser();
                            parseUser.put("name", name);
                            parseUser.setUsername(email.split("@")[0]); //Takes the deepakrohan from deepakrohan@facebook.com
                            parseUser.setEmail(email);

                            parseUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null) {
                                        Log.d("loginFBErr", "Storing Parse error: " + e.toString());
                                    } else {
                                        Toast.makeText(getActivity(), "name:" + name + " email: " + email, Toast.LENGTH_SHORT).show();
                                        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                                        installation.put("privacy","no");
                                        installation.put("username", ParseUser.getCurrentUser().getUsername());
                                        installation.saveInBackground();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            Log.d("loginfbsaveErr", e.toString());
                        }
                    }
                }).executeAsync();
    }
    /**
     * Twitter get username
     * TODO Add this piece of code
     */
    public void getTwitterUsername(){
        Log.d("loginTwitterUser", "Trying to update the twitter username");
        ParseUser tuser = ParseUser.getCurrentUser();
        tuser.setUsername("t" + ParseTwitterUtils.getTwitter().getScreenName().toString());
        tuser.put("name", ParseTwitterUtils.getTwitter().getScreenName().toString());
        tuser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if( e== null){
                    Log.d("loginTwitterUser", "User is stored");
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put("username", ParseUser.getCurrentUser().getUsername());
                    installation.put("privacy","no");

                    installation.saveInBackground();
                }else {
                    Log.d("loginTwitterUser","Failed");
                }
            }
        });
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void createNewAccount();
        public void goToProfilePage();
    }

}
