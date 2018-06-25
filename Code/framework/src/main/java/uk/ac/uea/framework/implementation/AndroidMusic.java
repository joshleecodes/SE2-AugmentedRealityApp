package uk.ac.uea.framework.implementation;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;

import uk.ac.uea.framework.Music;

/**
 * Class that implements the Music interface to provide a way of playing music in the context of an Android app
 */
public class AndroidMusic implements Music, OnCompletionListener, OnSeekCompleteListener, OnPreparedListener, OnVideoSizeChangedListener {
    /**MediaPlayer object to control playback of music */
    MediaPlayer mediaPlayer;
    /**Boolean that stores whether the class is ready to play music */
    boolean isPrepared = false;

    /**
     * Constructor for class that takes a file descriptor for the asset to be played (preferably sound) otherwise throw an exception
     * @param assetDescriptor
     */
    public AndroidMusic(AssetFileDescriptor assetDescriptor) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnSeekCompleteListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnVideoSizeChangedListener(this);
            
        } catch (Exception e) {
            throw new RuntimeException("Couldn't load music");
        }
    }

    /**
     * Recycles the memory used to store the music, effectively disposing of it, as Java garbage disposal is automatic and not efficient for a smartphone framework
     */
    @Override
    public void dispose() {
    
         if (this.mediaPlayer.isPlaying()){
               this.mediaPlayer.stop();
                }
        this.mediaPlayer.release();
    }

    /**
     * Accessor method used to determine whether the music object is set to loop
     * @return true if looping, otherwise false
     */
    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    /**
     * Method that uses mediaPlayer's accessor method used to determine whether the music object is playing
     * @return true if playing, otherwise false
     */
    @Override
    public boolean isPlaying() {
        return this.mediaPlayer.isPlaying();
    }

    /**
     * Method that uses mediaPlayer's accessor method used to determine whether the music object is stopped
     * @return true if stopped, otherwise false
     */
    @Override
    public boolean isStopped() {
        return !isPrepared;
    }

    /**
     * Pauses the music object, using the {@link MediaPlayer} object to save the time-code for when the {@link Music#play()} is next called
     */
    @Override
    public void pause() {
        if (this.mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }


    /**
     * Starts the music object playing via the {@link MediaPlayer} object, allowing for new plays and resuming
     */
    @Override
    public void play() {
        if (this.mediaPlayer.isPlaying())
            return;

        try {
            synchronized (this) {
                if (!isPrepared)
                    mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets whether the song will loop after it is done playing
     * @param isLooping boolean used to determine whether the loop will occur. True for looping, False for not.
     */
    @Override
    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

    /**
     * Sets the playback volume of the music.
     * @param volume float value used to specify volume
     */
    @Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }

    /**
     * Stops the music object playing, using the {@link MediaPlayer} object, resetting the preparedness field
     */
    @Override
    public void stop() {
         if (this.mediaPlayer.isPlaying() == true){
        this.mediaPlayer.stop();
        
       synchronized (this) {
           isPrepared = false;
        }}
    }

    /**
     * Sets the preparedness field back to false after the {@link MediaPlayer} object has finished
     * @param player
     */
    @Override
    public void onCompletion(MediaPlayer player) {
        synchronized (this) {
            isPrepared = false;
        }
    }

    /**
     * Sets the playhead back to the beginning of the song by setting the seek method to 0
     */
    @Override
    public void seekBegin() {
        mediaPlayer.seekTo(0);
        
    }

    /**
     * Sets preparedness to true, meaning music is ready to play
     * @param player
     */
    @Override
    public void onPrepared(MediaPlayer player) {
        // TODO Auto-generated method stub
         synchronized (this) {
               isPrepared = true;
            }
        
    }

    /**
     * Method called to indicate the end of a seek operation
     * @param player
     */
    @Override
    public void onSeekComplete(MediaPlayer player) {
        // TODO Auto-generated method stub
        //are these methods unfinished? Hrm... we may need to write these later
    }

    /**
     * Resizes video, although this isn't really needed for audio
     * @param player player object
     * @param width width of new video
     * @param height height of new video
     */
    @Override
    public void onVideoSizeChanged(MediaPlayer player, int width, int height) {
        // TODO Auto-generated method stub
        
    }
}
