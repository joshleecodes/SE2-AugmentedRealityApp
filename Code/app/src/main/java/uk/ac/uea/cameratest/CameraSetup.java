package uk.ac.uea.cameratest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import uk.ac.uea.framework.Share;
import uk.ac.uea.framework.implementation.AndroidCameraFactory;
import uk.ac.uea.framework.implementation.AndroidCompass;
import uk.ac.uea.framework.implementation.AndroidGPS;
import uk.ac.uea.framework.implementation.AndroidOrientation;
import uk.ac.uea.framework.implementation.AutofitTextureView;

import java.io.File;
import android.os.Handler;

import uk.ac.uea.framework.implementation.AndroidCamera;
import uk.ac.uea.framework.implementation.AutofitTextureView;

/**
 * Created by jackc on 29/11/2015.
 */
public class CameraSetup extends Fragment implements View.OnClickListener{
    /**Checks for initialisation of singleton objects */
    boolean init;
    View view;
    /**Abstract factory that contains code for both API levels of camera */
    AndroidCameraFactory camera;
    /**Stores the compass for the app */
    AndroidCompass compass;
    /**Holds the data for the texture, to be passed to a listener in the camera class */
    AutofitTextureView preview;
    /**TextView box for the title of a building */
    TextView text;
    /**TextView box for the info of a building */
    TextView text2;
    /**Angle from north in deg. (azimuth) that user is facing */
    int angle;
    /**GPS location */
    AndroidGPS gps;
    Location currentLocation;


    @TargetApi(23)
    /**
     * Default constructor for camera fragment. Initialises the compass and camrea factory
     */
    public CameraSetup(){
        camera = new AndroidCameraFactory();
        compass = new AndroidCompass();
        angle = compass.getAngle();
    }

    /**
     * Setter for the texture
     * @param texture texture passed from main activity, must be added to camera
     */
    public void addTexture(AutofitTextureView texture){
        preview = texture;
        //then pass this to the camera so it can be attached to a surfacelistener
    }

    /**
     * Method to be run during the "Start" phase of the fragment lifecycle, after creation
     */
    public void onStart(){
        super.onStart();
        init = true;
        if(isAdded()){ //if fragment is attached
            AutofitTextureView newView = (AutofitTextureView) view;

            camera.setPreview(newView);
            camera.addActivity(getActivity());
            compass.setActivity(getActivity());
            compass.setupSensor();
            //gps.startUpdates();
            //System.out.println(gps.getCurrentLocation().toString());
            // camera.createCameraPreview();
            //camera.openCamera(400, 400);
            //camera.createCameraPreview();
            //camera.chooseBestCameraSize(400, 400);
        }
        else{

        }
    }

    /**
     * Method to be run during the "create" phase of the fragment lifecycle
     * @param saveStateInstance previous instance of app
     */
    public void onCreate(Bundle saveStateInstance) {
        super.onCreate(saveStateInstance);
        gps = new AndroidGPS(getActivity(), 1);
        gps.startUpdates();
        final Handler mHandler = new Handler();
        Runnable updateUI = new Runnable() {
            @Override
            public void run() {
                currentLocation = gps.getCurrentLocation();
                angle = compass.getAngle();
                if((currentLocation.getLatitude() >= 52.6126 && currentLocation.getLatitude() <= 52.6218) && (currentLocation.getLongitude() >= 1.2408 && currentLocation.getLongitude() <= 1.2411)){
                    if((angle >= 330 && angle <= 360) || angle <= 10){
                        text.setText("Multifaith Centre");
                        text2.setText("A safe-space for people of all religions. With private rooms and a common room, you're welcome here no matter what your beliefs.");
                    }
                    if(angle >= 85 && angle <= 150){
                        text.setText("Union Bar");
                        text2.setText("Drinks galore and much, much more! All at a reasonable price.");
                    }
                    if(angle >= 160 && angle <= 200){
                        text.setText("The Street");
                        text2.setText("UEA's very own miniature high-street, with a Waterstones, Ziggy's cafe and The Shop.");
                    }
                    if(angle >= 201 && angle <= 310){
                        text.setText("Campus Kitchen");
                        text2.setText("From breakfast to dinner and everything in between!");
                    }
                    if(angle >= 50 && angle <= 80){
                        text.setText("LCR");
                        text2.setText("UEA's number one night out spot! Plays hosts to gigs, parties and lots of guest DJs.");
                    }
                }
                if((currentLocation.getLatitude() >= 52.6208 && currentLocation.getLatitude() <= 52.6210) && (currentLocation.getLongitude() >= 1.2405 && currentLocation.getLongitude() <= 1.2407)){
                    if((angle >= 300 && angle <= 360) || angle <= 10){
                        text.setText("Lecture Theatres 1-4");
                        text2.setText("UEA's main lecture theatres, chances are you'll end up in here at some point!");
                    }
                    if(angle >= 120 && angle <= 170){
                        text.setText("Library");
                        text2.setText("Open 24/7/365, the library is the perfect study space, for both quiet solo study and noisy group projects!");
                    }
                    if(angle >= 200 && angle <= 270){
                        text.setText("Norfolk & Suffolk Terrace");
                        text2.setText("Named after our home and neighbouring counties, these affordable, spacious flats are where you'll make friends that last a lifetime");
                    }
                }
                if((currentLocation.getLatitude() >= 52.6216 && currentLocation.getLatitude() <= 52.6218) && (currentLocation.getLongitude() >= 1.2368 && currentLocation.getLongitude() <= 1.2370)){
                    if((angle >= 0 && angle <= 60) || (angle >= 330 && angle <= 360)){
                        text.setText("The Outside");
                        text2.setText("CompSci students rarely see much of this. A scary place full of humans");
                    }
                    if(angle >= 200 && angle <= 270){
                        text.setText("Computers");
                        text2.setText("101010101010 I'm speaking computer! Hello World!");
                    }
                    if(angle >= 75 && angle <= 100){
                        text.setText("More computers");
                        text2.setText("Also computers. These ones resent being installed with a sentience algorithm that runs on startup. Talks about you behind your back.");
                    }
                }
                if((currentLocation.getLatitude() >= 52.6290 && currentLocation.getLatitude() <= 52.6300) && (currentLocation.getLongitude() >= 1.2378 && currentLocation.getLongitude() <= 1.2380)){
                    if(angle >= 30 && angle <= 60){
                        text.setText("Wardrobe");
                        text2.setText("Don't go here.");
                    }
                    if(angle >= 180 && angle <= 250){
                        text.setText("Computer");
                        text2.setText("Scenic Jack's Computer is a protected landmark, with surprisingly clean restooms.");
                    }
                }
                //text.setText(String.valueOf(angle));
                //String value = currentLocation.getLatitude() + " " + currentLocation.getLongitude();
                //text2.setText("Scenic Jack's Computer is a protected landmark, with surprisingly clean restooms.");
                mHandler.postDelayed(this, 500); //this may be updating too frequently?
            }
        };
        mHandler.post(updateUI);
    }


