package android_apps.rakeshbalan.smartsafepubsub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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
import com.pubnub.api.*;
import org.json.*;

/**
 * Created by rakeshbalan on 4/11/2016.
 */
public class SubscribeActivity extends AppCompatActivity {
    JSONObject jsonLatLong;
    double latitude, longitude;
    private GoogleMap mMap;
    Integer titleNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        titleNumber = 0;

        // Try to obtain the map from the SupportMapFragment.
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        try
        {
            //Subscribing the Geo-location json from the channel
            PubNubObject.getPubNubObject().subscribe("smartsafe-private-channel", new Callback() {
                @Override
                public void connectCallback(String channel, Object message) {
                    Log.d("debug", "SUBSCRIBE : CONNECT on channel: " + channel + " " + message.toString());
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    Log.d("debug", "SUBSCRIBE : CONNECT on channel: " + channel + " " + message.toString());
                }

                public void reconnectCallback(String channel, Object message) {
                    Log.d("debug", "SUBSCRIBE : RECONNECT on channel: " + channel + " " + message.toString());
                }

                @Override
                public void successCallback(String channel, Object message) {
                    try {
                        jsonLatLong = (JSONObject) message;
                        Log.d("debug", "SUBSCRIBE : " + channel + " " + jsonLatLong.getString("latitude") + " and " + jsonLatLong.getString("longitude"));

                        latitude = Double.parseDouble(jsonLatLong.getString("latitude"));
                        longitude = Double.parseDouble(jsonLatLong.getString("longitude"));

                        //making the process to run on the main UI thread to update Place Marker
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
                            }});


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    Log.d("debug", "SUBSCRIBE : ERROR on channel " + channel + " " + error.getErrorString().toString());
                }
            });


        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    //When back button is pressed, the location is unsubscribed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PubNubObject.getPubNubObject().unsubscribe("smartsafe-private-channel");
        Toast.makeText(SubscribeActivity.this, "Location Unsubscribed", Toast.LENGTH_SHORT).show();
        finish();
    }

}
