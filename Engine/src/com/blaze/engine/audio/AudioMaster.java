/*
 * Copyright (C) 2018 Benjamin Fredrick David. H
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blaze.engine.audio;

import com.blaze.engine.core.Math.Vector3f;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

/**
 *
 * @author Ben
 */
public class AudioMaster {

    public static ArrayList<Integer> buffers;

    public static void init() {
        try {
            buffers = new ArrayList<Integer>();
            AL.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(AudioMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setListener() {
        AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

    public static void setListener(Vector3f position) {
        AL10.alListener3f(AL10.AL_POSITION, position.GetX(), position.GetY(), position.GetZ());
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

    public static int loadSound(String file) {
        int buffer = AL10.alGenBuffers();
        buffers.add(buffer);
        //Loads the wave file from your file system
        java.io.FileInputStream fin = null;
        try {
            fin = new java.io.FileInputStream(file);
        } catch (java.io.FileNotFoundException ex) {
            ex.printStackTrace();
            return AL10.AL_FALSE;
        }
        WaveData waveFile = WaveData.create(new BufferedInputStream(fin));
        AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
        try {
            waveFile.dispose();
            fin.close();
        } catch (java.io.IOException ex) {
        }
        /*
        String fileName = "Player-jump2.wav";
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];
        if (ext.equals("wav")) {
            org.newdawn.slick.openal.WaveData waveFile = org.newdawn.slick.openal.WaveData.create(file);
            AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
            waveFile.dispose();
        } else {
            System.out.println("Format not supported!");
        }
         */
        return buffer;
    }

    public static void cleanUp() {
        for (int buffer : buffers) {
            AL10.alDeleteBuffers(buffer);
        }
        AL.destroy();
    }
}
