package android_apps.rakeshbalan.smartsafepubsub;

import com.pubnub.api.Pubnub;

/**
 * Created by rakeshbalan on 4/13/2016.
 */



//Similar to singleton class to reuse the same Pubnub object
public class PubNubObject {
    private static Pubnub pubnub = new Pubnub("pub-c-64ef573b-24bb-4def-b450-a97bdefe0590", "sub-c-9dc52cf8-ffb8-11e5-8916-0619f8945a4f", "", "", false);

    public static Pubnub getPubNubObject(){
        return pubnub;
    }
}