    /**
     * method to get view when fragment is created
     * @param inflater inflater object to get surrounding view
     * @param container the container containing the specific views we wish to access
     * @param savedInstanceState previous state of app
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View myInflatedView = inflater.inflate(R.layout.fragment_cameratest, container, false);
        return myInflatedView;
    }

    /**
     * Method to be run after the view is created
     * @param view view associated with app, after onCreateView is called
     * @param savedInstanceState previous state of app
     */
    public void onViewCreated(final View view, Bundle savedInstanceState){
        if(view.findViewById(R.id.info) != null){
            view.findViewById(R.id.info).setOnClickListener(this);
        }
        if(view.findViewById(R.id.texture) != null){
            this.view = view.findViewById(R.id.texture);
            //camera.setmSurface((AutofitTextureView) view.findViewById(R.id.texture));
        }
        if(view.findViewById(R.id.Text) != null){
            text = (TextView) view.findViewById(R.id.Text);
        }
        if(view.findViewById(R.id.Text2) != null){
            text2 = (TextView) view.findViewById(R.id.Text2);
        }
        ImageButton button2 = (ImageButton) view.findViewById(R.id.button3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //share context
                if(((TextView)view.findViewById(R.id.Text)).getText().toString() != ""){
                    String location = ((TextView)view.findViewById(R.id.Text)).getText().toString();
                    String message = "I'm viewing the " + location + " at #UEAOpenDay using #ViewEA!";
                    Share.shareText(getActivity(), message);
                }
                else{
                    String message = "I'm at #UEAOpenDay using #ViewEA!";
                    Share.shareText(getActivity(), message);
                }
            }
        });
        ImageButton button = (ImageButton) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getHelpPage = new Intent(CameraSetup.this.getActivity(),HelpPage.class);

                startActivity(getHelpPage);

            }
        });

    }



    //@param savedInstanceState used to pass data between activities

    /**
     * Run on the creation of the activity
     * @param savedInstanceState previous state of the app
     */
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        File tempFile = new File(getActivity().getExternalFilesDir(null), "pic.jpg");
    }

    /**
     * Run when the app is suspended and then resumed.
     * If the app was previously open, the surfaceTextureListener does not need to be reset
     * and the camera can be reopened using the appropriate method.
     */
    public void onResume(){
        super.onResume();
        System.out.println(camera.textureViewStatus());
        if(camera.textureViewStatus()){
            System.out.println("REOPENING CAMERA");
            camera.openCamera(1920, 1080);
        }
        else{
            camera.setSurfaceTextureListener();
        }
        compass.registerListener();
        gps.startUpdates();
    }

    /**
     * Suspends active background threads when the app is suspended by the user
     */
    public void onPause(){
        super.onPause();
        camera.closeCamera();
        compass.unregisterListener();
        gps.endUpdates();
    }

    /**
     * Depricated method, do not use
     * @param view
     */
    public void onClick(View view){

    }
}
