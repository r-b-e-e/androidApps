package android_apps.rb.smartsafe;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by rakeshbalan on 2/8/2016.
 */
public class MyLocationListener extends AppCompatActivity implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
        Log.d("debug", String.valueOf(loc.getLongitude()));
        Log.d("debug", String.valueOf(loc.getLatitude()));

        Firebase locationRef = ActivateBeacon.smartSafeRef.child("location");
        LocationClass locationClass = new LocationClass(loc.getLatitude(), loc.getLongitude());
        locationRef.push().setValue(locationClass);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider,
                                int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}
