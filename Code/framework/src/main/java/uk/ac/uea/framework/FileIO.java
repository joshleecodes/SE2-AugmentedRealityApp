package uk.ac.uea.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.SharedPreferences;

/**
 * Interface that provides a set of common methods needed for file input/output
 */
public interface FileIO {
    /**
     * Method that takes a file location and returns an InputStream that can be used to read the data
     * @param file location of file to be loaded
     * @return the {@link InputStream} object of the loaded file.
     * @throws IOException when the file path specified is incorrect
     */
    public InputStream readFile(String file) throws IOException;

    /**
     * Method that takes a potential file location and creates a new file that writes data to it
     * @param file the location of the new file to be created
     * @return {@link OutputStream} the Output for the file
     * @throws IOException when the file path specified already exists or is otherwise invalid
     */
    public OutputStream writeFile(String file) throws IOException;

    /**
     * Method to read in an asset from a file location. Should behave differently than {@link FileIO#readFile(String)}
     * @param file the location of the assset to be read in
     * @return the {@link InputStream} object of the loaded file.
     * @throws IOException when the file path specified is incorrect
     */
    public InputStream readAsset(String file) throws IOException;

    /**
     * Method to return preference data (android API) from the app-wide settings
     * @return SharedPreferences object that can then be accessed/modified as necessary
     */
    public SharedPreferences getSharedPref();
}
