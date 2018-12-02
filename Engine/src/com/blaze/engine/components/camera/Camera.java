package com.blaze.engine.components.camera;

import com.blaze.engine.components.GameComponent;
import com.blaze.engine.core.CoreEngine;
import com.blaze.engine.core.Math.Matrix4f;
import com.blaze.engine.core.Math.Vector3f;
import com.blaze.engine.core.ObjectType;

public class Camera extends GameComponent {

    public ObjectType type = ObjectType.CAMERA;
    private Matrix4f m_projection;

    public Camera(Matrix4f projection) {
        this.m_projection = projection;
    }

    public Matrix4f GetViewProjection() {
        Matrix4f cameraRotation = GetTransform().GetTransformedRot().Conjugate().ToRotationMatrix();
        Vector3f cameraPos = GetTransform().GetTransformedPos().Mul(-1);

        Matrix4f cameraTranslation = new Matrix4f().InitTranslation(cameraPos.GetX(), cameraPos.GetY(), cameraPos.GetZ());

        return m_projection.Mul(cameraRotation.Mul(cameraTranslation));
    }

    @Override
    public void AddToEngine(CoreEngine engine) {
        engine.GetRenderingEngine().AddCamera(this);
    }
}
