package uk.ac.uea.framework.implementation;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import uk.ac.uea.framework.GPS;

/**
 * Class that handles the GPS tracking of a device, given a Context and a period, the number of
 * seconds between each location update.
 * Created by Caine on 31/01/2016.
 */
public class AndroidGPS implements GPS {
    private Location currentLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private int period;


    /**
     * Constructor for a GPS tracker. Creates an instance of LocationManager and a LocationListener
     * to enable location updates. Also sets how often the device's current location is updated.
     * @param c The context of the current Activity
     * @param period The number of seconds between each location update.
     */
    public AndroidGPS(Context c, int period){
        this.period = period;
        //Create the LocationManager
        locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        // Get the last known location using the correct criteria
        Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        String provider = locationManager.getBestProvider(criteria, true);
        currentLocation = locationManager.getLastKnownLocation(provider);
        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Update the device's current location
                currentLocation = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        Log.d("AndroidGPS: ", "Instance Created");
    }

    /**
     * Starts the updates via the listener for the active GPS location
     */
    public void startUpdates(){
        //Add correct criteria
        Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        String provider = locationManager.getBestProvider(criteria, true);
        //Request location updates every few seconds
        locationManager.requestLocationUpdates(provider, period*1000, 0, locationListener);
    }

    /**
     * Stops the updates via the listener
     */
    public void endUpdates(){
        // Stop updating the user's location
        locationManager.removeUpdates(locationListener);
    }

    /**
     *
     * @return current location
     */
    public Location getCurrentLocation(){
        return currentLocation;
    }

    /**
     * Sets period of time to update
     * @param period number of seconds for the handler to update
     */
    public void setPeriod(int period){
        this.period = period;
    }

    /**
     * Checks the proximity of you to the destination
     * @param range The minimum distance required to be "within range" of the destination.
     * @param destination The location the device is travelling to.
     * @return true if if the distance is less than the range
     */
    public boolean proximityCheck(double range, Location destination){
        double distance = currentLocation.distanceTo(destination);
        if (distance <= range){
            return true;
        }else{
            return false;
        }
    }
}
