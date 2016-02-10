package android_apps.rb.smartsafe.smartsafemonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * Created by rakeshbalan on 2/9/2016.
 */
public class MapActivity extends AppCompatActivity  {
    LinkedHashMap<Integer, LocationClass> hashMapLocation;
    Integer iterator;
    double latitude, longitude;
    private GoogleMap mMap;
    Integer titleNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Firebase.setAndroidContext(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setIcon(R.mipmap.app_icon);
        titleNumber = 0;


        Firebase refMap = new Firebase("https://smartsafe.firebaseio.com/location");
        refMap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Log.d("debug", String.valueOf(snapshot.getValue()));

                    Log.d("debug", "Children: " + snapshot.getChildrenCount());
                    hashMapLocation = new LinkedHashMap<Integer, LocationClass>();
                    iterator = 0;
                    mMap = null;


                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        //LocationClass locationClass = postSnapshot.getValue(LocationClass.class);
                        //Log.d("debug", locationClass.getLatitudeValue() + " " + locationClass.getLongitudeValue());

                        HashMap<String, Double> hashMapLocationSingle = (HashMap<String, Double>) postSnapshot.getValue();
                        Log.d("debug", hashMapLocationSingle.get("latitudeValue") + " " + hashMapLocationSingle.get("longitudeValue"));

                        LocationClass locationClass = new LocationClass(hashMapLocationSingle.get("latitudeValue"), hashMapLocationSingle.get("longitudeValue"));
                        iterator++;
                        hashMapLocation.put(iterator, locationClass);
                    }

                    Integer maxKey = Collections.max(hashMapLocation.keySet());
                    Log.d("debug", String.valueOf(maxKey));
                    latitude = hashMapLocation.get(maxKey).getLatitudeValue();
                    longitude = hashMapLocation.get(maxKey).getLongitudeValue();

                    if (mMap == null) {
                        // Try to obtain the map from the SupportMapFragment.
                        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                                .getMap();
                        // Check if we were successful in obtaining the map.
                        if (mMap != null) {
                            LatLng placeFound = new LatLng(latitude, longitude);
                            titleNumber++;
                            MarkerOptions marker = new MarkerOptions().position(placeFound).title(String.valueOf(titleNumber));
                            marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_icon));
                            mMap.addMarker(marker);

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(placeFound));
                            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(placeFound, 18); //This goes up to 21
                            mMap.animateCamera(yourLocation);
                        }
                    }

                } else {
                    Log.d("debug", "No Data Available");
                    Toast.makeText(MapActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("debug", firebaseError.getMessage());
                Toast.makeText(MapActivity.this, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
