package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;


/**
 * Created by Rakesh Balan on 11/2/2015.
 */

public class SignUpActivity extends AppCompatActivity {
    String email ="";
    String cPWd ="";
    String pwdVal ="";
    String firstVal ="";
    String lastVal = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("MessageMe (SignUp)");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        Button btn = (Button) findViewById(R.id.cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button btnSign = (Button) findViewById(R.id.sign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText first = (EditText) findViewById(R.id.fNameID);
                EditText lastname = (EditText) findViewById(R.id.LNameID);
                firstVal = first.getText().toString();
                lastVal = lastname.getText().toString();
                EditText pwd = (EditText) findViewById(R.id.PasswordID);
                pwdVal = pwd.getText().toString();
                EditText Cpwd = (EditText) findViewById(R.id.CPasswordID);
                cPWd = Cpwd.getText().toString();
                EditText emails = (EditText) findViewById(R.id.EmailID);
                email = emails.getText().toString();
                if (firstVal.equals("")  || email.equals("") || pwdVal.equals("") || cPWd.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Value is empty", Toast.LENGTH_SHORT).show();

                } else if (!pwdVal.equals(cPWd)) {
                    Toast.makeText(SignUpActivity.this, "Pwds do not match", Toast.LENGTH_SHORT).show();
                }
                else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                    query.whereEqualTo("email", email);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> scoreList, ParseException e) {
                            if (e == null) {
                                Log.d("score", "Retrieved " + scoreList.size() + " scores");

                                if (scoreList.size() > 0) {
                                    Toast.makeText(SignUpActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();

                                } else {
                                    ParseUser user = new ParseUser();
                                    user.setUsername(email);
                                    user.setEmail(email);
                                    user.setPassword(pwdVal);
                                    user.put("UserValue", firstVal + " " + lastVal);
                                    User javauser = new User(firstVal + " "+ lastVal,email,pwdVal);

                                    user.signUpInBackground(new SignUpCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                Toast.makeText(SignUpActivity.this, "Logged in Success", Toast.LENGTH_SHORT).show();
                                                Log.d("as", "Successful");
                                                Intent i = new Intent(SignUpActivity.this, MessagesActivity.class);
                                                startActivity(i);
                                                finish();
                                            } else {
                                                Log.d("as", "Failed");
                                            }
                                        }
                                    });
                                }
                            } else {
                                Log.d("score", "Error: " + e.getMessage());
                            }
                        }
                    });
                }

            }
        });
    }


}
