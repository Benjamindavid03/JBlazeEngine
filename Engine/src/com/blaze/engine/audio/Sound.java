/*
 * Copyright 2018 Ben.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blaze.engine.audio;

import java.io.BufferedInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Sound {

    /**
     * Buffers hold sound data.
     */
    IntBuffer buffer = BufferUtils.createIntBuffer(1);

    /**
     * Sources are points emitting sound.
     */
    IntBuffer source = BufferUtils.createIntBuffer(1);

    /**
     * Position of the source sound.
     */
    FloatBuffer sourcePos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();

    /**
     * Velocity of the source sound.
     */
    FloatBuffer sourceVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();

    /**
     * Position of the listener.
     */
    FloatBuffer listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();

    /**
     * Velocity of the listener.
     */
    FloatBuffer listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();

    /**
     * Orientation of the listener. (first 3 elements are "at", second 3 are
     * "up")
     */
    FloatBuffer listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[]{0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f}).rewind();

    /**
     * boolean LoadALData()
     *
     * This function will load our sample data from the disk using the Alut
     * utility and send the data into OpenAL as a buffer. A source is then also
     * created to play that buffer.
     */
    int loadALData() {
        // Load wav data into a buffer.
        AL10.alGenBuffers(buffer);

        if (AL10.alGetError() != AL10.AL_NO_ERROR) {
            return AL10.AL_FALSE;
        }

        //Loads the wave file from your file system
        java.io.FileInputStream fin = null;
        try {
            fin = new java.io.FileInputStream("/home/james/lib/lwjgl-2.9.2/res/Footsteps.wav");
        } catch (java.io.FileNotFoundException ex) {
            ex.printStackTrace();
            return AL10.AL_FALSE;
        }
        WaveData waveFile = WaveData.create(new BufferedInputStream(fin));
        try {
            fin.close();
        } catch (java.io.IOException ex) {
        }

        //Loads the wave file from this class's package in your classpath
//    WaveData waveFile = WaveData.create("FancyPants.wav");
        AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();

        // Bind the buffer with the source.
        AL10.alGenSources(source);

        if (AL10.alGetError() != AL10.AL_NO_ERROR) {
            return AL10.AL_FALSE;
        }

        AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buffer.get(0));
        AL10.alSourcef(source.get(0), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(source.get(0), AL10.AL_GAIN, 1.0f);
        AL10.alSource(source.get(0), AL10.AL_POSITION, sourcePos);
        AL10.alSource(source.get(0), AL10.AL_VELOCITY, sourceVel);

        // Do another error check and return.
        if (AL10.alGetError() == AL10.AL_NO_ERROR) {
            return AL10.AL_TRUE;
        }

        return AL10.AL_FALSE;
    }

    /**
     * void setListenerValues()
     *
     * We already defined certain values for the Listener, but we need to tell
     * OpenAL to use that data. This function does just that.
     */
    void setListenerValues() {
        AL10.alListener(AL10.AL_POSITION, listenerPos);
        AL10.alListener(AL10.AL_VELOCITY, listenerVel);
        AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
    }

    /**
     * void killALData()
     *
     * We have allocated memory for our buffers and sources which needs to be
     * returned to the system. This function frees that memory.
     */
    void killALData() {
        AL10.alDeleteSources(source);
        AL10.alDeleteBuffers(buffer);
    }

    public static void main(String[] args) {
        new Sound().execute();
    }

    public void execute() {
        // Initialize OpenAL and clear the error bit.
        AL con = null;
        //       try {
        //con = AL.create();
//    	  con = AL.getCurrentContext();
//	} catch (LWJGLException e) {
//		e.printStackTrace();
//	}
// Load the wav data.
        if (loadALData() == AL10.AL_FALSE) {
            System.out.println("Error loading data.");
            return;
        }
        setListenerValues();
// Loop.
        System.out.println("OpenAL Tutorial 1 - Single Static Source");
        System.out.println("[Menu]");
        System.out.println("p - Play the sample.");
        System.out.println("s - Stop the sample.");
        System.out.println("h - Pause the sample.");
        System.out.println("q - Quit the program.");
        char c = ' ';
        Scanner stdin = new Scanner(System.in);
        while (c != 'q') {
            try {
                System.out.print("Input: ");
                c = (char) stdin.nextLine().charAt(0);
            } catch (Exception ex) {
                c = 'q';
            }

            switch (c) {
                // Pressing 'p' will begin playing the sample.
                case 'p':
                    AL10.alSourcePlay(source.get(0));
                    break;

                // Pressing 's' will stop the sample from playing.
                case 's':
                    AL10.alSourceStop(source.get(0));
                    break;

                // Pressing 'h' will pause the sample.
                case 'h':
                    AL10.alSourcePause(source.get(0));
                    break;
            };
        }
        killALData();
        AL.destroy();
    }
}