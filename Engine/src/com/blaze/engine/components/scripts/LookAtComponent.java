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
package com.blaze.engine.components.scripts;

import com.blaze.engine.components.GameComponent;
import com.blaze.engine.core.Math.Quaternion;
import com.blaze.engine.core.Math.Vector3f;
import com.blaze.engine.rendering.RenderingEngine;
import com.blaze.engine.rendering.Shader;

public class LookAtComponent extends GameComponent {

    private RenderingEngine m_renderingEngine;

    @Override
    public void Update(float delta) {
        if (m_renderingEngine != null) {
            Quaternion newRot = GetTransform().GetLookAtRotation(m_renderingEngine.GetMainCamera().GetTransform().GetTransformedPos(),
                    new Vector3f(0, 1, 0));
            //GetTransform().GetRot().GetUp());

            GetTransform().SetRotation(GetTransform().GetRotation().NLerp(newRot, delta * 5.0f, true));
            //GetTransform().SetRot(GetTransform().GetRot().SLerp(newRot, delta * 5.0f, true));
        }
    }

    @Override
    public void Render(Shader shader, RenderingEngine renderingEngine) {
        this.m_renderingEngine = renderingEngine;
    }
}
