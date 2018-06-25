package uk.ac.uea.framework.implementation;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import uk.ac.uea.framework.Audio;
import uk.ac.uea.framework.Music;
import uk.ac.uea.framework.Sound;

/**
 * Implementation designed to allow the playback of audio in an android app.
 */
public class AndroidAudio implements Audio {
    /** Object to provide access to asset files */
    AssetManager assets;
    /** Object to manage and play audio resources, including streams and low-latency playback     */
    SoundPool soundPool;

    /**
     * Constructor for class that takes the activity context and gets the assets and populates the sound pool
     * @param activity activity context passed to the class
     */
    public AndroidAudio(Activity activity) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.assets = activity.getAssets();
        this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0); //code is deprecated
    }

    /**
     * Method that creates a {@link Music} object from a file location given
     * @param filename the path of the audio file to become a {@link Music} object
     * @return a {@link Music} object created from the file
     */
    @Override
    public Music createMusic(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            return new AndroidMusic(assetDescriptor);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load music '" + filename + "'");
        }
    }
    /**
     * Method that creates a {@link Sound} object from a file location given
     * @param filename the path of the audio file to become a {@link Sound} object
     * @return a {@link Sound} object created from the file
     */
    @Override
    public Sound createSound(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            int soundId = soundPool.load(assetDescriptor, 0);
            return new AndroidSound(soundPool, soundId);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load sound '" + filename + "'");
        }
    }
}
