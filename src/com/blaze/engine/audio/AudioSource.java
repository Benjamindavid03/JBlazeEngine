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
