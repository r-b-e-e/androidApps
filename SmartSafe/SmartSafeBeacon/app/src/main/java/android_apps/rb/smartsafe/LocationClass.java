package android_apps.rb.smartsafe;

/**
 * Created by rakeshbalan on 2/8/2016.
 */
public class LocationClass {
    double latitudeValue, longitudeValue;

    public LocationClass(double latitudeValue, double longitudeValue) {
        this.latitudeValue = latitudeValue;
        this.longitudeValue = longitudeValue;
    }

    public double getLatitudeValue() {
        return latitudeValue;
    }

    public void setLatitudeValue(double latitudeValue) {
        this.latitudeValue = latitudeValue;
    }

    public double getLongitudeValue() {
        return longitudeValue;
    }

    public void setLongitudeValue(double longitudeValue) {
        this.longitudeValue = longitudeValue;
    }
}
