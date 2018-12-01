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
package com.blaze.game;

import com.blaze.engine.core.Game;
import com.blaze.engine.core.Scene;

public class PlainGame extends Game {

    com.blaze.engine.core.Scene scene = new Scene();

    void inputExState() {
        /*
        while (Keyboard.next()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                if (state == State.INTRO) {
                    state = State.MAIN_MENU;
                } else if (state == State.MAIN_MENU) {
                    state = State.GAME;
                } else if (state == State.GAME) {
                    state = State.INTRO;
                }
            }
        }
         */
    }

    @Override
    public void Update(float delta) {
        super.Update(delta);
    }

    public void LoadGameElements() {
        com.blaze.engine.utils.SceneManagement.LoadMeshintoGame(this, "plane", "plane3.obj", "white.png");
        com.blaze.engine.utils.SceneManagement.createCube(this);
        scene.setupDefaultSun(this);
        scene.loadFreeLookandMoveMainCamera(this);

        /*
        GameObject obj = new GameObject("Terrain");
        obj.AddComponent(new Terrain());
        AddGameObject(obj);
         */
    }

    @Override
    public void Init() {
        LoadGameElements();
    }
}
