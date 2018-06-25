package uk.ac.uea.framework.implementation;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import uk.ac.uea.framework.Input;

/**
 * Implementation of {@link Input} interface for use in Android
 */
public class AndroidInput implements Input {
    /**TouchHandler object, polymorphic as to handle both types of event */
    TouchHandler touchHandler;

    /**
     * Constructor that takes the app context, current View object and scale to create an input
     * @param context Current context of the app
     * @param view current view triggered to handle event
     * @param scaleX scale of view in x direction
     * @param scaleY scale of view in y direction
     */
    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
        if(Integer.parseInt(VERSION.SDK) < 5) //Ooh boy, more depricated code! What a framework
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        else
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);        
    }

    /**
     * Asks whether specific touch event is down
     * @param pointer identifier for touch event, should be used to refer to fingers on a touch screen
     * @return true if the touch event at the pointer is down, otherwise false
     */
    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    /**
     * Method that returns x co-ordinate of the touch event
     * @param pointer
     * @return x-coordinate of touch event
     */
    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    /**
     * Method that returns y co-ordinate of the touch event
     * @param pointer
     * @return y-coordinate of touch event
     */
    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }


    /**
     * Returns a list of touch events
     * @return
     */
    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
    
}
