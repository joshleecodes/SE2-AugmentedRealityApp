package uk.ac.uea.framework;

/**
 * Interface that provides a set of common methods needed for handling music playback
 */
public interface Music {
    /**
     * Starts the music object playing
     */
    public void play();

    /**
     * Stops the music object playing
     */
    public void stop();

    /**
     * Pauses the music object, saving the time-code for when the {@link Music#play()} is next called
     */
    public void pause();

    /**
     * Sets whether the song will loop after it is done playing
     * @param looping boolean used to determine whether the loop will occur. True for looping, False for not.
     */
    public void setLooping(boolean looping);

    /**
     * Sets the playback volume of the music.
     * @param volume float value used to specify volume
     */
    public void setVolume(float volume);

    /**
     * Accessor method used to determine whether the music object is playing
     * @return true if playing, otherwise false
     */
    public boolean isPlaying();

    /**
     * Accessor method used to determine whether the music object is stopped
     * @return true if stopped, otherwise false
     */
    public boolean isStopped();

    /**
     * Accessor method used to determine whether the music object is set to loop
     * @return true if looping, otherwise false
     */
    public boolean isLooping();

    /**
     * Recycles the memory used to store the music, effectively disposing of it, as Java garbage disposal is automatic and not efficient for a smartphone framework
     */
    public void dispose();

    /**
     * Returns the play-head of the music track to the beginning.
     */
    void seekBegin();
}