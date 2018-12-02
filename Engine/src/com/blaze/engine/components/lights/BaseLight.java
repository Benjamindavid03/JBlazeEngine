package com.blaze.engine.components.lights;

import com.blaze.engine.components.GameComponent;
import com.blaze.engine.core.CoreEngine;
import com.blaze.engine.core.Math.Vector3f;
import com.blaze.engine.core.ObjectType;
import com.blaze.engine.rendering.Shader;

/**
 *
 * @author Ben
 */
public class BaseLight extends GameComponent {

    public ObjectType type = ObjectType.LIGHT;
    private Vector3f m_color;
    private float m_intensity;
    private Shader m_shader;

    public BaseLight(Vector3f color, float intensity) {
        this.m_color = color;
        this.m_intensity = intensity;
    }

    @Override
    public void AddToEngine(CoreEngine engine) {
        engine.GetRenderingEngine().AddLight(this);
    }

    public void SetShader(Shader shader) {
        this.m_shader = shader;
    }

    public Shader GetShader() {
        return m_shader;
    }

    public Vector3f GetColor() {
        return m_color;
    }

    public void SetColor(Vector3f color) {
        this.m_color = color;
    }

    public float GetIntensity() {
        return m_intensity;
    }

    public void SetIntensity(float intensity) {
        this.m_intensity = intensity;
    }
}
