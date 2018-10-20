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
package com.blaze.engine.components;

import com.blaze.engine.core.CoreEngine;
import com.blaze.engine.core.GameObject;
import com.blaze.engine.core.Transform;
import com.blaze.engine.rendering.RenderingEngine;
import com.blaze.engine.rendering.Shader;

public abstract class GameComponent {

    private GameObject m_parent;

    public void Input(float delta) {
    }

    public void Update(float delta) {
    }

    public void Render(Shader shader, RenderingEngine renderingEngine) {
    }

    public void SetParent(GameObject parent) {
        this.m_parent = parent;
    }

    public Transform GetTransform() {
        return m_parent.GetTransform();
    }

    public void AddToEngine(CoreEngine engine) {
    }
}
