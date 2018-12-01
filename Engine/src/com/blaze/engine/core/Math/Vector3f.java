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
package com.blaze.engine.core.Math;

public class Vector3f {

    public float x;
    public float y;
    public float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float Length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float Max() {
        return Math.max(x, Math.max(y, z));
    }

    public float Dot(Vector3f r) {
        return x * r.GetX() + y * r.GetY() + z * r.GetZ();
    }

    public Vector3f Cross(Vector3f r) {
        float x_ = y * r.GetZ() - z * r.GetY();
        float y_ = z * r.GetX() - x * r.GetZ();
        float z_ = x * r.GetY() - y * r.GetX();

        return new Vector3f(x_, y_, z_);
    }

    public Vector3f Normalized() {
        float length = Length();

        return new Vector3f(x / length, y / length, z / length);
    }

    public Vector3f Rotate(Vector3f axis, float angle) {
        float sinAngle = (float) Math.sin(-angle);
        float cosAngle = (float) Math.cos(-angle);

        return this.Cross(axis.Mul(sinAngle)).Add( //Rotation on local X
                (this.Mul(cosAngle)).Add( //Rotation on local Z
                        axis.Mul(this.Dot(axis.Mul(1 - cosAngle))))); //Rotation on local Y
    }

    public Vector3f Rotate(Quaternion rotation) {
        Quaternion conjugate = rotation.Conjugate();

        Quaternion w = rotation.Mul(this).Mul(conjugate);

        return new Vector3f(w.GetX(), w.GetY(), w.GetZ());
    }

    public Vector3f Lerp(Vector3f dest, float lerpFactor) {
        return dest.Sub(this).Mul(lerpFactor).Add(this);
    }

    public Vector3f Add(Vector3f r) {
        return new Vector3f(x + r.GetX(), y + r.GetY(), z + r.GetZ());
    }

    public Vector3f Add(float r) {
        return new Vector3f(x + r, y + r, z + r);
    }

    public Vector3f Sub(Vector3f r) {
        return new Vector3f(x - r.GetX(), y - r.GetY(), z - r.GetZ());
    }

    public Vector3f Sub(float r) {
        return new Vector3f(x - r, y - r, z - r);
    }

    public Vector3f Mul(Vector3f r) {
        return new Vector3f(x * r.GetX(), y * r.GetY(), z * r.GetZ());
    }

    public Vector3f Mul(float r) {
        return new Vector3f(x * r, y * r, z * r);
    }

    public Vector3f Div(Vector3f r) {
        return new Vector3f(x / r.GetX(), y / r.GetY(), z / r.GetZ());
    }

    public Vector3f Div(float r) {
        return new Vector3f(x / r, y / r, z / r);
    }

    public Vector3f Abs() {
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public String toString() {
        return "(" + x + " " + y + " " + z + ")";
    }

    public Vector2f GetXY() {
        return new Vector2f(x, y);
    }

    public Vector2f GetYZ() {
        return new Vector2f(y, z);
    }

    public Vector2f GetZX() {
        return new Vector2f(z, x);
    }

    public Vector2f GetYX() {
        return new Vector2f(y, x);
    }

    public Vector2f GetZY() {
        return new Vector2f(z, y);
    }

    public Vector2f GetXZ() {
        return new Vector2f(x, z);
    }

    public Vector3f Set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3f Set(Vector3f r) {
        Set(r.GetX(), r.GetY(), r.GetZ());
        return this;
    }

    public float GetX() {
        return x;
    }

    public void SetX(float x) {
        this.x = x;
    }

    public float GetY() {
        return y;
    }

    public void SetY(float y) {
        this.y = y;
    }

    public float GetZ() {
        return z;
    }

    public void SetZ(float z) {
        this.z = z;
    }

    public boolean equals(Vector3f r) {
        return x == r.GetX() && y == r.GetY() && z == r.GetZ();
    }
}
