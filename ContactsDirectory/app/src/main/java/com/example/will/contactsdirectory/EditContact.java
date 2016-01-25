package com.example.will.contactsdirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Homework 2
 * EditContact
 * Ganesh Ramamoorthy, Rakesh Balan,William Rosmon
 */
public class EditContact extends AppCompatActivity {
    private String selectedImagePath;
    private Button editContact;
    private ArrayList<Contact> contacts;
    private Contact selectedContact;
    private String[] names;
    private EditText editName;
    private EditText editEMail;
    private EditText editPhone;
    private ImageView image;
    private Button save;
    private Button cancel;
    private Uri imgURI;
    Bitmap bitmapImage;
    BitmapFactory BitmapFactory;
    private final static int IMG_REQ_KEY = 100;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+";
    private int editedContactIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.app_icon);
        actionBar.setTitle(R.string.editLabel);
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
        editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder contactsBuilder = new AlertDialog.Builder(thisContext);
                contactsBuilder.setTitle(R.string.SelectContact);
                contactsBuilder.setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editName.setEnabled(true);
                        editEMail.setEnabled(true);
                        editPhone.setEnabled(true);
                        image.setEnabled(true);
                        save.setEnabled(true);
                        selectedContact = contacts.get(which);
                        editedContactIndex = which;
                        editName.setText(contacts.get(which).getName());
                        editPhone.setText(contacts.get(which).getPhoneNumber());
                        editEMail.setText(contacts.get(which).geteMail());
                        if (contacts.get(which).getChkContact().equals("false")) {
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
        editName = (EditText) findViewById(R.id.nameTextField);
        editEMail = (EditText) findViewById(R.id.emailAddressText);
        editPhone = (EditText) findViewById(R.id.phoneNumberText);
        image = (ImageView) findViewById(R.id.editImage);
        editContact = (Button) findViewById(R.id.edit_button);
        save = (Button) findViewById(R.id.btnEditDel);
        cancel = (Button) findViewById(R.id.btnEditCancel);
        editName.setEnabled(false);
        editEMail.setEnabled(false);
        editPhone.setEnabled(false);
        image.setEnabled(false);
        save.setEnabled(false);

        setListeners();
    }

    private void setListeners() {
        image.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                //intent.setAction(Intent.ACTION_GET_CONTENT); Points directly to choose from recent,downloads
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMG_REQ_KEY);
            }
        }));


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {

                    setResult(RESULT_CANCELED);
                } else {
                    String ename=editName.getText().toString();
                   String ephone= editPhone.getText().toString();
                    String eEmail=editEMail.getText().toString();
                    Uri imgNew=contacts.get(editedContactIndex).getPicture();
                    Uri picture;
                    String ChkContact;
                    if (selectedImagePath != null) {
                         picture=Uri.parse(selectedImagePath);
                         ChkContact="true";
                    }
                    else{
                         picture=imgNew;
                         ChkContact="false";
                    }
                    Intent i = new Intent();
                    i.putExtra(MainActivity.EDIT_CONTACT_STRING, editedContactIndex);
                    i.putExtra("editedContact", (Parcelable) new Contact(ename, eEmail, ephone, picture, ChkContact));
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

    public boolean validate() {

        if (editName.getText().toString() == null || editName.getText().toString().length() == 0) {
            Toast.makeText(getBaseContext(), "Please Enter a Name ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (editPhone.getText().toString().length() == 0) {
            Toast.makeText(getBaseContext(), "Please Enter a Phone Number", Toast.LENGTH_LONG).show();
            return false;
        }
        if (editPhone.getText().toString().length() != 10) {
            Toast.makeText(getBaseContext(), "Phone Number must be 10 digits", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!(editEMail.getText().toString().trim().matches(emailPattern))) {
            Toast.makeText(getApplicationContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMG_REQ_KEY) {
            imgURI = data.getData();
            selectedImagePath = getPath(imgURI);
            image.setImageURI(imgURI);

        }
    }
}
