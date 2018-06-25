package uk.ac.uea.cameratest;

/**
 * Created by jackc on 29/11/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import uk.ac.uea.framework.implementation.AutofitTextureView;


public class CameraActivity extends Activity {

    public CameraActivity(){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Creates proper aspect ratio for 16:9 capture by removing notification bar and title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set up preview texture
        AutofitTextureView preview = (AutofitTextureView) findViewById(R.id.texture);
        //now texture has been set up, it needs connecting to the surface listener
        CameraSetup setup = new CameraSetup();
        setup.addTexture(preview);
        setContentView(R.layout.fragment_cameratest);
        if(savedInstanceState == null){
            getFragmentManager().beginTransaction().replace(R.id.framing, setup).commit();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

}
