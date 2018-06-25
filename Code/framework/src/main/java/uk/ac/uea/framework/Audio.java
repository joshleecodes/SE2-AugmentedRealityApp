package uk.ac.uea.framework;

/**
 * Interface that provides a common set of methods that can create two separate types of audio object
 */
public interface Audio {
    /**
     * Method that creates a {@link Music} object from a file location given
     * @param file the path of the audio file to become a {@link Music} object
     * @return a {@link Music} object created from the file
     */
    public Music createMusic(String file);

    /**
     * Method that creates a {@link Sound} object from a file location given
     * @param file the path of the audio file to become a {@link Sound} object
     * @return a {@link Sound} object created from the file
     */
    public Sound createSound(String file);
}
