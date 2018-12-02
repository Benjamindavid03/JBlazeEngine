package com.blaze.engine.audio;

import org.lwjgl.openal.AL10;

/**
 *
 * @author Ben
 */
public class AudioSource {

    private int sourceId;

    public AudioSource() {
        sourceId = AL10.alGenBuffers();
        AL10.alSourcef(sourceId, AL10.AL_GAIN, 1);
        AL10.alSourcef(sourceId, AL10.AL_PITCH, 1);
        AL10.alSource3f(sourceId, AL10.AL_POSITION, 0, 0, 0);
    }

    public void play(int buffer) {
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
        AL10.alSourcePlay(sourceId);
    }

    public void stop(int buffer) {
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
        AL10.alSourceStop(sourceId);
    }

    public void delete() {
        AL10.alDeleteSources(sourceId);
    }
}
