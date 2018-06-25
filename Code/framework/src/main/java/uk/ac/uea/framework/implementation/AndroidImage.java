package uk.ac.uea.framework.implementation;

import android.graphics.Bitmap;

import uk.ac.uea.framework.Image;
import uk.ac.uea.framework.Graphics.ImageFormat;

/**
 * Implementation of Image interface
 */
public class AndroidImage implements Image {
    /**Bitmap object to store the image loaded in */
    Bitmap bitmap;
    /**Stores the image format enum from Graphics interface */
    ImageFormat format;

    /**
     * Constructor for class that takes the image and format
     * @param bitmap bitmap image object to be stored in object
     * @param format format of the image (types in enum in {@link uk.ac.uea.framework.Graphics} interface)
     */
    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    /**
     * Returns the width of the image object
     * @return width
     */
    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    /**
     * Returns the height of the image object
     * @return height
     */
    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    /**
     * Returns the format of the image as an {@link ImageFormat} enum object
     * @return format of the image
     */
    @Override
    public ImageFormat getFormat() {
        return format;
    }

    /**
     * Recycles the memory used to store the image, effectively disposing of it, as Java garbage disposal is automatic and not efficient for a smartphone framework
     */
    @Override
    public void dispose() {
        bitmap.recycle();
    }      
}
