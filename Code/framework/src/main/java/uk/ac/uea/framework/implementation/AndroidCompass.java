package uk.ac.uea.framework.implementation;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import uk.ac.uea.framework.Orientation;

/**
 * Created by Jack L. Clements on 30/01/2016.
 */
public class AndroidCompass implements Orientation {
    /**Manager object for all sensors on a device */
    private SensorManager sensorM;
    /**Sensor object representing the device geomagnetic field sensor */
    private Sensor magneticFieldSensor;
    /**Sensor object representing the device accelerometer*/
    private Sensor accelerometerSensor;
    /**The activity of the parent class making the object calls */
    private Activity activity;
    /**Last read set of magnetic field values, in format x, y, z */
    private float[] magneticValues;
    /**Last read set of accelerometer values, in LOCAL format x, y, z (see graphics theory for more) */
    private float[] accelValues;
    /**Double object used to convert the final north value to an int */
    Double degToInt;
    /**Alpha value used for lowpass filter */
    private final float ALPHA = 0.25f;

    /**
     * Nested class implementation of the SensorEventListener interface.
     * A listener object designed to detect and react to changes in the {@link Sensor} object
     */
    private final SensorEventListener mSensorEventListener = new SensorEventListener() {
        /**
         * Method run by listener when the sensor input changes
         * @param sensorEvent
         */
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            Sensor mySensor = sensorEvent.sensor;
            if(mySensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){

                //code to handle magnetic field
                for(int i = 0; i < 3; i++){
                    magneticValues[i] = sensorEvent.values[i];
                }
                magneticValues = lowPass(sensorEvent.values.clone(), magneticValues);

                //System.out.println("MAGNETS X - " + magneticValues[0] + " Y - " + magneticValues[1] + " Z - " + magneticValues[2]);
            } //test values before calculating anything else
            if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){

                for(int i = 0; i < 3; i++){
                    accelValues[i] = sensorEvent.values[i];
                }
                accelValues = lowPass(sensorEvent.values.clone(), accelValues);
            }
            calculateNorth();
        }

        /**
         * Called when the accuracy set changes. Accuracy will need not change for these implementations.
         * @param sensor
         * @param i
         */
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    /**
     * Default constructor, initialises arrays and objects.
     */
    public AndroidCompass(){
        magneticValues = new float[3];
        accelValues = new float[3];
        degToInt = 0.0;
    }

    /**
     * Establishes sensors - specifically the accelerometer
     */
    public void setupSensor(){
        sensorM = (SensorManager) activity.getSystemService(activity.SENSOR_SERVICE);
        magneticFieldSensor = sensorM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * Registers the listener object to the sensor, to detect events
     */
    public void registerListener(){
        sensorM.registerListener(mSensorEventListener, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorM.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Unregisters the listener object from the sensor, to be used when pausing or closing the app
     */
    public void unregisterListener(){
        sensorM.unregisterListener(mSensorEventListener, magneticFieldSensor);
        sensorM.unregisterListener(mSensorEventListener, accelerometerSensor);
    }

    /**
     * Sets the {@link Activity} context to grab system hardware specifics
     * @param activity
     */
    public void setActivity(Activity activity){
        this.activity = activity;
    }

    /**
     * Calculates the azimuth from the given data.
     * Gravity matrix is remapped to the upright position in order to track the camera movement.
     * This is done with a rotation matrix - see android graphics theory for more details.
     */
    public void calculateNorth(){
        float[] rotR = new float[9];
        float[] rotI = new float[9];
        float[] remapR = new float[9];
        float[] oritentation = new float[3];
        sensorM.getRotationMatrix(rotR, rotI, accelValues, magneticValues);
        sensorM.remapCoordinateSystem(rotR, SensorManager.AXIS_X, SensorManager.AXIS_Z, remapR);
        sensorM.getOrientation(remapR, oritentation);
        double azimuthInDegress = Math.toDegrees(oritentation[0]);
        if (azimuthInDegress < 0.0f) {
            azimuthInDegress += 360.0f;
        }
        degToInt = (Double) azimuthInDegress;
        //System.out.println("YOU ARE POINTING - " + degToInt.intValue());
    }

    /**
     * returns the int value of the aziumuth
     * @return
     */
    public int getAngle(){
        return degToInt.intValue();
    }

    /**
     * A low pass filter, run through the compass data in order to eliminate erroneous results.
     * Output is stored as a parameter and return type to calculate output.
     * @param input input array data
     * @param output the new, filtered data
     * @return the output
     */
    public float[] lowPass(float[] input, float[] output){
        if(output == null){
            return input;
        }
        for(int i = 0; i < input.length; i++){
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }
}
