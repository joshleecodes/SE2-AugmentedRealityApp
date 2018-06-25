package uk.ac.uea.framework.implementation;

import android.media.SoundPool;

import uk.ac.uea.framework.Sound;

/**
 * Class that implements the Sound interface to provide an Android-specific implementation
 */
public class AndroidSound implements Sound {
    /**Identification number of sound object */
    int soundId;
    /**Object that can hold audio streams, sound control methods and a collection of samples included in the Android SDK */
    SoundPool soundPool;

    /**
     * Constructor that takes values for fields in order to construct object
     * @param soundPool soundPool object
     * @param soundId soundID int
     */
    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    /**
     * Begins playback of sound at set volume
     * @param volume playback volume for sound
     */
    @Override
    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    /**
     * Recycles the memory used to store the sound, effectively disposing of it, as Java garbage disposal is automatic and not efficient for a smartphone framework
     */
    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }

    /**
     * Stops the sound playback
     */
    @Override
    public void stop(){
        soundPool.stop(soundId);
    }
}
