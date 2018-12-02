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
