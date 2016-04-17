package smoking_cessation.uncc.questionnairesmokingcessation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rakeshbalan on 4/13/2016.
 */
public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                //shared preferences


                startActivity(new Intent(SplashScreen.this, NewUserActivity.class));
                finish();
            }
        }, SPLASH_TIME);

    }
}
