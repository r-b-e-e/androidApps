package com.example.will.contactsdirectory;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
/**
 * Homework 2
 * CreateContact
 * Ganesh Ramamoorthy, Rakesh Balan,William Rosmon
 */
public class CreateContact extends AppCompatActivity {
    private String selectedImagePath;
    private Button saveButton;
    private Button cancelButton;
    private EditText nameText;
    private EditText emailText;
    private EditText phoneText;
    private ImageView image;
    private final static int IMG_REQ_KEY = 100;
    private Uri imgURI;
    final static String NEW_CONTACT_STRING = "New Contact";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.app_icon);
        actionBar.setTitle(R.string.createLabel);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        saveButton = (Button) findViewById(R.id.btnSave);
        cancelButton = (Button) findViewById(R.id.btnCancel);
        nameText = (EditText) findViewById(R.id.txtName);
        emailText = (EditText) findViewById(R.id.txtEmail);
        phoneText = (EditText) findViewById(R.id.txtPhoneNumber);
        image = (ImageView) findViewById(R.id.imgAvatar);
        imgURI = Uri.parse(image.getDrawable().toString());
        setListeners();
    }


    private void setListeners() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    setResult(RESULT_CANCELED);
                } else {
                    Intent i = new Intent();
                    Contact contact;
                    if (selectedImagePath != null) {
                        contact = new Contact(nameText.getText().toString(), emailText.getText().toString(),
                                phoneText.getText().toString(), Uri.parse(selectedImagePath), "true");
                    } else {
                        contact = new Contact(nameText.getText().toString(), emailText.getText().toString(),
                                phoneText.getText().toString(), imgURI, "false");
                    }
                    i.putExtra(NEW_CONTACT_STRING, (Serializable) contact);
                    setResult(RESULT_OK, i);
                    finish();
                }

            }
        });

        cancelButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        }));

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMG_REQ_KEY) {
            imgURI = data.getData();
            selectedImagePath = getPath(imgURI);
            image.setImageURI(imgURI);

        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public boolean validate() {

        if (nameText.getText().toString() == null || nameText.getText().toString().length() == 0) {
            Toast.makeText(getBaseContext(), "Please Enter a Name ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (phoneText.getText().toString().length() == 0) {
            Toast.makeText(getBaseContext(), "Please Enter a Phone Number", Toast.LENGTH_LONG).show();
            return false;
        }
        if (phoneText.getText().toString().length() != 10) {
            Toast.makeText(getBaseContext(), "Phone Number must be 10 digits", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!(emailText.getText().toString().trim().matches(emailPattern))) {
            Toast.makeText(getApplicationContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
