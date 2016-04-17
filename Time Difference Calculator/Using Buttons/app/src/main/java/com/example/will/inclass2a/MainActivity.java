package com.example.will.inclass2a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

// Created by Rakesh Balan Lingakumar

public class MainActivity extends AppCompatActivity {

    public boolean checkNull() {
        String text = ((EditText) findViewById(R.id.hours)).getText().toString();
        String minText = ((EditText) findViewById(R.id.minutes)).getText().toString();
        if (text.equals("")||minText.equals("")) {
            Toast.makeText(getApplicationContext(), "No Time is Entered", Toast.LENGTH_SHORT).show();
            ((TextView) findViewById(R.id.resultLabel)).setText("");
            return false;
        } else if (!(text.matches("^[\\d]+$"))){
            Toast.makeText(getApplicationContext(), "Invalid number / Special characters", Toast.LENGTH_SHORT).show();
            ((TextView) findViewById(R.id.resultLabel)).setText("");
            return false;
        }
        else if (!(minText.matches("^[\\d]+$"))){
            Toast.makeText(getApplicationContext(), "Invalid number / Special characters", Toast.LENGTH_SHORT).show();
            ((TextView) findViewById(R.id.resultLabel)).setText("");
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch sw = (Switch)findViewById(R.id.AMPMSwitch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView tv = (TextView) findViewById(R.id.AMPMLable);
                if(isChecked){
                    tv.setText(getString(R.string.PMLabel));
                } else {
                    tv.setText(getString(R.string.AMLabel));
                }
            }
        });

