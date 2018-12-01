package com.blaze.engine.audio;

/**
 * A sound that can be played through OpenAL
 *
 * @author Kevin Glass
 */
public class Sound_1 {

    /**
     * The store from which this sound was loaded
     */
    private SoundLoader store;
    /**
     * The buffer containing the sound
     */
    private int buffer;

    /**
     * Create a new sound
     *
     * @param store The sound store from which the sound was created
     * @param buffer The buffer containing the sound data
     */
    Sound_1(SoundLoader store, int buffer) {
        this.store = store;
        this.buffer = buffer;
    }

    /**
     * Play this sound as a sound effect
     *
     * @param pitch The pitch of the play back
     * @param gain The gain of the play back
     */
    public void play(float pitch, float gain) {
        store.playAsSound(buffer, pitch, gain);
    }
}
