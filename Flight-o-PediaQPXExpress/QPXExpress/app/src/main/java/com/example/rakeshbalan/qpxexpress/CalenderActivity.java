package com.example.rakeshbalan.qpxexpress;

import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rakeshbalan on 12/26/2015.
 */
public class CalenderActivity extends AppCompatActivity{
    CalendarView calendarView;
    TextView textViewSelectedDate;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar;
    long dateMin;
    Button buttonDateConfirm;
    String dateInString = "";
    boolean isDatePicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select Travel Date");
        getSupportActionBar().setIcon(R.mipmap.app_icon);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setShowWeekNumber(false);
        calendarView.setFirstDayOfWeek(1);

        calendar = Calendar.getInstance();
        dateMin = calendar.getTime().getTime(); //long datatype value
        calendarView.setMinDate(dateMin);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                textViewSelectedDate = (TextView) findViewById(R.id.textViewSelectedDate);
                textViewSelectedDate.setText("Selected Date: " + year + "-" + (month + 1) + "-" + dayOfMonth);
                dateInString = year + "-" + (month + 1) + "-" + dayOfMonth;
                isDatePicked = true;
            }
        });

        buttonDateConfirm = (Button) findViewById(R.id.buttonDateConfirm);
        buttonDateConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDatePicked)
                {
                    Intent intent=new Intent();
                    intent.putExtra("TRAVEL_DATE", dateInString);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please select the travel date", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
