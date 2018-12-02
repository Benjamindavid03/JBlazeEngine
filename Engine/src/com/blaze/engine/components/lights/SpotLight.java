package com.blaze.engine.components.lights;

import com.blaze.engine.core.Math.Vector3f;
import com.blaze.engine.core.ObjectType;
import com.blaze.engine.rendering.Attenuation;
import com.blaze.engine.rendering.Shader;

public class SpotLight extends PointLight {

    public ObjectType type = ObjectType.LIGHT;
    private float m_cutoff;

    public SpotLight(Vector3f color, float intensity, Attenuation attenuation, float cutoff) {
        super(color, intensity, attenuation);
        this.m_cutoff = cutoff;

        SetShader(new Shader("forward-spot"));
    }

    public Vector3f GetDirection() {
        return GetTransform().GetTransformedRot().GetForward();
    }

    public float GetCutoff() {
        return m_cutoff;
    }

    public void SetCutoff(float cutoff) {
        this.m_cutoff = cutoff;
    }
}
