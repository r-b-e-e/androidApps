package com.example.will.contactsdirectory;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Homework 2
 * MainActivity
 * Ganesh Ramamoorthy, Rakesh Balan, William Rosmon
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Contact> contactArrayList = new ArrayList<Contact>();
    private Button createButton;
    private Button editButton;
    private Button displayButton;
    private Button deleteButton;
    private Button finishButton;
    final static String CONTACTS_KEY = "Contacts";
    private final static int CREATE_REQ_CODE = 100;
    private final static int DELETE_REQ_CODE = 101;
    private final static int EDIT_REQ_CODE = 102;
    final static String DEL_CONTACT_STRING = "DelContact";
    final static String EDIT_CONTACT_STRING = "edtContact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.app_icon);
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Assigns views and sets Listeners
        assignVariables();

    }

    /**
     * Assigns the proper views to variables
     */
    private void assignVariables() {
        createButton = (Button) findViewById(R.id.btnCreateContact);
        editButton = (Button) findViewById(R.id.btnEditContact);
        displayButton = (Button) findViewById(R.id.btnDisplay);
        deleteButton = (Button) findViewById(R.id.btnDelete);
        finishButton = (Button) findViewById(R.id.btnFinish);
        setListeners();
    }

    /**
     * Sets listeners for buttons in Main Activity
     */
    private void setListeners() {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CreateContact.class);
                i.putParcelableArrayListExtra(CONTACTS_KEY, contactArrayList);
                startActivityForResult(i, CREATE_REQ_CODE);
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), EditContact.class);
                i.putParcelableArrayListExtra(CONTACTS_KEY, contactArrayList);
                startActivityForResult(i, EDIT_REQ_CODE);
            }
        });
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ViewContacts.class);
                i.putParcelableArrayListExtra(CONTACTS_KEY, contactArrayList);
                startActivity(i);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), DeleteContact.class);
                i.putParcelableArrayListExtra(CONTACTS_KEY, contactArrayList);
                startActivityForResult(i, DELETE_REQ_CODE);
            }
        });
    }

    /**
     * Performs required computation based on results received by
     * other activities
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Demo", "Clicked is " + requestCode + "" + resultCode);

        if (requestCode == CREATE_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                contactArrayList.add((Contact) data.getSerializableExtra(CreateContact.NEW_CONTACT_STRING));
                 Toast.makeText(this, R.string.CreatedContact, Toast.LENGTH_SHORT).show();
                sortArrayList();
            } else {
                 Toast.makeText(getApplicationContext(), R.string.OperationCancelled, Toast.LENGTH_SHORT).show();;
            }
        } else if (requestCode == EDIT_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                 Toast.makeText(getApplicationContext(), R.string.EditedContact, Toast.LENGTH_SHORT).show();;
                Contact editedContact;
                 editedContact =(Contact) data.getParcelableExtra("editedContact");
                int editTest = (Integer) data.getExtras().get(EDIT_CONTACT_STRING);
                contactArrayList.get(editTest).setName(editedContact.getName());
                contactArrayList.get(editTest).setPhoneNumber(editedContact.getPhoneNumber());
                contactArrayList.get(editTest).seteMail(editedContact.geteMail());
                contactArrayList.get(editTest).setPicture(editedContact.getPicture());
                contactArrayList.get(editTest).setChkContact(editedContact.getChkContact());
Log.d("DemoEditMain",""+contactArrayList.get(editTest).toString());
                sortArrayList();
            } else {
                 Toast.makeText(getApplicationContext(), R.string.OperationCancelled, Toast.LENGTH_SHORT).show();;
            }
        } else if (requestCode == DELETE_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                int test = (Integer) data.getExtras().get(DEL_CONTACT_STRING);
                contactArrayList.remove(test);
                 Toast.makeText(getApplicationContext(), R.string.DeletedContact, Toast.LENGTH_SHORT).show();;
                //  contactArrayList = data.getParcelableArrayListExtra(CONTACTS_KEY);
                sortArrayList();
            } else {
                 Toast.makeText(getApplicationContext(), R.string.OperationCancelled, Toast.LENGTH_SHORT).show();;
            }
        }


    }

    /**
     * Sorts contact ArrayList by name
     */
    private void sortArrayList() {
        Collections.sort(contactArrayList, new Comparator<Contact>() {
            @Override
            public int compare(Contact lhs, Contact rhs) {
                return lhs.compareTo(rhs);
            }
        });
    }
}
