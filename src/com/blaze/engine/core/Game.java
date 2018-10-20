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
package com.blaze.engine.core;

import com.blaze.engine.rendering.RenderingEngine;
import java.util.ArrayList;

public abstract class Game {

    private GameObject m_root;

    public void Init() {
    }

    public void Input(float delta) {
        GetRootObject().InputAll(delta);
    }

    public void Update(float delta) {
        GetRootObject().UpdateAll(delta);
    }

    public void Render(RenderingEngine renderingEngine) {
        renderingEngine.Render(GetRootObject());
    }

    public GameObject GetObject(String name) {
        GameObject temp = new GameObject();
        ArrayList<GameObject> list = GetRootObject().GetAllAttached();
        for (int i = 0; i < list.size(); i++) {
            if (name == list.get(i).getName()) {
                temp = list.get(i);
                break;
            }
        }
        return temp;
    }

    public ArrayList<GameObject> GetGameObjects() {
        ArrayList<GameObject> list = GetRootObject().GetAllAttached();
        return list;
    }

    public GameObject GetObject(int index) {
        ArrayList<GameObject> list = GetRootObject().GetAllAttached();
        return list.get(index);
    }

    public void AddObject(GameObject object) {
        GetRootObject().AddChild(object);
    }

    public GameObject GetRootObject() {
        if (m_root == null) {
            m_root = new GameObject();
        }

        return m_root;
    }

    public void SetEngine(CoreEngine engine) {
        GetRootObject().SetEngine(engine);
    }
}
