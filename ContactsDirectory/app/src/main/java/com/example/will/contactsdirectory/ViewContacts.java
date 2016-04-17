package com.example.will.contactsdirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Homework 2
 * ViewContacts
 * Ganesh Ramamoorthy, Rakesh Balan, William Rosmon
 */
public class ViewContacts extends AppCompatActivity {

    private ArrayList<Contact> contacts;
    private Contact selectedContact;
    private String[] names;
    private TextView viewName;
    private TextView viewEMail;
    private TextView viewPhone;
    private ImageView image;
    private Button finish;
    private Uri imgURI;
    Bitmap bitmapImage;
    BitmapFactory BitmapFactory;
    private final static int IMG_REQ_KEY = 100;
    private int editedContactIndex;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.app_icon);
        actionBar.setTitle(R.string.viewLabel);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        contacts = getIntent().getParcelableArrayListExtra(MainActivity.CONTACTS_KEY);
        names = new String[contacts.size()];
        populateArray();
        assignViews();
        if (checkContacts()) {
            resultView(0);
        } else {
            viewName.setText("No Contacts found");
            viewPhone.setEnabled(false);
            viewEMail.setEnabled(false);
        }
    }

    public void emailClick(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{contacts.get(i).geteMail()});
        emailIntent.putExtra(Intent.EXTRA_CC, "");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Compose Mail");
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void phoneClick(View v) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contacts.get(i).getPhoneNumber()));
        startActivity(intent);
    }

    /**
     * Populates Array of Names from Contacts Array List
     */
    private void populateArray() {
        for (int i = 0; i < contacts.size(); i++) {
            names[i] = contacts.get(i).getName();
        }
    }

    private void assignViews() {
        viewName = (TextView) findViewById(R.id.nameTextField);
        viewEMail = (TextView) findViewById(R.id.emailAddressText);
        viewPhone = (TextView) findViewById(R.id.phoneNumberText);
        image = (ImageView) findViewById(R.id.editImage);
        finish = (Button) findViewById(R.id.btnFinish);
        setListeners();
    }

    public void resultView(int i) {
        viewName.setText(contacts.get(i).getName());
        viewPhone.setText(contacts.get(i).getPhoneNumber());
        viewEMail.setText(contacts.get(i).geteMail());
        //  image.setImageURI(contacts.get(i).getPicture());
        if (contacts.get(i).getChkContact().equalsIgnoreCase("false")) {
            image.setImageDrawable(getResources().getDrawable(R.drawable.avadefault));
        } else {
            image.setImageURI(contacts.get(i).getPicture());
        }

    }

    public boolean checkContacts() {
        if (contacts.size() > 0) {
            return true;
        }
        return false;
    }

    public void prevClick(View v) {
        if (checkContacts()) {
            if (i >= 1) {
                i = i - 1;
                resultView(i);
            }
        }
    }

    public void firstClick(View v) {
        if (checkContacts()) {
            i = 0;
            resultView(i);
        }
    }

    public void lastClick(View v) {
        if (checkContacts()) {
            i = contacts.size() - 1;
            resultView(i);
        }
    }

    public void nxtClick(View v) {
        if (checkContacts()) {
            Log.d("Demoiiiisisssiiiiiiii", "" + contacts.size());
            if (i < contacts.size() - 1) {
                i = i + 1;
                Log.d("Demoiiiiiiiiiiiiiii", "" + i);
                resultView(i);
            }
        }
    }

    private void setListeners() {


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }

        });
    }

}
