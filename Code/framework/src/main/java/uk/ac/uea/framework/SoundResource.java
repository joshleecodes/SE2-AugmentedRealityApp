package uk.ac.uea.framework;

/**
 * Class that creates a sound resource given an Audio object and creates a sound object that can be played and stopped on command.
 * Created by Jack L. Clements on 09/10/2015.
 */
import android.app.Activity;

import uk.ac.uea.framework.implementation.AndroidAudio;

public class SoundResource {
    /**Audio object, used to store activity read in by constructor */
    private Audio myAudio;
    /**Sound object, used to store audio object converted to sound */
    private Sound mySound;

    /**
     * Constructor for SoundResource that initialises the Audio field ready for loading
     * @param act the activity that gets converted into the Audio object
     */
    public SoundResource(Activity act){
        myAudio = new AndroidAudio(act);
    }

    /**
     * Creates a Sound object by referencing the file path of the resource used and the Audio object's create method.
     * @param resourcePath the file path of the sound needed to be played
     */
    public void load(String resourcePath){
        mySound = myAudio.createSound(resourcePath);
    }

    /**
     * Plays the Sound resource loaded in the {@link SoundResource#mySound} field at the volume 0.9, this method can be overridden/extended in subclasses to specify volume.
     */
    public void play(){
        mySound.play((float)0.9);
    }

    /**
     * Stops the Sound resource {@link SoundResource#mySound} from playing if it is currently playing
     */
    public void stop(){
        mySound.stop();
    }
}
