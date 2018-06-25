package uk.ac.uea.framework.implementation;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import uk.ac.uea.framework.Pool;
import uk.ac.uea.framework.Input.TouchEvent;
import uk.ac.uea.framework.Pool.PoolObjectFactory;

/**
 * Implementation of the TouchHandler interface designed around handling a single touch
 */
public class SingleTouchHandler implements TouchHandler {
    /**Boolean that stores whether the touch event is a touch down or move event*/
    boolean isTouched;
    /**x-coordinate of touch event */
    int touchX;
    /**y-coordinate of touch event */
    int touchY;
    /**Pool collection object that stores the touch events */
    Pool<TouchEvent> touchEventPool;
    /**List collection object that stores the touch events as an ArrayList */
    List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    /**List collection object that stores the touch events buffer to perform as an ArrayList */
    List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
    /**scale of view in x direction */
    float scaleX;
    /**scale of view in y direction */
    float scaleY;

    /**
     * Constructor for touch handler
     * @param view contains layout for UI components
     * @param scaleX scale of view in x direction
     * @param scaleY scale of view in y direction
     */
    public SingleTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }            
        };
        touchEventPool = new Pool<TouchEvent>(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    /**
     * Takes UI view and the touch event as a parameter then uses a switch statement in order to determine the subsequent events
     * @param v current view associated with touch event
     * @param event MotionEvent object to determine event type
     * @return returns true, presumably to indicate completion of method
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized(this) {
            TouchEvent touchEvent = touchEventPool.newObject();
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchEvent.type = TouchEvent.TOUCH_DOWN;
                isTouched = true;
                break;
            case MotionEvent.ACTION_MOVE:
                touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                isTouched = true;
                break;
            case MotionEvent.ACTION_CANCEL:                
            case MotionEvent.ACTION_UP:
                touchEvent.type = TouchEvent.TOUCH_UP;
                isTouched = false;
                break;
            }
            
            touchEvent.x = touchX = (int)(event.getX() * scaleX);
            touchEvent.y = touchY = (int)(event.getY() * scaleY);
            touchEventsBuffer.add(touchEvent);                        
            
            return true;
        }
    }

    /**
     * Takes identifier for touch event and checks whether it is down or not
     * @param pointer identifier for the touch event
     * @return true if the event is a down event
     */
    @Override
    public boolean isTouchDown(int pointer) {
        synchronized(this) {
            if(pointer == 0)
                return isTouched;
            else
                return false;
        }
    }

    /**
     * Returns the x co-ordinate of the touch event
     * @param pointer identifier for touch event
     * @return the x co-ordinate of the touch event
     */
    @Override
    public int getTouchX(int pointer) {
        synchronized(this) {
            return touchX;
        }
    }

    /**
     * Returns the y co-ordinate of the touch event
     * @param pointer identifier for touch event
     * @return the y co-ordinate of the touch event
     */
    @Override
    public int getTouchY(int pointer) {
        synchronized(this) {
            return touchY;
        }
    }

    /**
     * Returns a list of touch events
     * @return returns the list of touch events from the buffer
     */
    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized(this) {     
            int len = touchEvents.size();
            for( int i = 0; i < len; i++ )
                touchEventPool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }
}
