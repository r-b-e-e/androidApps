package com.example.rakeshbalan.connectfour;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setIcon(R.drawable.app_icon);

        ImageView imageButton = (ImageView) findViewById(R.id.imageView);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), BoardActivity.class);
                startActivity(i);
            }
        });

        Button buttonNew = (Button) findViewById(R.id.buttonNewGame);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), BoardActivity.class);
                startActivity(i);
            }
        });

        Button buttonExit = (Button) findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                android.os.Process
                        .killProcess(android.os.Process
                                .myPid());
                System.exit(1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        android.os.Process
                .killProcess(android.os.Process
                        .myPid());
        System.exit(1);
    }
}
