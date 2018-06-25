package uk.ac.uea.framework.implementation;

import java.util.List;

import android.view.View.OnTouchListener;

import uk.ac.uea.framework.Input.TouchEvent;

/**
 * Interface that provides a set of methods for handling different types of touch events, both single and multi-touch
 */
public interface TouchHandler extends OnTouchListener {
    /**
     * Takes identifier for touch event and checks whether it is down or not
     * @param pointer identifier for the touch event
     * @return true if touch is down, else false
     */
    public boolean isTouchDown(int pointer);

    /**
     * Returns the x co-ordinate of the touch event
     * @param pointer identifier for touch event
     * @return the x co-ordinate of the touch event
     */
    public int getTouchX(int pointer);

    /**
     * Returns the y co-ordinate of the touch event
     * @param pointer identifier for touch event
     * @return the y co-ordinate of the touch event
     */
    public int getTouchY(int pointer);

    /**
     * Returns a list of touch events
     * @return
     */
    public List<TouchEvent> getTouchEvents();
}
