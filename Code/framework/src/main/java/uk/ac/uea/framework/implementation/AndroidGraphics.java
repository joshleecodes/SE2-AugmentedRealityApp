package uk.ac.uea.framework.implementation;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import uk.ac.uea.framework.Graphics;
import uk.ac.uea.framework.Image;

/**
 * Class that allows the display of graphics on a canvas, including drawn images and image files (.png, .jpg, etc.)
 */
public class AndroidGraphics implements Graphics {
    /** Object that stores the assets for the graphics implementation    */
    AssetManager assets;
    /** Bitmap object that stores the complete frame to be displayed, from the memory buffer. Designed more around game implementations, fitting in with the intention of the legacy code  */
    Bitmap frameBuffer;
    /** Canvas object that creates and stores the graphics canvas to which things can be drawn    */
    Canvas canvas;
    /**  Paint object that holds the colour and style information for drawing objects   */
    Paint paint;
    /**     */
    Rect srcRect = new Rect();
    /**     */
    Rect dstRect = new Rect();

    /**
     * Constructor that loads in the assets and framebuffer for displaying images in bitmap format, then creates a canvas and paint context.
     * @param assets Assets to be loaded in
     * @param frameBuffer Framebuffer object to store the frame
     */
    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer); //Canvas created from framebuffer
        this.paint = new Paint(); //New paint object to store style/colour
    }

    /**
     * Creates a new image object in a format that can be drawn to the canvas
     * @param fileName the file path of the image
     * @param format the format the image is to be displayed in
     * @return
     */
    @Override
    public Image newImage(String fileName, ImageFormat format) {
        Config config = null;
        if (format == ImageFormat.RGB565)
            config = Config.RGB_565;
        else if (format == ImageFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;
        
        
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = ImageFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = ImageFormat.ARGB4444;
        else
            format = ImageFormat.ARGB8888;

        return new AndroidImage(bitmap, format);
    }

    /**
     * Clears the current screen and resets it to a new colour value
     * @param color the colour value (RGB) that the screen is to be reset to
     */
    @Override
    public void clearScreen(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    /**
     * Draws a line on the canvas, from (x, y) to (x2, y2)
     * @param x horizontal co-ordinate of the first point
     * @param y vertical co-ordinate of the first point
     * @param x2 horizontal co-ordinate of the second point
     * @param y2 vertical co-ordinate of the second point
     * @param color colour of the line
     */
    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    /**
     * Draws a rectangular shape on the canvas
     * @param x horizontal co-ordinate of the point in the top-left of the rectangle
     * @param y vertical co-ordinate of the point in the top-left of the rectangle
     * @param width horizontal co-ordinate of the point in the bottom-right of the rectangle
     * @param height vertical co-ordinate of the point in the bottom-right of the rectangle
     * @param color colour of the object
     */
    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }

    /**
     * Converts given colour information into an ARGB format that includes an alpha-channel
     * @param a alpha channel value
     * @param r red value
     * @param g green value
     * @param b blue value
     */
    @Override
    public void drawARGB(int a, int r, int g, int b) {
        paint.setStyle(Style.FILL);
       canvas.drawARGB(a, r, g, b);
    }

    /**
     * Draws an image of inputted text parsed as a {@link String} object to the canvas
     * @param text the text to be drawn
     * @param x the horizontal top-left point of the text image
     * @param y the vertical top-left point of the text image
     * @param paint the colouring style and colour
     */
    @Override
    public void drawString(String text, int x, int y, Paint paint){
        canvas.drawText(text, x, y, paint);

        
    }

    /**
     * Draws an image on the canvas given an {@link Image} object and a frame in which to draw it
     * @param Image
     * @param x horizontal co-ordinate of the point in the top-left of the frame
     * @param y vertical co-ordinate of the point in the top-left of the frame
     * @param srcX horizontal starting point of the top-left of the source image
     * @param srcY vertical starting point of the top-left of the source image
     * @param srcWidth width of the source image
     * @param srcHeight height of the source image
     */
    public void drawImage(Image Image, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;

        canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect,
                null);
    }

    /**
     * Overloaded method that draws an image on the canvas given an {@link Image} object and co-ordinates of the top-left of the frame
     * @param Image Image to be drawn
     * @param x horizontal co-ordinate of the point in the top-left of the frame
     * @param y vertical co-ordinate of the point in the top-left of the frame
     */
    @Override
    public void drawImage(Image Image, int x, int y) {
        canvas.drawBitmap(((AndroidImage)Image).bitmap, x, y, null);
    }

    /**
     * Draws a scaled version of the image on the canvas, given the parameters of the original and the new image to be drawn
     * @param Image Image to be drawn and scaled
     * @param x New X co-ordinate
     * @param y New Y co-ordinate
     * @param width New width
     * @param height New height
     * @param srcX Original X co-ordiante
     * @param srcY Original Y co-ordinate
     * @param srcWidth Source width
     * @param srcHeight Source height
     */
    public void drawScaledImage(Image Image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight){
        
        
     srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + width;
        dstRect.bottom = y + height;
        
   
        
        canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect, null);
        
    }

    /**
     * Returns the width of the canvas via the frame buffer's info one one frame
     * @return the height of a single frame
     */
    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    /**
     * Returns the height of the canvas via the frame buffer's info on one frame
     * @return the height of a single frame
     */
    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
}
