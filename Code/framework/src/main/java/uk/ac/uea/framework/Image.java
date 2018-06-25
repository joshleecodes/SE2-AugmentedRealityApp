package uk.ac.uea.framework;

import uk.ac.uea.framework.Graphics.ImageFormat;

/**
 * Interface that provides a set of common methods needed for displaying image objects which can be parsed to a {@link Graphics} canvas
 */
public interface Image {
    /**
     * Returns the width of the image object
     * @return width
     */
    public int getWidth();

    /**
     * Returns the height of the image object
     * @return height
     */
    public int getHeight();

    /**
     * Returns the format of the image as an {@link ImageFormat} enum object
     * @return Image format
     */
    public ImageFormat getFormat();

    /**
     * Recycles the memory used to store the image, effectively disposing of it, as Java garbage disposal is automatic and not efficient for a smartphone framework
     */
    public void dispose();
}
