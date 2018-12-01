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
package com.blaze.engine.components.camera;

import com.blaze.engine.components.GameComponent;
import com.blaze.engine.core.ObjectType;
import com.blaze.engine.core.Input;
import com.blaze.engine.core.Math.Vector2f;
import com.blaze.engine.core.Math.Vector3f;
import com.blaze.engine.rendering.Window;

public class FreeLook extends GameComponent {

    public ObjectType type = ObjectType.CAMERA;
    private static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);

    private boolean m_mouseLocked = false;
    private float m_sensitivity;
    private int m_unlockMouseKey;

    public FreeLook(float sensitivity) {
        this(sensitivity, Input.KEY_ESCAPE);
    }

    public FreeLook(float sensitivity, int unlockMouseKey) {
        this.m_sensitivity = sensitivity;
        this.m_unlockMouseKey = unlockMouseKey;
    }

    @Override
    public void Input(float delta) {
        Vector2f centerPosition = new Vector2f(Window.GetWidth() / 2, Window.GetHeight() / 2);

        if (Input.GetKey(m_unlockMouseKey)) {
            Input.SetCursor(true);
            m_mouseLocked = false;
        }
        if (Input.GetMouseDown(0)) {
            Input.SetMousePosition(centerPosition);
            Input.SetCursor(false);
            m_mouseLocked = true;
        }

        if (m_mouseLocked) {
            Vector2f deltaPos = Input.GetMousePosition().Sub(centerPosition);

            boolean rotY = deltaPos.GetX() != 0;
            boolean rotX = deltaPos.GetY() != 0;

            if (rotY) {
                GetTransform().Rotate(Y_AXIS, (float) Math.toRadians(deltaPos.GetX() * m_sensitivity));
            }
            if (rotX) {
                GetTransform().Rotate(GetTransform().GetRotation().GetRight(), (float) Math.toRadians(-deltaPos.GetY() * m_sensitivity));
            }

            if (rotY || rotX) {
                Input.SetMousePosition(centerPosition);
            }
        }
    }
}
