package uk.ac.uea.framework;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Class to implement the ability to share strings and images to social media.
 * Created by Caine on 02/02/2016.
 */
public class Share {

    /**
     * Shares a specific string to an app that will accept it.
     * @param context
     * @param string
     */
    public static void shareText(Context context, String string){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, string);
        shareIntent.setType("text/plain");
        context.startActivity(shareIntent);
    }

    /**
     * Shares a specific string and image to an app that will accept it.
     * @param context
     * @param string
     * @param image
     */
    public static void shareTextAndImage(Context context, String string, Uri image){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, string);
        shareIntent.putExtra(Intent.EXTRA_STREAM, image);
        shareIntent.setType("*/*");
        context.startActivity(shareIntent);
    }
}
