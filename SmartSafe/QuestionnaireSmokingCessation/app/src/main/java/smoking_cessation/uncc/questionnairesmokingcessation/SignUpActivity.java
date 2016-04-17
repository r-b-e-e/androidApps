package smoking_cessation.uncc.questionnairesmokingcessation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

/**
 * Created by rakeshbalan on 4/13/2016.
 */


//Demographic questions

public class SignUpActivity extends AppCompatActivity {
    String AppendixA_Q1;
    String AppendixA_Q2;
    String AppendixA_Q3;
    String AppendixA_Q4;
    String AppendixA_Q5;
    String AppendixA_Q6;
    String AppendixA_Q7;
    String AppendixA_Q8;
    String AppendixA_Q9;
    String AppendixA_Q10;
    String AppendixA_Q11;
    String AppendixA_Q12;
    String AppendixA_Q13;
    String AppendixA_Q14;


    Spinner spinner_Q3;
    EditText editTextQ3;
    Spinner spinner_Q7;
    EditText editTextQ7;
    Spinner spinner_Q9;
    EditText editTextQ9;
    Spinner spinner_Q12;
    EditText editTextQ12;
    Spinner spinner_Q13;
    EditText editTextQ13;

    Button buttonNext;
    LinearLayout linearLayoutNext;
    NumberPicker numberPickerSocialStatusMeasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        linearLayoutNext = (LinearLayout) findViewById(R.id.linearLayoutNext);


        spinner_Q3 = ((Spinner) findViewById(R.id.spinnerAppendixA_Q3));
        editTextQ3 = ((EditText) findViewById(R.id.editTextOtherCountry));
        spinner_Q3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2) {
                    editTextQ3.setVisibility(View.VISIBLE);
                } else {
                    editTextQ3.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_Q7 = ((Spinner) findViewById(R.id.spinnerAppendixA_Q7));
        editTextQ7 = ((EditText) findViewById(R.id.editTextOtherRace));
        spinner_Q7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 6) {
                    editTextQ7.setVisibility(View.VISIBLE);
                } else {
                    editTextQ7.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_Q9 = ((Spinner) findViewById(R.id.spinnerAppendixA_Q9));
        editTextQ9 = ((EditText) findViewById(R.id.editTextOtherReligion));
        spinner_Q9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 10) {
                    editTextQ9.setVisibility(View.VISIBLE);
                } else {
                    editTextQ9.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_Q12 = ((Spinner) findViewById(R.id.spinnerAppendixA_Q12));
        editTextQ12 = ((EditText) findViewById(R.id.editTextCurrentOccupation));
        spinner_Q12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    editTextQ12.setVisibility(View.VISIBLE);
                } else {
                    editTextQ12.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_Q13 = ((Spinner) findViewById(R.id.spinnerAppendixA_Q13));
        editTextQ13 = ((EditText) findViewById(R.id.editTextNumberChildren));
        spinner_Q13.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    editTextQ13.setVisibility(View.VISIBLE);
                } else {
                    editTextQ13.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppendixA_Q1 = ((EditText) findViewById(R.id.editTextAppendixA_Q1)).getText().toString();
                Log.d("debug", AppendixA_Q1);


                AppendixA_Q2 = ((Spinner) findViewById(R.id.spinnerAppendixA_Q2)).getSelectedItem().toString();
                Log.d("debug", AppendixA_Q2);


                if (spinner_Q3.getSelectedItemPosition() == 2) {
                    AppendixA_Q3 = (editTextQ3.getText().toString());
                } else {
                    AppendixA_Q3 = spinner_Q3.getSelectedItem().toString();
                }
                Log.d("debug", AppendixA_Q3);


                AppendixA_Q4 = ((EditText) findViewById(R.id.editTextFeet)).getText().toString() + "' " + ((EditText) findViewById(R.id.editTextInches)).getText().toString() + "''";
                Log.d("debug", AppendixA_Q4);


                AppendixA_Q5 = ((EditText) findViewById(R.id.editTextAppendixA_Q5)).getText().toString();
                Log.d("debug", AppendixA_Q5);


                AppendixA_Q6 = ((Spinner) findViewById(R.id.spinnerAppendixA_Q6)).getSelectedItem().toString();
                Log.d("debug", AppendixA_Q6);


                if (spinner_Q7.getSelectedItemPosition() == 6) {
                    AppendixA_Q7 = (editTextQ7.getText().toString());
                } else {
                    AppendixA_Q7 = spinner_Q7.getSelectedItem().toString();
                }
                Log.d("debug", AppendixA_Q7);


                AppendixA_Q8 = ((Spinner) findViewById(R.id.spinnerAppendixA_Q8)).getSelectedItem().toString();
                Log.d("debug", AppendixA_Q8);


                if (spinner_Q9.getSelectedItemPosition() == 10) {
                    AppendixA_Q9 = (editTextQ9.getText().toString());
                } else {
                    AppendixA_Q9 = spinner_Q9.getSelectedItem().toString();
                }
                Log.d("debug", AppendixA_Q9);


                AppendixA_Q10 = ((Spinner) findViewById(R.id.spinnerAppendixA_Q10)).getSelectedItem().toString();
                Log.d("debug", AppendixA_Q10);


                AppendixA_Q11 = ((Spinner) findViewById(R.id.spinnerAppendixA_Q11)).getSelectedItem().toString();
                Log.d("debug", AppendixA_Q11);


                if (spinner_Q12.getSelectedItemPosition() == 1) {
                    AppendixA_Q12 = spinner_Q12.getSelectedItem().toString() + ": " + (editTextQ12.getText().toString());
                } else {
                    AppendixA_Q12 = spinner_Q12.getSelectedItem().toString();
                }
                Log.d("debug", AppendixA_Q12);


                if (spinner_Q13.getSelectedItemPosition() == 1) {
                    AppendixA_Q13 = spinner_Q13.getSelectedItem().toString() + ": " + (editTextQ13.getText().toString());
                } else {
                    AppendixA_Q13 = spinner_Q13.getSelectedItem().toString();
                }
                Log.d("debug", AppendixA_Q13);



                buttonNext.setVisibility(View.INVISIBLE);
                linearLayoutNext.setVisibility(View.VISIBLE);
            }
        });


        numberPickerSocialStatusMeasure = (NumberPicker) findViewById(R.id.numberPickerSocialStatusMeasure);
        numberPickerSocialStatusMeasure.setMaxValue(10);
        numberPickerSocialStatusMeasure.setMinValue(1);
        numberPickerSocialStatusMeasure.setWrapSelectorWheel(true);
        numberPickerSocialStatusMeasure.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                AppendixA_Q14 = String.valueOf(newVal);
            }
        });

        findViewById(R.id.submitAppendixAButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("debug", AppendixA_Q14);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
