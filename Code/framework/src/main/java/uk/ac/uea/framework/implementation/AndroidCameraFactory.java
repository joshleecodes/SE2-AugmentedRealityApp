package uk.ac.uea.framework.implementation;

import android.app.Activity;
import android.support.annotation.NonNull;

import uk.ac.uea.framework.Camera;

/**
 * Created by Jack L. Clements on 24/11/2015.
 * Abstract Factory design pattern, designed to let camera implementation work on most android devices
 */
public class AndroidCameraFactory implements Camera{
    /**Camera object stored by abstract factory */
    private Camera camera;

    /**
     * Constructor for abstract factory, does an API check and constructs the appropriate camera object
     */
    public AndroidCameraFactory(){
        if(android.os.Build.VERSION.SDK_INT >= 21){
            camera = new AndroidCamera();
        }
        else{
            camera = new AndroidDepCamera();
        }
    }

    //THESE NEED IMPLEMENTING

    /**
     * Calls the handlepermissions method in the appropriate camera
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void handlePermissions(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        camera.handlePermissions(requestCode, permissions, grantResults);
    }

    /**
     * Calls the handlepermissions method in the appropriate camera
     * @param height best height
     * @param width best width
     */
    public void chooseBestCameraSize(int height, int width){
       camera.chooseBestCameraSize(height, width);
    }

    /**
     * Calls the handlepermissions method in the appropriate camera
     */
    public void createCameraPreview(){
        camera.createCameraPreview();
    }

    /**
     * Calls the openCamera method in the appropriate camera
     * @param height camera height
     * @param width camera width
     */
    public void openCamera(int height, int width){
        camera.openCamera(height, width);
    }

    /**
     * Calls the closeCamera method in the appropriate camera
     */
    public void closeCamera(){
        camera.closeCamera();
    }

    /**
     * Calls the captureMessage method in the appropriate camera
     * @param string
     */
    public void captureMessage(String string){
        camera.captureMessage(string);
    }

    /**
     * Calls the setPreview method in the appropriate camera
     * @param texture
     */
    public void setPreview(AutofitTextureView texture){
        camera.setPreview(texture);
    }

    /**
     * Calls the addActivity method in the appropriate camera
     * @param activity
     */
    public void addActivity(Activity activity){
        camera.addActivity(activity);
    }

    /**
     * Calls the setSurfaceTextureListener method in the appropriate camera
     */
    public void setSurfaceTextureListener(){
        camera.setSurfaceTextureListener();
    }
    /**
     * Calls the textureViewStatus method in the appropriate camera
     */
    public boolean textureViewStatus(){
        return camera.textureViewStatus();
    }

    /**
     * Getter for camera object, designed to be used as singleton
     * @return
     */
    public Camera getCamera(){
        return camera;
    }
    /*
    public AndroidCameraFactory getInstance(){
        if
    }*/
}
