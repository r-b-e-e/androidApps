package android_apps.rakeshbalan.smartsafepubsub;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.*;
import android.location.Location;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager = null;
    private LocationListener locationListener = null; // to handle location changes
    JSONObject jsonLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button imageViewPublish = (Button) findViewById(R.id.imageViewPublish);
        Button imageViewSubscribe = (Button) findViewById(R.id.imageViewSubscribe);

        imageViewPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (getGpsStatus())
                {
                    Toast.makeText(MainActivity.this, "Location Published", Toast.LENGTH_SHORT).show();

                    //Log.d("debug", "Location Published");
                    try {
                        locationListener = new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                Toast.makeText(MainActivity.this, location.getLatitude() + " and " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                                try {
                                    //converting the latitude and longitude to a JSON Object
                                    jsonLatLong = new JSONObject();
                                    jsonLatLong.put("latitude", location.getLatitude());
                                    jsonLatLong.put("longitude", location.getLongitude());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //Publishing the Geo-location json in the channel
                                PubNubObject.getPubNubObject().publish("smartsafe-private-channel", jsonLatLong, new Callback() {
                                    @Override
                                    public void successCallback(String channel, Object message) {
                                        Log.d("debug", channel + " " + message.toString());
                                    }

                                    @Override
                                    public void errorCallback(String channel, PubnubError error) {
                                        Log.d("debug", channel + " " + error.getErrorString().toString());
                                    }
                                });
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {

                            }

                            @Override
                            public void onProviderEnabled(String provider) {

                            }

                            @Override
                            public void onProviderDisabled(String provider) {

                            }
                        };
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener); //LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE
                    } catch (SecurityException se) {
                        Log.d("debug", se.getMessage().toString());
                        Toast.makeText(MainActivity.this, se.getMessage().toString() + ". Please try again later", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    alertbox();
                }
            }
        });

        //goes to Subscribed Map Activity
        imageViewSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SubscribeActivity.class));
            }
        });
    }

    //AlertDialog to be displayed incase if the GPS is not enabled
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

    //Check for the GPS Status
    private boolean getGpsStatus() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
        return gpsStatus;
    }
}
