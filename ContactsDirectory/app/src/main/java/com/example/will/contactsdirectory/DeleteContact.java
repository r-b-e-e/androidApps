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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Homework 2
 * DeleteContact
 * Ganesh Ramamoorthy, Rakesh Balan,William Rosmon
 */
public class DeleteContact extends AppCompatActivity {

    private Button delContact;
    private ArrayList<Contact> contacts;
    private Contact selectedContact;
    private String[] names;
    private EditText deleteName;
    private EditText deleteEmail;
    private EditText deletePhone;
    private ImageView image;
    private Button delete;
    private Button cancel;
    private Uri imgURI;
    Bitmap bitmapImage;
    BitmapFactory BitmapFactory;
    private final static int IMG_REQ_KEY = 100;
    private int delContactIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.app_icon);
        actionBar.setTitle(R.string.deleteLabel);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        contacts = getIntent().getParcelableArrayListExtra(MainActivity.CONTACTS_KEY);
        names = new String[contacts.size()];
        populateArray();
        assignViews();
        final Context thisContext = this;
        /*
        Builds Alert Dialog to chose which contact to edit
         */
        delContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder contactsBuilder = new AlertDialog.Builder(thisContext);
                contactsBuilder.setTitle(R.string.SelectContact);
                contactsBuilder.setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedContact = contacts.get(which);
                        delContactIndex = which;
                        deleteName.setText(contacts.get(which).getName());
                        deletePhone.setText(contacts.get(which).getPhoneNumber());
                        deleteEmail.setText(contacts.get(which).geteMail());
                        if (contacts.get(which).getChkContact().equalsIgnoreCase("false")) {
                            image.setImageDrawable(getResources().getDrawable(R.drawable.avadefault));
                        } else {
                            image.setImageURI(contacts.get(which).getPicture());
                        }
                    }
                });
                contactsBuilder.show();
            }
        });
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
        deleteName = (EditText) findViewById(R.id.nameTextField);
        deleteEmail = (EditText) findViewById(R.id.emailAddressText);
        deletePhone = (EditText) findViewById(R.id.phoneNumberText);
        image = (ImageView) findViewById(R.id.editImage);
        delContact = (Button) findViewById(R.id.edit_button);
        delete = (Button) findViewById(R.id.btnEditDel);
        cancel = (Button) findViewById(R.id.btnEditCancel);
        deleteName.setEnabled(false);
        deleteEmail.setEnabled(false);
        deletePhone.setEnabled(false);
        image.setEnabled(false);
        setListeners();
    }

    private void setListeners() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteName.getText().toString() == null || deleteName.getText().toString().length() <= 0 || deletePhone.getText().toString().length() == 0) {
                    setResult(RESULT_CANCELED);
                    Toast.makeText(getBaseContext(), "Please Select a contact to be deleted", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent();
                    i.putExtra(MainActivity.DEL_CONTACT_STRING, delContactIndex);
                    setResult(RESULT_OK, i);
                    finish();
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }


}
