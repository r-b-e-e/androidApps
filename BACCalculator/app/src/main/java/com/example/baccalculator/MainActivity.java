package com.example.baccalculator;
/*
William Rosmon
Rakesh Lingakumar
Ganesh Ramamoorthy
 */

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    String gender = "F";
    boolean savePressed = false;
    double genderd = 0.01;
    double Weight = 0.0;
    DecimalFormat decFormat = new DecimalFormat("#.##");
    DecimalFormat numFormat = new DecimalFormat("##");
    double BAC = 0.0;
    double BACSave = 0.0;
    double progressResult = 0.00;
    double totalAlcohol = 0;

    /**
     * setError method is used to validate the entered Weight text value
     * @return
     */
    public boolean setError() {
        EditText weightText = ((EditText) findViewById(R.id.EnterWeight));
        String entWeight = weightText.getText().toString();
        if (entWeight.equals("")) {
            weightText.setError("Enter the weight in lbs.");
            return false;
        } else if (entWeight.equals("0")) {
            weightText.setError("Enter a proper Weight.");
            return false;
        }

        return true;
    }

    /**
     * onCreate is the method that will create the instance initially
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.cmovine);
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayUseLogoEnabled(true);
        textDefaultGreen();
        actionBar.setDisplayShowHomeEnabled(true);

        final Switch toggle = (Switch) findViewById(R.id.genderSwitch);
        final TextView progressText = (TextView) findViewById(R.id.textViewProgress);

        toggle.setChecked(false);

        progressText.setText(5 + "%");
        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        ProgressBar p = (ProgressBar) findViewById(R.id.progressBar);
        p.setProgress(0);
        p.setMax(25);
        final int step = 5;
        final int max = 25;
        final int min = 5;
        sb.setMax((max - min) / step);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = min + (progress * step);
                progressText.setText(value + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gender = "M";
                } else {
                    gender = "F";
                }
            }
        });
    }

    /**
     * textDefaultGreen will make the default text you're safe available on the screen on create and reset
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void textDefaultGreen() {
        TextView textresult = (TextView) findViewById(R.id.colorText);
        defaultTextResult(textresult, Color.parseColor("#347235"), getString(R.string.SafeLabel));
        textresult.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_green));
    }

    /**
     * onResetClicked is the method that enables the disabled button and resets the app values
     * @param view
     */
    public void onResetClicked(View view) {
        RadioGroup rg = (RadioGroup) findViewById(R.id.BacRg);
        rg.check(R.id.radioButton1);
        Switch toggle = (Switch) findViewById(R.id.genderSwitch);
        toggle.setChecked(false);
        ProgressBar p = (ProgressBar) findViewById(R.id.progressBar);
        p.setProgress(0);
        TextView progressText = (TextView) findViewById(R.id.textViewProgress);
        progressText.setText(5 + "%");
        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        sb.setProgress(0);
        EditText weightText = ((EditText) findViewById(R.id.EnterWeight));
        weightText.setText("");
        weightText.setError(null);
        TextView bacText = (TextView) findViewById(R.id.bacText);
        bacText.setText(R.string.bacLev);
        Button addBtn = (Button) findViewById(R.id.addButton);
        Button saveBtn = (Button) findViewById(R.id.buttonSave);
        textDefaultGreen();
        saveBtn.setEnabled(true);
        savePressed = false;
        progressResult = 0.0;
        addBtn.setEnabled(true);
        BAC = 0.0;
        totalAlcohol = 0.0;
        BACSave = 0.0;
    }

    /**
     * onAddClicked is the method that's called when save or Add drink is clicked
     * @param view
     */
    public void onAddClicked(View view) {
        if (setError()) {
            RadioGroup rg = (RadioGroup) findViewById(R.id.BacRg);
            String textProgress = ((TextView) findViewById(R.id.textViewProgress)).getText().toString();
            Button addBtn = (Button) findViewById(R.id.addButton);
            Button saveBtn = (Button) findViewById(R.id.buttonSave);

            ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar);
            mProgress.setMax(25);
            TextView textresult = (TextView) findViewById(R.id.colorText);
            double answer = 0.00;

            TextView bacText = (TextView) findViewById(R.id.bacText);
            String alcoholPercent = textProgress.substring(0, textProgress.length() - 1);
            Double aPercent = Double.parseDouble(alcoholPercent);
            if (view.getId() == R.id.buttonSave) {
                savePressed = true;
                String weigh = ((EditText) findViewById(R.id.EnterWeight)).getText().toString();
                Weight = Double.parseDouble(weigh);
                if (gender.equals("M")) {
                    genderd = 0.73;
                } else if (gender.equals("F")) {
                    genderd = 0.66;
                }
                calcBAC();
            } else if (view.getId() == R.id.addButton) {
                if (savePressed) {

                    int ounces;
                    double den = 0.0;
                    if (rg.getCheckedRadioButtonId() == R.id.radioButton1) {
                        ounces = 1;
                        double A = (aPercent / 100) * ounces;
                        den = Weight * genderd;
                        totalAlcohol += A;
                    } else if (rg.getCheckedRadioButtonId() == R.id.radioButton5) {
                        ounces = 5;
                        double A = (aPercent / 100) * ounces;
                        den = Weight * genderd;
                        totalAlcohol += A;

                    } else if (rg.getCheckedRadioButtonId() == R.id.radioButton12) {
                        ounces = 12;
                        double A = (aPercent / 100) * ounces;
                        den = Weight * genderd;

                        totalAlcohol += A;
                    }
                    BAC = (totalAlcohol * 5.14) / den;
                    if (decFormat.format(BAC).equals("0")) {
                        bacText.setText(R.string.bacLev);
                        mProgress.setProgress(0);
                        displayTextColor(addBtn, saveBtn, textresult, BAC);
                    } else {
                        double progressValue = Double.parseDouble(decFormat.format(BAC));
                        bacText.setText("BAC Level: " + decFormat.format(BAC));
                        Double dec = progressValue * 100;
                        int out = dec.intValue();
                        mProgress.setProgress(out);
                        displayTextColor(addBtn, saveBtn, textresult, BAC);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Weight and Gender should be entered and registered using the SAVE button before drinks can be added !!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * calcBAC() method calculates the BAC from the aggregate alcohol level in save button method call
     */
    public void calcBAC() {
        if (gender.equals("M")) {
            genderd = 0.73;
        } else if ((gender.equals("F"))) {
            genderd = 0.66;
        }
        ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar);
        Button addBtn = (Button) findViewById(R.id.addButton);
        Button saveBtn = (Button) findViewById(R.id.buttonSave);
        String weigh = ((EditText) findViewById(R.id.EnterWeight)).getText().toString();
        Double Weight = Double.parseDouble(weigh);
        TextView textResult = (TextView) findViewById(R.id.colorText);
        Double denominator = Weight * genderd;
        BACSave = (totalAlcohol * 5.14) / denominator;
        TextView bacTxt = (TextView) findViewById(R.id.bacText);
        if (decFormat.format(BACSave).equals("0")) {
            bacTxt.setText(R.string.bacLev);
            mProgress.setProgress(0);
            displayTextColor(addBtn, saveBtn, textResult, BACSave);
        } else {
            bacTxt.setText("BAC Level: " + decFormat.format(BACSave));
            Double progressValueSave = Double.parseDouble(decFormat.format(BACSave));
            Double dec = progressValueSave * 100;
            int out = dec.intValue();
            mProgress.setProgress(out);
            displayTextColor(addBtn, saveBtn, textResult, BACSave);
        }
    }

    /**
     * displayTextColor method is used to display the Status Text Based on the BAC Result
     * @param addBtn
     * @param saveBtn
     * @param textresult
     * @param answer
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void displayTextColor(Button addBtn, Button saveBtn, TextView textresult, Double answer) {
        if (answer <= 0.08) {
            defaultTextResult(textresult, Color.parseColor("#347235"), getString(R.string.SafeLabel));
            textresult.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_green));
        } else if (answer > 0.08 && answer < 0.20) {
            defaultTextResult(textresult, Color.parseColor("#EE9A4D"), getString(R.string.CarefulLabel));
            textresult.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_orange));
        } else if (answer >= 0.20) {
            defaultTextResult(textresult, Color.parseColor("#990012"), getString(R.string.OverLimitLabel));
            textresult.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_red));
        }
        if (answer > 0.25) {
            saveBtn.setEnabled(false);
            addBtn.setEnabled(false);
            Toast.makeText(getApplicationContext(), "No more drinks for you.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * defaultTextResult is used to set the default text color based on the BAC
     * @param textresult
     * @param color
     * @param string
     */
    private void defaultTextResult(TextView textresult, int color, String string) {
        textresult.setBackgroundColor(color);
        textresult.setTextColor(Color.WHITE);
        textresult.setText(string);

    }


}