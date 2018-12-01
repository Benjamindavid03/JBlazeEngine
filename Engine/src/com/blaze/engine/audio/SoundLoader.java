package com.blaze.engine.audio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.HashMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

/**
 * Responsible for holding and playing the sounds used in the game.
 *
 * @author Kevin Glass
 */
public class SoundLoader {

    /**
     * The single instance of this class
     */
    private static final SoundLoader store = new SoundLoader();

    /**
     * True if sound effects are turned on
     */
    private boolean sounds;
    /**
     * True if sound initialisation succeeded
     */
    private boolean soundWorks;
    /**
     * The number of sound sources enabled - default 8
     */
    private int sourceCount;
    /**
     * The map of references to IDs of previously loaded sounds
     */
    private HashMap loaded = new HashMap();
    /**
     * The OpenGL AL sound sources in use
     */
    private IntBuffer sources;
    /**
     * The next source to be used for sound effects
     */
    private int nextSource;
    /**
     * True if the sound system has been initialise
     */
    private boolean inited = false;

    /**
     * Create a new sound store
     */
    private SoundLoader() {
    }

    /**
     * Indicate whether sound effects should be played
     *
     * @param sounds True if sound effects should be played
     */
    public void setSoundsOn(boolean sounds) {
        if (soundWorks) {
            this.sounds = sounds;
        }
    }

    /**
     * Check if sound effects are currently enabled
     *
     * @return True if sound effects are currently enabled
     */
    public boolean soundsOn() {
        return sounds;
    }

    /**
     * Initialise the sound effects stored. This must be called before anything
     * else will work
     */
    public void init() {
        inited = true;
        try {
            AL.create();
            soundWorks = true;
            sounds = true;
        } catch (Exception e) {
            e.printStackTrace();
            soundWorks = false;
            sounds = false;
        }

        if (soundWorks) {
            sourceCount = 8;
            sources = BufferUtils.createIntBuffer(8);
            AL10.alGenSources(sources);

            if (AL10.alGetError() != AL10.AL_NO_ERROR) {
                sounds = false;
                soundWorks = false;
            }
        }
    }

    /**
     * Play the specified buffer as a sound effect with the specified pitch and
     * gain.
     *
     * @param buffer The ID of the buffer to play
     * @param pitch The pitch to play at
     * @param gain The gain to play at
     */
    void playAsSound(int buffer, float pitch, float gain) {
        if (soundWorks) {
            if (sounds) {
                nextSource++;
                if (nextSource > 7) {
                    nextSource = 1;
                }
                AL10.alSourceStop(sources.get(nextSource));

                AL10.alSourcei(sources.get(nextSource), AL10.AL_BUFFER, buffer);
                AL10.alSourcef(sources.get(nextSource), AL10.AL_PITCH, pitch);
                AL10.alSourcef(sources.get(nextSource), AL10.AL_GAIN, gain);

                AL10.alSourcePlay(sources.get(nextSource));
            }
        }
    }

    /**
     * Get the Sound based on a specified OGG file
     *
     * @param ref The reference to the OGG file in the classpath
     * @return The Sound read from the OGG file
     * @throws IOException Indicates a failure to load the OGG
     */
    public Sound_1 getOgg(String ref) throws IOException {
        ref = "./res/audio/" + ref;
        if (!soundWorks) {
            return new Sound_1(this, 0);
        }

        if (!inited) {
            throw new RuntimeException("Can't load sounds until SoundLoader is init()");
        }
        int buffer = -1;

        if (loaded.get(ref) != null) {
            buffer = ((Integer) loaded.get(ref)).intValue();
        } else {
            System.out.println("Loading: " + ref);
            try {
                IntBuffer buf = BufferUtils.createIntBuffer(1);

                InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(ref);

                OggDecoder decoder = new OggDecoder();
                OggData ogg = decoder.getData(in);

                AL10.alGenBuffers(buf);
                AL10.alBufferData(buf.get(0), ogg.channels > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16, ogg.data, ogg.rate);

                loaded.put(ref, new Integer(buf.get(0)));

                buffer = buf.get(0);
            } catch (Exception e) {
                e.printStackTrace();
                Sys.alert("Error", "Failed to load: " + ref + " - " + e.getLocalizedMessage());
                System.exit(0);
            }
        }

        if (buffer == -1) {
            throw new IOException("Unable to load: " + ref);
        }

        return new Sound_1(this, buffer);
    }

    /**
     * Get the single instance of this class
     *
     * @return The single instnace of this class
     */
    public static SoundLoader get() {
        return store;
    }
}
