package uk.ac.uea.framework.implementation;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import uk.ac.uea.framework.Pool;
import uk.ac.uea.framework.Input.TouchEvent;
import uk.ac.uea.framework.Pool.PoolObjectFactory;
/**
 * Implementation of the TouchHandler interface designed around handling multi-touch events
 */
public class MultiTouchHandler implements TouchHandler {
    /**Stores the maximum amount of touchpoints that a device can have. 10 fingers for most humans, so 10 is a logical number. */
    private static final int MAX_TOUCHPOINTS = 10;
    /**Boolean array that stores whether each touch event is a touch down or move event*/
    boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
    /**Array of x-coordinates of each touch event */
    int[] touchX = new int[MAX_TOUCHPOINTS];
    /**Array of y-coordinates of each touch event */
    int[] touchY = new int[MAX_TOUCHPOINTS];
    /**Array of identification integers */
    int[] id = new int[MAX_TOUCHPOINTS];
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
     * Constructor for multi touch handler class.
     * @param view contains layout for UI components
     * @param scaleX scale of view in x direction
     * @param scaleY scale of view in y direction
     */
    public MultiTouchHandler(View view, float scaleX, float scaleY) {
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
     * Takes UI view and the touch event as a parameter then uses a switch statement in order to determine what type of event each event in the array is
     * @param v current view associated with touch event
     * @param event MotionEvent object to determine event type
     * @return returns true, presumably to indicate completion of method
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
            int pointerCount = event.getPointerCount();
            TouchEvent touchEvent;
            for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
                if (i >= pointerCount) {
                    isTouched[i] = false;
                    id[i] = -1;
                    continue;
                }
                int pointerId = event.getPointerId(i);
                if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                    // if it's an up/down/cancel/out event, mask the id to see if we should process it for this touch
                    // point
                    continue;
                }
                switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.type = TouchEvent.TOUCH_DOWN;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
                    touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                    isTouched[i] = true;
                    id[i] = pointerId;
                    touchEventsBuffer.add(touchEvent);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.type = TouchEvent.TOUCH_UP;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
                    touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                    isTouched[i] = false;
                    id[i] = -1;
                    touchEventsBuffer.add(touchEvent);
                    break;

                case MotionEvent.ACTION_MOVE:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
                    touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                    isTouched[i] = true;
                    id[i] = pointerId;
                    touchEventsBuffer.add(touchEvent);
                    break;
                }
            }
            return true;
        }
    }

    /**
     * Takes identifier for specific touch event in array and checks whether it is down or not
     * @param pointer identifier for the touch event
     * @return
     */
    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return false;
            else
                return isTouched[index];
        }
    }
    /**
     * Returns the x co-ordinate of the specified touch event in the array of events
     * @param pointer identifier for touch event
     * @return the x co-ordinate of the touch event
     */
    @Override
    public int getTouchX(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchX[index];
        }
    }
    /**
     * Returns the y co-ordinate of the specified touch event in the array of events
     * @param pointer identifier for touch event
     * @return the y co-ordinate of the touch event
     */
    @Override
    public int getTouchY(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchY[index];
        }
    }

    /**
     * Returns a list of touch events
     * @return returns the list of touch events from the buffer
     */
    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents.size();
            for (int i = 0; i < len; i++)
                touchEventPool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }

    /**
     * returns the index for a given pointerId or -1 if no index.
     */
    private int getIndex(int pointerId) {
        for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
            if (id[i] == pointerId) {
                return i;
            }
        }
        return -1;
    }
}
