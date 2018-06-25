package uk.ac.uea.framework;

import java.util.List;

/**
 * Interface that provides a set of common methods needed for handling user input
 */
public interface Input {
    /**
     * Static nested class that deals with the data associated with touch events and their location
     */
    public static class TouchEvent {
        /**
         * Set of fields concerned with the different types of touch events.
         */
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;
        public static final int TOUCH_HOLD = 3;
        /**
         * Set of fields set when an event is triggered, storing the type, the location and the pointer.
         */
        public int type;
        public int x, y;
        public int pointer;


    }

    /**
     * Asks whether specific touch event is down
     * @param pointer identifier for touch event, should be used to refer to fingers on a touch screen
     * @return true if the touch event at the pointer is down, otherwise false
     */
    public boolean isTouchDown(int pointer);

    /**
     * Method that returns x co-ordinate of the touch event
     * @param pointer
     * @return x-coordinate of touch event
     */
    public int getTouchX(int pointer);

    /**
     * Method that returns y co-ordinate of the touch event
     * @param pointer
     * @return y-coordinate of touch event
     */
    public int getTouchY(int pointer);

    /**
     * Returns a list of touch events
     * @return
     */
    public List<TouchEvent> getTouchEvents();
}
