package com.example.rb.inclass1b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button_inch;
    Button button_feet;
    Button button_miles;
    Button button_clear;
    String editTextMeterInput;
    double editTextMeterInputMeter;

    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.title_button);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        textViewResult = (TextView) findViewById(R.id.textViewResult);

        Button buttonConvert = (Button) findViewById(R.id.buttonConvert);
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNotNull())
                {
                    RadioButton radioButtonInch = (RadioButton) findViewById(R.id.radioButtonInch);
                    RadioButton radioButtonFeet = (RadioButton) findViewById(R.id.radioButtonFeet);
                    RadioButton radioButtonMiles = (RadioButton) findViewById(R.id.radioButtonMiles);
                    RadioButton radioButtonClear = (RadioButton) findViewById(R.id.radioButtonClear);

                    editTextMeterInput = (String)((EditText) findViewById(R.id.editTextMeterInput)).getText().toString();
                    editTextMeterInputMeter = Double.parseDouble(editTextMeterInput);

                    if(radioButtonInch.isChecked())
                    {
                        double inch = editTextMeterInputMeter * 39.3701;
                        textViewResult.setText("Inches:   " + String.valueOf(inch));
                    }

                    if(radioButtonFeet.isChecked())
                    {
                        double feet = editTextMeterInputMeter * 3.28;
                        textViewResult.setText("Feets:   " + String.valueOf(feet));
                    }

                    if(radioButtonMiles.isChecked())
                    {
                        double miles = editTextMeterInputMeter * 0.0006;
                        textViewResult.setText("Miles:   " + String.valueOf(miles));
                    }

                    if(radioButtonClear.isChecked())
                    {
                        EditText editTextMeter = (EditText) findViewById(R.id.editTextMeterInput);
                        editTextMeter.setText("");
                        textViewResult.setText("Result: ");
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please fill the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean isNotNull()
    {
        // Input is always a number or null
        boolean bool = true;
        editTextMeterInput = (String)((EditText) findViewById(R.id.editTextMeterInput)).getText().toString();
        if(editTextMeterInput.equals(""))
        {
            bool = false;
        }
        return bool;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
