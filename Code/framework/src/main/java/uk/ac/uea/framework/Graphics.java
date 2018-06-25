package uk.ac.uea.framework;

import android.graphics.Paint;

/**
 * Interface that provides a set of common methods needed for displaying graphics
 */
public interface Graphics {
    /**
     * enum value that specifies the format the images are to be displayed in RGB/ARGB8888/ARGB4444
     */
	public static enum ImageFormat {
        ARGB8888, ARGB4444, RGB565
    }

    /**
     * Method that creates a new {@link Image} object given the file path and format of the image
     * @param fileName the file path of the image
     * @param format the format the image is to be displayed in
     * @return the {@link Image} object
     */
    public Image newImage(String fileName, ImageFormat format);

    /**
     * Method to clear the screen and reset it to a specified colour
     * @param color the colour value (RGB) that the screen is to be reset to
     */
    public void clearScreen(int color);

    /**
     * Method to draw a line, from (x, y) to (x2, y2)
     * @param x horizontal co-ordinate of the first point
     * @param y vertical co-ordinate of the first point
     * @param x2 horizontal co-ordinate of the second point
     * @param y2 vertical co-ordinate of the second point
     * @param color colour of the line
     */
    public void drawLine(int x, int y, int x2, int y2, int color);

    /**
     * Method to draw a rectangular shape
     * @param x horizontal co-ordinate of the point in the top-left of the rectangle
     * @param y vertical co-ordinate of the point in the top-left of the rectangle
     * @param width horizontal co-ordinate of the point in the bottom-right of the rectangle
     * @param height vertical co-ordinate of the point in the bottom-right of the rectangle
     * @param color colour of the object
     */
    public void drawRect(int x, int y, int width, int height, int color);

    /**
     * Draws an image given an {@link Image} object and a frame in which to draw it
     * @param image Image to be drawn
     * @param x horizontal co-ordinate of the point in the top-left of the frame
     * @param y vertical co-ordinate of the point in the top-left of the frame
     * @param srcX horizontal starting point of the top-left of the source image
     * @param srcY vertical starting point of the top-left of the source image
     * @param srcWidth width of the source image
     * @param srcHeight height of the source image
     */
    public void drawImage(Image image, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight);

    /**
     * Overloaded method that draws an image given an {@link Image} object and co-ordinates of the top-left of the frame
     * @param Image Image to be drawn
     * @param x horizontal co-ordinate of the point in the top-left of the frame
     * @param y vertical co-ordinate of the point in the top-left of the frame
     */
    public void drawImage(Image Image, int x, int y);

    /**
     * Method that draws an image of inputted text parsed as a {@link String} object
     * @param text the text to be drawn
     * @param x the horizontal top-left point of the text image
     * @param y the vertical top-left point of the text image
     * @param paint the colouring style and colour
     */
    void drawString(String text, int x, int y, Paint paint);

    /**
     * Method that gets the width of the implementation-specific object
     * @return width
     */
    public int getWidth();

    /**
     * Method that gets the height of the implementation-specific object
     * @return height
     */
    public int getHeight();

    /**
     * Converts given colour information into an ARGB format that includes an alpha-channel
     * @param i alpha channel value
     * @param j red value
     * @param k green value
     * @param l blue value
     */
    public void drawARGB(int i, int j, int k, int l);
}
