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
package com.blaze.engine.components.lights;

import com.blaze.engine.core.Math.Vector3f;
import com.blaze.engine.core.ObjectType;
import com.blaze.engine.rendering.Shader;

public class DirectionalLight extends BaseLight {

    public ObjectType type = ObjectType.LIGHT;

    public DirectionalLight(Vector3f color, float intensity) {
        super(color, intensity);

        SetShader(new Shader("forward-directional"));
    }

    public Vector3f GetDirection() {
        return GetTransform().GetTransformedRot().GetForward();
    }
}