        Button best = (Button) findViewById(R.id.button_EST);
        best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(checkNull()) {
                        String text = ((EditText) findViewById(R.id.hours)).getText().toString();
                        String minText = ((EditText) findViewById(R.id.minutes)).getText().toString();
                        int hr = Integer.parseInt(text);
                        int mi = Integer.parseInt(minText);
                        if (hr > 12) {
                            Toast.makeText(getApplicationContext(), "Wrong Hour", Toast.LENGTH_SHORT).show();
                        } else if (hr <= 12) {
                            if (mi > 60) {
                                Toast.makeText(getApplicationContext(), "Wrong Minute", Toast.LENGTH_SHORT).show();
                            } else if (mi <= 60) {
                                Calendar localTime = Calendar.getInstance();
                                localTime.set(Calendar.HOUR, hr);
                                localTime.set(Calendar.MINUTE, mi);
                                TextView amPm = (TextView) findViewById(R.id.AMPMLable);
                                if (amPm.getText().toString().equals("AM")) {
                                    localTime.set(Calendar.AM_PM, Calendar.AM);
                                } else if (amPm.getText().toString().equals("PM")) {
                                    localTime.set(Calendar.AM_PM, Calendar.PM);
                                }
                                TextView result = (TextView) findViewById(R.id.resultLabel);
                                int hour = localTime.get(Calendar.HOUR);
                                int minute = localTime.get(Calendar.MINUTE);
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
                                int hoursToSubtract = 5;
                                localTime.add(Calendar.HOUR, -hoursToSubtract);
                             /* EST =  UTC - 5:00
                              CST = UTC - 6:00
                              MST = UTC - 7:00
                              PST = UTC - 8:00*/
                                result.setText("EST:" + ":" + sdf.format(localTime.getTime()) + "");
                            }
                        }
                }
            }
        });

        Button bcst = (Button) findViewById(R.id.button_CST);
        bcst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNull()) {
                    String text = ((EditText) findViewById(R.id.hours)).getText().toString();
                    String minText = ((EditText) findViewById(R.id.minutes)).getText().toString();
                    int hr = Integer.parseInt(text);
                    int mi = Integer.parseInt(minText);
                    if (hr > 12) {
                        Toast.makeText(getApplicationContext(), "Wrong Hour", Toast.LENGTH_SHORT).show();
                    } else if (hr <= 12) {
                        if (mi > 60) {
                            Toast.makeText(getApplicationContext(), "Wrong Minute", Toast.LENGTH_SHORT).show();
                        } else if (mi <= 60) {
                            Calendar localTime = Calendar.getInstance();
                            localTime.set(Calendar.HOUR, hr);
                            localTime.set(Calendar.MINUTE, mi);
                            TextView amPm = (TextView) findViewById(R.id.AMPMLable);
                            if (amPm.getText().toString().equals("AM")) {
                                localTime.set(Calendar.AM_PM, Calendar.AM);
                            } else if (amPm.getText().toString().equals("PM")) {
                                localTime.set(Calendar.AM_PM, Calendar.PM);
                            }
                            TextView result = (TextView) findViewById(R.id.resultLabel);
                            int hour = localTime.get(Calendar.HOUR);
                            int minute = localTime.get(Calendar.MINUTE);
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
                            int hoursToSubtract = 6;
                            localTime.add(Calendar.HOUR, -hoursToSubtract);
                             /* EST =  UTC - 5:00
                              CST = UTC - 6:00
                              MST = UTC - 7:00
                              PST = UTC - 8:00*/
                            result.setText("CST:" + ":" + sdf.format(localTime.getTime()) + "");
                        }
                    }

                }

            }
        });

        Button bmst = (Button) findViewById(R.id.button_MST);
        bmst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNull()) {
                    String text = ((EditText) findViewById(R.id.hours)).getText().toString();
                    String minText = ((EditText) findViewById(R.id.minutes)).getText().toString();
                    int hr = Integer.parseInt(text);
                    int mi = Integer.parseInt(minText);
                    if (hr > 12) {
                        Toast.makeText(getApplicationContext(), "Wrong Hour", Toast.LENGTH_SHORT).show();
                    } else if (hr <= 12) {
                        if (mi > 60) {
                            Toast.makeText(getApplicationContext(), "Wrong Minute", Toast.LENGTH_SHORT).show();
                        } else if (mi <= 60) {
                            Calendar localTime = Calendar.getInstance();
                            localTime.set(Calendar.HOUR, hr);
                            localTime.set(Calendar.MINUTE, mi);
                            TextView amPm = (TextView) findViewById(R.id.AMPMLable);
                            if (amPm.getText().toString().equals("AM")) {
                                localTime.set(Calendar.AM_PM, Calendar.AM);
                            } else if (amPm.getText().toString().equals("PM")) {
                                localTime.set(Calendar.AM_PM, Calendar.PM);
                            }
                            TextView result = (TextView) findViewById(R.id.resultLabel);
                            int hour = localTime.get(Calendar.HOUR);
                            int minute = localTime.get(Calendar.MINUTE);
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
                            int hoursToSubtract = 7;
                            localTime.add(Calendar.HOUR, -hoursToSubtract);
                             /* EST =  UTC - 5:00
                              CST = UTC - 6:00
                              MST = UTC - 7:00
                              PST = UTC - 8:00*/
                            result.setText("MST:" + ":" + sdf.format(localTime.getTime()) + "");
                        }
                    }
                }
            }
        });

        Button bpst = (Button) findViewById(R.id.button_PST);
        bpst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNull()) {
                    String text = ((EditText) findViewById(R.id.hours)).getText().toString();
                    String minText = ((EditText) findViewById(R.id.minutes)).getText().toString();
                    int hr = Integer.parseInt(text);
                    int mi = Integer.parseInt(minText);
                    if (hr > 12) {
                        Toast.makeText(getApplicationContext(), "Wrong Hour", Toast.LENGTH_SHORT).show();
                    } else if (hr <= 12) {
                        if (mi > 60) {
                            Toast.makeText(getApplicationContext(), "Wrong Minute", Toast.LENGTH_SHORT).show();
                        } else if (mi <= 60) {
                            Calendar localTime = Calendar.getInstance();
                            localTime.set(Calendar.HOUR, hr);
                            localTime.set(Calendar.MINUTE, mi);
                            TextView amPm = (TextView) findViewById(R.id.AMPMLable);
                            if (amPm.getText().toString().equals("AM")) {
                                localTime.set(Calendar.AM_PM, Calendar.AM);
                            } else if (amPm.getText().toString().equals("PM")) {
                                localTime.set(Calendar.AM_PM, Calendar.PM);
                            }
                            TextView result = (TextView) findViewById(R.id.resultLabel);
                            int hour = localTime.get(Calendar.HOUR);
                            int minute = localTime.get(Calendar.MINUTE);
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
                            int hoursToSubtract = 8;
                            localTime.add(Calendar.HOUR, -hoursToSubtract);
                             /* EST =  UTC - 5:00
                              CST = UTC - 6:00
                              MST = UTC - 7:00
                              PST = UTC - 8:00*/
                            result.setText("PST:" + ":" + sdf.format(localTime.getTime()) + "");
                        }
                    }
                }
            }
        });

        Button bclear = (Button) findViewById(R.id.button_clear);
        bclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.hours)).setText("");
                ((TextView) findViewById(R.id.minutes)).setText("");
                TextView amPm = (TextView) findViewById(R.id.AMPMLable);
                Switch  toggle = (Switch) findViewById(R.id.AMPMSwitch);
                amPm.setText("AM");
                toggle.setChecked(false);
                ((TextView) findViewById(R.id.resultLabel)).setText("Result:");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
