package com.example.rakeshbalan.qpxexpress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by rakeshbalan on 12/25/2015.
 */
public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.app_icon);

        Button buttonSearchFlights = (Button) findViewById(R.id.buttonSearchFlights);
        buttonSearchFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent indent = new Intent(StartActivity.this, SearchActivity.class);
                startActivity(indent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
