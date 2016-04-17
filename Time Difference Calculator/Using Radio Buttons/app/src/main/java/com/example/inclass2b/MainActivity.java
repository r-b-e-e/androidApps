package com.example.inclass2b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Created by Rakesh Balan Lingakumar

public class MainActivity extends AppCompatActivity {
    RadioGroup rg;

    public boolean checkNull() {
        String text = ((EditText) findViewById(R.id.Hours)).getText().toString();
        String minText = ((EditText) findViewById(R.id.Minutes)).getText().toString();
        if (text.equals("")||minText.equals("")) {
            Toast.makeText(getApplicationContext(), "Hour/Minute is Blank", Toast.LENGTH_SHORT).show();
            ((TextView) findViewById(R.id.resultInfo)).setText("");
            return false;
        } else if (!(text.matches("^[\\d]+$"))){
            Toast.makeText(getApplicationContext(), "Invalid number / Special characters", Toast.LENGTH_SHORT).show();
            ((TextView) findViewById(R.id.resultInfo)).setText("");
            return false;
        }
        else if (!(minText.matches("^[\\d]+$"))){
            Toast.makeText(getApplicationContext(), "Invalid number / Special characters", Toast.LENGTH_SHORT).show();
            ((TextView) findViewById(R.id.resultInfo)).setText("");
            return false;
        }

        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        final  TextView amPm= (TextView)findViewById(R.id.AMPM);
       final  Switch  toggle = (Switch) findViewById(R.id.switch1);
         rg= (RadioGroup)findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonCLR) {
                    ((TextView) findViewById(R.id.Hours)).setText("");
                    ((TextView) findViewById(R.id.Minutes)).setText("");
                    amPm.setText("AM");
                    toggle.setChecked(false);
                    rg.check(R.id.radioButtonEst);
                    ((TextView) findViewById(R.id.resultInfo)).setText("Result:");

                }
            }
        });




      findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

          TextView result = (TextView) findViewById(R.id.resultInfo);

          @Override
          public void onClick(View v) {
              if (checkNull()) {

                  String text = ((EditText) findViewById(R.id.Hours)).getText().toString();
                  String minText = ((EditText) findViewById(R.id.Minutes)).getText().toString();
                  int hr = Integer.parseInt(text);
                  int mi = Integer.parseInt(minText);
                  if (hr > 12) {
                      Toast.makeText(getApplicationContext(), "Wrong Hour", Toast.LENGTH_SHORT).show();
                  } else if (hr <= 12) {

                      if (mi > 60) {
                          Toast.makeText(getApplicationContext(), "Wrong Minutes", Toast.LENGTH_SHORT).show();
                      } else if (mi <= 60) {
                          Calendar localTime = Calendar.getInstance();
                          localTime.set(Calendar.HOUR, hr);
                          localTime.set(Calendar.HOUR, localTime.get(Calendar.HOUR));
                          localTime.set(Calendar.MINUTE, mi);
                          if (amPm.getText().toString().equals("AM")) {
                              localTime.set(Calendar.AM_PM, Calendar.AM);
                          } else if (amPm.getText().toString().equals("PM")) {
                              localTime.set(Calendar.AM_PM, Calendar.PM);
                          }
                         // int hour = localTime.get(Calendar.HOUR);
                         // int minute = localTime.get(Calendar.MINUTE);

                          SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");

                          if (rg.getCheckedRadioButtonId() == R.id.radioButtonEst) {

                              int hoursToSubtract =5;
                              localTime.add(Calendar.HOUR, -hoursToSubtract);

                              result.setText("EST:" + ":" + sdf.format(localTime.getTime()) + "");
                          }
                          if (rg.getCheckedRadioButtonId() == R.id.radioButtonCst) {
                              int hoursToSubtract = 6;
                              localTime.add(Calendar.HOUR, -hoursToSubtract);
                              result.setText("CST:" + ":" + sdf.format(localTime.getTime()) + "");
                          }
                          if (rg.getCheckedRadioButtonId() == R.id.radioButtonmst) {
                              int hoursToSubtract = 7;
                              localTime.add(Calendar.HOUR, -hoursToSubtract);
                              result.setText("MST:" + ":" + sdf.format(localTime.getTime()) + "");
                          }
                          if (rg.getCheckedRadioButtonId() == R.id.radioButtonpst) {
                              int hoursToSubtract = 8;
                              localTime.add(Calendar.HOUR, -hoursToSubtract);
                              result.setText("PST:" + ":" + sdf.format(localTime.getTime()) + "");
                          }
                          if (rg.getCheckedRadioButtonId() == R.id.radioButtonCLR) {
                          }
                      }
                  }
              }
          }
      });


        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    amPm.setText("PM");
                } else {
                    amPm.setText("AM");
                }

            }
        });
    }

}