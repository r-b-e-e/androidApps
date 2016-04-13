package android_apps.rakeshbalan.smartsafepubsub;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rakeshbalan on 4/11/2016.
 */

//Shows the Splash Screen for 2 seconds
public class SplashScreen extends AppCompatActivity {
    public final int SPLASH_SCREEN_CONSTANT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, SPLASH_SCREEN_CONSTANT);

    }
}
