package android_apps.rakeshbalan.githubrailsopenissues;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rakeshbalan on 4/7/2016.
 */
public class SplashScreen extends AppCompatActivity{
    private final int SPLASH_SCREEN_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();

        //giving a delay of 2ms
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, SPLASH_SCREEN_TIME);
    }
}
