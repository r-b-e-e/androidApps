package com.example.inclass3;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ganesh Ramamoorthy,Rakesh Balan,William Rosmon
 */
public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog;
    Handler handler;
    ProgressDialog pd;
    double avgVal = 0.0;
    private ArrayList<String> myList;
    AlertDialog.Builder contactsBuilder;
    double resavgVal;
    SeekBar sb;
    TextView textresult;
    TextView progressText;
    int test = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sb = (SeekBar) findViewById(R.id.seekBar);
        progressText = (TextView) findViewById(R.id.ProgressText);
        textresult = (TextView) findViewById(R.id.textResult);
        final int step = 1;
        final int max = 10;
        progressText.setText("Selected Passwords Count:     " + test);
        final int min = 1;
        myList = new ArrayList<String>();
        sb.setMax((max - min) / step);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = min + (progress * step);
                test = value;
                progressText.setText("Selected Passwords Count:     " + value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        Button btn = (Button) findViewById(R.id.buttonAsync);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkTest()) {
                    new doWork().execute(test);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter a Value in Seekbar", Toast.LENGTH_SHORT).show();
                }
            }
        });


        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Generating Passwords...");
        pd.setCancelable(false);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case DoneWrk.START:
                        pd.show();
                        break;
                    case DoneWrk.PROGRESS:
                        break;
                    case DoneWrk.DONE:
                        pd.dismiss();
                        final CharSequence[] cs = myList.toArray(new CharSequence[myList.size()]);
                        contactsBuilder = new AlertDialog.Builder(MainActivity.this);
                        contactsBuilder.setTitle(R.string.passwords);

                        contactsBuilder.setItems(cs, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                CharSequence result = cs[which];
                                textresult.setText("Password: " + result.toString());
                            }
                        });
                        contactsBuilder.show();
                        myList.clear();
                        break;
                }
                return false;
            }
        });

        Button btnThread = (Button) findViewById(R.id.buttonThread);
        btnThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkTest()) {

                    ExecutorService tpool;
                    if (test == 1) {
                        tpool = Executors.newFixedThreadPool(test);
                    } else {
                        tpool = Executors.newFixedThreadPool(test / 2);
                    }
                    tpool.execute((Runnable) new DoneWrk(test));
                } else {
                    Toast.makeText(getApplicationContext(), "Enter a Value in Seekbar", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private boolean chkTest() {
        if (test == 0) {
            return false;
        }
        return true;
    }


    class doWork extends AsyncTask<Integer, Integer, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            for (int i = 0; i < params[0]; i++) {

                String pwd = Util.getPassword();
                myList.add(pwd);
                resavgVal = avgVal;
                publishProgress(i + 1);
            }
            return myList;
        }


        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            dialog.dismiss();
            final CharSequence[] cs = strings.toArray(new CharSequence[strings.size()]);
            contactsBuilder = new AlertDialog.Builder(MainActivity.this);
            contactsBuilder.setTitle(R.string.passwords);

            contactsBuilder.setItems(cs, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    CharSequence result = cs[which];
                    textresult.setText("Password: " + result.toString());
                }
            });
            contactsBuilder.show();
            myList.clear();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Generating Passwords...");
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

    }



    class DoneWrk implements Runnable {
        static final int START = 0;
        static final int PROGRESS = 1;
        static final int DONE = 2;
        int testDone;

        public DoneWrk(int testDone) {
            this.testDone = testDone;
        }

        @Override
        public void run() {

            Message msg = new Message();
            msg.what = START;
            handler.sendMessage(msg);
            for (int i = 0; i < testDone; i++) {

                String pwd = Util.getPassword();
                myList.add(pwd);
                msg = new Message();
                msg.what = PROGRESS;
                handler.sendMessage(msg);

            }
            msg = new Message();
            msg.what = DONE;
            handler.sendMessage(msg);
        }
    }


}


