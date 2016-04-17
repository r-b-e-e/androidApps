package com.example.group10b_hw03;



import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


// Created by Rakesh Balan Lingakumar

public class CreateQuestionActivity extends AppCompatActivity {
    ImageView imageViewAddQ;
    Button buttonSelectImage;
    Button buttonQuestionSubmit;
    EditText editTextQuestion;
    EditText editTextOptions;
    ImageView imageViewAddOption;
    LinearLayout radioSol;
    RadioGroup radioGroupOptions;
    HashMap<String, String> options = new HashMap<String, String>();
    StringBuilder sb = new StringBuilder();
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private ImageView img;
    ProgressDialog progressDialogQuestion;
    Bitmap bitmap;
    String imagePath="",imageExt="";
    String imageURL="";

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        if (isConnected()) {
        intializeFields();

        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_PICK);//ACTION_PICK only Gallery ACTION_GET_CONTENT
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                Intent lPictureIntent = new Intent(Intent.ACTION_PICK);
                lPictureIntent.setType("image/*");
                startActivityForResult(lPictureIntent, 1);
            }
        });

        buttonQuestionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image = ((BitmapDrawable) imageViewAddQ.getDrawable()).getBitmap();
//                new uploadImage(image,"test").execute("http://dev.theappsdr.com/apis/trivia_fall15/uploadPhoto.php");
                if (checkText(editTextQuestion)) {
                    editTextQuestion.setError("Enter a valid Question");
                } else if (options.size() < 2) {
                    Toast.makeText(CreateQuestionActivity.this, "Enter more options", Toast.LENGTH_SHORT).show();
                } else if (radioGroupOptions.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(CreateQuestionActivity.this, "Check an option", Toast.LENGTH_SHORT).show();
                } else if (!(checkText(editTextQuestion)) && radioGroupOptions.getCheckedRadioButtonId() != -1 && options.size() >= 2) {
                    sb.append(editTextQuestion.getText().toString() + ";");
                    for (String key : options.keySet()) {
                        sb.append(options.get(key) + ";");
                    }
                    if (imageURL.toString().equals("")) {
                        sb.append(";" + radioGroupOptions.getCheckedRadioButtonId() + ";");
                        Log.d("demo", sb.toString());
                        new QuestionCreate().execute(sb.toString());
                        Toast.makeText(CreateQuestionActivity.this,"Question Added",Toast.LENGTH_SHORT).show();
                        sb.setLength(0);

                    } else {
                        sb.append(imageURL.toString().trim() + ";" + radioGroupOptions.getCheckedRadioButtonId() + ";");
                        Log.d("demo", sb.toString());
                        new QuestionCreate().execute(sb.toString());
                        Toast.makeText(CreateQuestionActivity.this,"Question Added",Toast.LENGTH_SHORT).show();
                        sb.setLength(0);

                    }
                   finish();

                }
            }
        });
        imageViewAddOption.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (!(editTextOptions.getText().toString().trim().equals(""))) {


                    Log.d("Demo", "radio add");
                    RadioButton rb1 = new RadioButton(CreateQuestionActivity.this);
                    rb1.setText(editTextOptions.getText());
                    rb1.setId(i);
                    options.put(i + "", editTextOptions.getText().toString());
                    radioGroupOptions.addView(rb1);
                    editTextOptions.setText("");
                    i++;

                } else {
                    Log.d("demo", "error");
                }
            }
        });

        } else {
            Toast.makeText(getApplicationContext(), "Internet Connection Failure", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Check for Null Values
     *
     * @param editText
     * @return
     */
    private boolean checkText(EditText editText) {
        intializeFields();
        if (editText.getText().toString().trim().equals("")) {
            return true;
        }
        return false;
    }

    private void intializeFields() {
        imageViewAddQ = (ImageView) findViewById(R.id.imageViewAddQ);
        imageViewAddOption = (ImageView) findViewById(R.id.imageViewAddOption);
        buttonSelectImage = (Button) findViewById(R.id.buttonSelectImage);
        buttonQuestionSubmit = (Button) findViewById(R.id.buttonQuestionSubmit);
        editTextQuestion = (EditText) findViewById(R.id.editTextQuestion);
        editTextOptions = (EditText) findViewById(R.id.editTextOptions);
        radioSol = (LinearLayout) findViewById(R.id.radioSol);
        radioGroupOptions = (RadioGroup) findViewById(R.id.radioGroupOptions);
        progressDialogQuestion = new ProgressDialog(CreateQuestionActivity.this);

    }

    @Override
    protected void onActivityResult(int aRequestCode, int aResultCode, Intent aData) {
        super.onActivityResult(aRequestCode, aResultCode, aData);
        //sets imageView as the image selected
        if (aResultCode == RESULT_OK) {
            switch (aRequestCode) {
                case 1:
                    Uri lSelectedImgUri = aData.getData();


                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), lSelectedImgUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(lSelectedImgUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    cursor.moveToFirst();
                    imagePath = cursor.getString(columnIndex);
//
//
//
                    cursor.close();
                    Log.d("Image Path", imagePath);

                    imageViewAddQ.setImageURI(lSelectedImgUri);
                    new uploadImage().execute(imagePath);

            }
        }
    }




    class uploadImage extends AsyncTask<String, String, String> {
        BufferedReader lReader = null;
        int bytesRead,bytesAvailable,bufferSize;

        int maxBufferSize = 1*1024*1024;
        byte[] buffer;



        @Override
        protected String doInBackground(String... params) {
            String attachmentName = "uploaded_file";
            String attachmentFileName = "User.png";
            String crlf = "\r\n";
            String twoHyphens = "--";
            String boundary =  "*****";

            try {
                URL url = new URL("http://dev.theappsdr.com/apis/trivia_fall15/uploadPhoto.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                FileInputStream fileInputStream = new FileInputStream(new File(params[0]));

                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep Alive");
                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);


                DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());

                dataOutputStream.writeBytes(twoHyphens + boundary + crlf);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\""+attachmentName+"\";filename=\""
                        +attachmentFileName +"\""+crlf);
                dataOutputStream.writeBytes(crlf);



                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer,0,bufferSize);

                while(bytesRead>0){
                    dataOutputStream.write(buffer,0,bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                }


                dataOutputStream.writeBytes(crlf);
                dataOutputStream.writeBytes(twoHyphens + boundary + crlf);

                dataOutputStream.flush();
                dataOutputStream.close();

                lReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder lStringBuilder = new StringBuilder();
                String lLine = "";
                while ((lLine = lReader.readLine()) != null){
                    lStringBuilder.append(lLine + "\n");
                }
                return lStringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            imageURL=s;
            Log.d("demo", s);
        }
    }

    class QuestionCreate extends AsyncTask<String, Void, Void> {
        BufferedReader reader = null;

        @Override
        protected Void doInBackground(String... params) {
            try {
                Log.d("demo","--params--"+params[0]);
                URL url = new URL("http://dev.theappsdr.com/apis/trivia_fall15/saveNew.php");
                String encodedURL = "gid=" + URLEncoder.encode("96105e2bf6316d086a172ace603b94e6", "UTF-8") +
                        "&q="+URLEncoder.encode(params[0],"UTF-8");
                Log.d("DemoURL", encodedURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
                out.write(encodedURL);
                out.flush();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = reader.readLine();
                Log.d("demo", line);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogQuestion.show();
            progressDialogQuestion.setMessage("Uploading...");
            progressDialogQuestion.setCancelable(false);
            progressDialogQuestion.setTitle("Adding Quesion.");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialogQuestion.dismiss();

        }
    }


    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
