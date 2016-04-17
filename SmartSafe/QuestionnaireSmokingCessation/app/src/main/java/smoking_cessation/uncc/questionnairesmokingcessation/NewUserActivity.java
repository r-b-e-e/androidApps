package smoking_cessation.uncc.questionnairesmokingcessation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by rakeshbalan on 4/13/2016.
 */

//incase if shared preference fails ..... have this activity to login using their credentials and store SP again
public class NewUserActivity extends AppCompatActivity{
    Button buttonNewUser;
    Button buttonExistingUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);

        buttonNewUser = (Button) findViewById(R.id.buttonNewUser);
        buttonExistingUser = (Button) findViewById(R.id.buttonExistingUser);

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewUserActivity.this, SignUpActivity.class));
            }
        });

        buttonExistingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewUserActivity.this, "Will be implemented later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
