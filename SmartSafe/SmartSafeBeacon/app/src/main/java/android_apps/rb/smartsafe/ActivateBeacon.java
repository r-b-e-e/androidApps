package android_apps.rb.smartsafe;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.client.Firebase;

/**
 * Created by rakeshbalan on 2/8/2016.
 */

public class ActivateBeacon extends AppCompatActivity {
    ToggleButton toggleButtonActivate;
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
    public static Firebase smartSafeRef = new Firebase("https://smartsafe.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate);
        Firebase.setAndroidContext(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setIcon(R.mipmap.app_icon);

        toggleButtonActivate = (ToggleButton) findViewById(R.id.toggleButtonActivate);
        toggleButtonActivate.setEnabled(true);

        toggleButtonActivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (getGpsStatus()) {
                        Toast.makeText(ActivateBeacon.this, "Tracking Activated", Toast.LENGTH_SHORT).show();
                        Log.d("debug", "Tracking Activated");
                        locationListener = new MyLocationListener();

                        try {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                        } catch (SecurityException se) {
                            Log.d("debug", se.getMessage().toString());
                            Toast.makeText(ActivateBeacon.this, se.getMessage().toString() + ". Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        toggleButtonActivate.setChecked(false);
                        alertbox();
                    }
                } else {
                    Toast.makeText(ActivateBeacon.this, "Tracking Stopped", Toast.LENGTH_SHORT).show();
                    Log.d("debug", "Tracking Stopped");
                }
            }
        });

    }

    protected void alertbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your device's GPS is disabled")
                .setCancelable(false)
                .setTitle("GPS Status")
                .setPositiveButton("GPS ON",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean getGpsStatus() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
        return gpsStatus;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
