package uk.ac.uea.framework.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

import uk.ac.uea.framework.FileIO;

/**
 * Implementation of the FileIO interface for File input/output in an Android app
 */
public class AndroidFileIO implements FileIO {
    /**Object to store the current context of the app */
    Context context;
    /**Object to store assets for FileIO */
    AssetManager assets;
    /**String that stores the default storage path for the IO context */
    String externalStoragePath;

    /**
     * Constructor for the FileIO object
     * @param context current context for the app
     */
    public AndroidFileIO(Context context) {
        this.context = context;
        this.assets = context.getAssets();
        this.externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
        
 
    
    }

    /**
     * Method to read in an asset from a file location.
     * @param file the location of the assset to be read in
     * @return
     * @throws IOException
     */
    @Override
    public InputStream readAsset(String file) throws IOException {
        return assets.open(file);
    }

    /**
     * Method that takes an external file location and returns an InputStream that can be used to read the data
     * @param file location of file to be loaded
     * @return
     * @throws IOException
     */
    @Override
    public InputStream readFile(String file) throws IOException {
        return new FileInputStream(externalStoragePath + file);
    }

    /**
     * Method that takes a potential file location and creates a new file that writes data to it
     * @param file the location/filename of the new file to be created
     * @return {@link OutputStream} the Output for the file
     * @throws IOException when the file path specified already exists or is otherwise invalid
     */
    @Override
    public OutputStream writeFile(String file) throws IOException {
        return new FileOutputStream(externalStoragePath + file);
    }

    /**
     * Method to return preference data (android API) from the app-wide settings
     * @return SharedPreferences object that can then be accessed/modified as necessary
     */
    public SharedPreferences getSharedPref() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
