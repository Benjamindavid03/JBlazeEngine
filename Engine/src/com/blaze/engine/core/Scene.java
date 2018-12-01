/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blaze.engine.core;

import com.blaze.engine.components.camera.Camera;
import com.blaze.engine.components.camera.FreeLook;
import com.blaze.engine.components.camera.FreeMove;
import com.blaze.engine.components.input.RotateObject;
import com.blaze.engine.components.lights.DirectionalLight;
import com.blaze.engine.core.Math.Matrix4f;
import com.blaze.engine.core.Math.Quaternion;
import com.blaze.engine.core.Math.Vector3f;
import com.blaze.engine.rendering.Window;

/**
 *
 * @author Benjamin Fredrick David.H
 */
public class Scene {
    
    private GameObject sun;
    private Vector3f skyColor;
    private Vector3f sunColor;
    public Vector3f sunRotationVector;
    private float sunIntensity = 0.1f;
    /**
     * @return the sun
     */
    public GameObject getSun() {
        return sun;
    }

    /**
     * @param sun the sun to set
     */
    public void setSun(GameObject sun) {
        this.sun = sun;
    }

    /**
     * @return the skyColor
     */
    public Vector3f getSkyColor() {
        return skyColor;
    }

    /**
     * @param skyColor the skyColor to set
     */
    public void setSkyColor(Vector3f skyColor) {
        this.skyColor = skyColor;
    }

    /**
     * @return the sunColor
     */
    public Vector3f getSunColor() {
        return sunColor;
    }

    /**
     * @param sunColor the sunColor to set
     */
    public void setSunColor(Vector3f sunColor) {
        this.sunColor = sunColor;
    }

    /**
     * @return the sunIntensity
     */
    public float getSunIntensity() {
        return sunIntensity;
    }

    /**
     * @param sunIntensity the sunIntensity to set
     */
    public void setSunIntensity(float sunIntensity) {
        this.sunIntensity = sunIntensity;
    }
      
    public void setupDefaultSun(Game game) {
        setSun(new GameObject());
        setSkyColor(Color.colorFromRGB(25, 34, 23));
        setSunColor(Color.colorFromRGB(1, 1, 2));
        DirectionalLight directionalLight = new DirectionalLight(getSunColor(), getSunIntensity());
        getSun().AddComponent(directionalLight);
        getSun().AddComponent(new RotateObject(getSunIntensity()));
        sunRotationVector = new Vector3f(1, 0, 0);
        directionalLight.GetTransform().SetRotation(new Quaternion(sunRotationVector, (float) Math.toRadians(-20)));
        game.AddGameObject(getSun());
    }
    
    public void loadFreeLookandMoveMainCamera(Game game) {
        Camera cam = new Camera(new Matrix4f().InitPerspective((float) Math.toRadians(70.0f),
                (float) Window.GetWidth() / (float) Window.GetHeight(), 0.01f, 1000.0f));
        FreeLook freelook = new FreeLook(0.2f);
        FreeMove freemove = new FreeMove(10.0f);
        GameObject camera = new GameObject("MainCamera");
        camera.AddComponent(cam);
        camera.AddComponent(freelook);
        camera.AddComponent(freemove);
        game.AddGameObject(camera);
    }
}
