package com.example.group10b_hw03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

// Created by Rakesh Balan Lingakumar

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        TextView progressText = (TextView) findViewById(R.id.progressText);
        TextView resultText = (TextView) findViewById(R.id.resultText);

        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        int progressValue = getIntent().getExtras().getInt("answerProgress");
        int TotalprogressValue = getIntent().getExtras().getInt("TotalProgress");
        pb.setProgress(progressValue);
        pb.setMax(TotalprogressValue);
        int answerTotal = getIntent().getExtras().getInt("answerTotal");
        progressText.setText(answerTotal + "%");

        if (answerTotal == 100) {
            resultText.setText("Congrats!! You got all Correct Answers");
        } else {
            resultText.setText("Try again and see if you can get\n        all the correct Answers!!!");
        }
        Button tryAgainBtn = (Button) findViewById(R.id.tryAgain);
        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTrivia = new Intent(getBaseContext(), TriviaActivity.class);
                startActivity(intentTrivia);
            }
        });
        Button quitBtn = (Button) findViewById(R.id.quit);
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

}