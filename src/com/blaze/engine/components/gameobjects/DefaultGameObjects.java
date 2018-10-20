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
package com.blaze.engine.components.gameobjects;

import com.blaze.engine.core.Game;
import com.blaze.engine.core.GameObject;
import com.blaze.engine.rendering.Material;
import com.blaze.engine.rendering.Mesh;
import com.blaze.engine.rendering.Texture;

/**
 *
 * @author Ben
 */
public class DefaultGameObjects {

    public static void createCube(Game g) {
        Material material = new Material(new Texture("bricks2.jpg"), 1, 8,
                new Texture("bricks2_normal.png"), new Texture("bricks2_disp.jpg"), 0.04f, -1.0f);

        GameObject temp = new GameObject("cube");
        MeshRenderer renderer = new MeshRenderer(new Mesh("cube.obj"), material);
        temp.AddComponent(renderer);
        g.AddObject(temp);
    }

    public static void createQuad(Game g) {
        GameObject temp = new GameObject("quad").AddComponent(new MeshRenderer(new Mesh("quad.obj"), new Material(new Texture("bricks.png"))));
        g.AddObject(temp);
    }
}
