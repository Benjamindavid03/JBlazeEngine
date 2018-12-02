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
