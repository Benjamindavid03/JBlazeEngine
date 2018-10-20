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
package com.blaze;

import com.blaze.editor.GameEditor;
import com.blaze.engine.core.CoreEngine;
import com.blaze.game.CustomGame;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        //set the system property to load the dll file for running.
        System.setProperty("org.lwjgl.librarypath", new File("libraries").getAbsolutePath());
        CoreEngine engine = new CoreEngine(1024, 768, 60, new CustomGame());
        GameEditor.engine = engine;

        engine.CreateWindow("3D Game Engine");
        //Game Engine editor
        /*GameEditor f = new GameEditor();
        f.setVisible(true);

        engine.CreateWindow("3D Game Engine", f.GameView);
         */
        engine.Start();
    }
}
