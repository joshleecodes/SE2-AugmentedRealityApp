package uk.ac.uea.framework;

/**
 * Interface that provides a set of common methods needed for playing sound
 */
public interface Sound {
    /**
     * Begins playback of sound at set volume
     * @param volume playback volume for sound
     */
    public void play(float volume);

    /**
     * Recycles the memory used to store the sound, effectively disposing of it, as Java garbage disposal is automatic and not efficient for a smartphone framework
     */
    public void dispose();

    /**
     * Stops the sound playback
     */
    public void stop();
}
