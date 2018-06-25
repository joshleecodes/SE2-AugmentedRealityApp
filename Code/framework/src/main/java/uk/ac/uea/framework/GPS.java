package uk.ac.uea.framework;

import android.location.Location;

/**
 * Interface that provides a set of common methods needed to track the user's current location.
 * Created by Caine on 30/01/2016.
 */
public interface GPS {

    /**
     * Begins updating the device's current location. Usually called in onStart() or onResume().
     */
    public void startUpdates();

    /**
     * Stops updating the device's current location. Usually called in onPause() or onStop().
     */
    public void endUpdates();

    /**
     * Returns the device's current location. A check must be put in place to assure the returned
     * location is not null.
     * @return The current Location of the device.
     */
    public Location getCurrentLocation();

    /**
     * Sets the number of seconds between each location update.
     * @param period
     */
    public void setPeriod(int period);

    /**
     * Returns true if the device is within a specified range of a destination.
     * @param range The minimum distance required to be "within range" of the destination.
     * @param destination The location the device is travelling to.
     * @return
     */
    public boolean proximityCheck(double range, Location destination);
}
