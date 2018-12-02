package com.blaze.engine.rendering;

import com.blaze.engine.core.Math.Vector3f;

public class Attenuation extends Vector3f {

    public Attenuation(float constant, float linear, float exponent) {
        super(constant, linear, exponent);
    }

    public float GetConstant() {
        return GetX();
    }

    public float GetLinear() {
        return GetY();
    }

    public float GetExponent() {
        return GetZ();
    }
}
