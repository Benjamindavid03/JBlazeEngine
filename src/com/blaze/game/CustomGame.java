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
package com.blaze.game;

import com.blaze.engine.audio.AudioMaster;
import com.blaze.engine.audio.AudioSource;
import com.blaze.engine.components.camera.Camera;
import com.blaze.engine.components.camera.FreeLook;
import com.blaze.engine.components.camera.FreeMove;
import com.blaze.engine.components.gameobjects.DefaultGameObjects;
import com.blaze.engine.components.gameobjects.LookAtComponent;
import com.blaze.engine.components.gameobjects.MeshRenderer;
import com.blaze.engine.components.input.RotateObject;
import com.blaze.engine.components.lights.DirectionalLight;
import com.blaze.engine.components.lights.PointLight;
import com.blaze.engine.components.physics.Rigidbody;
import com.blaze.engine.core.Game;
import com.blaze.engine.core.GameObject;
import com.blaze.engine.core.Matrix4f;
import com.blaze.engine.core.Quaternion;
import com.blaze.engine.core.Vector3f;
import com.blaze.engine.rendering.Attenuation;
import com.blaze.engine.rendering.Material;
import com.blaze.engine.rendering.Mesh;
import com.blaze.engine.rendering.Texture;
import com.blaze.engine.rendering.Window;

public class CustomGame extends Game {

    public GameObject sun;
    public Vector3f skyColor;
    public Vector3f sunColor;
    public Vector3f sunRotationVector;
    public float sunIntensity = 0.7f;
    public int buffer;
    AudioSource source;

    private void setupSun() {
        skyColor = new Vector3f(1, 1, 1);
        sunColor = new Vector3f(1, 1, 1);
        sun = new GameObject();
        DirectionalLight directionalLight = new DirectionalLight(sunColor, sunIntensity);
        sun.AddComponent(directionalLight);
        sun.AddComponent(new RotateObject(sunIntensity));
        sunRotationVector = new Vector3f(1, 0, 0);
        directionalLight.GetTransform().SetRotation(new Quaternion(sunRotationVector, (float) Math.toRadians(-45)));
        AddObject(sun);
    }

    private void LoadGameMesh(String name, String meshnName, String textureName) {
        Mesh mesh = new Mesh(meshnName);
        Material material = new Material(new Texture(textureName));
        MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
        GameObject object = new GameObject(name);
        object.AddComponent(meshRenderer);
        object.GetTransform().GetPosition().Set(0, -1, 5);
        AddObject(object);
    }

    private void LoadRotatableGameMesh(String name, String meshnName, String textureName) {
        Mesh mesh = new Mesh(meshnName);
        Material material = new Material(new Texture(textureName));
        MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
        GameObject object = new GameObject(name);
        object.AddComponent(meshRenderer);
        object.AddComponent(new RotateObject(0.1f));
        object.GetTransform().GetPosition().Set(0f, 0.4f, 5f);
        AddObject(object);
    }

    private void LoadPhysicsObject(String name, String meshnName, String textureName) {
        Mesh mesh = new Mesh(meshnName);
        Material material = new Material(new Texture(textureName));
        MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
        GameObject object = new GameObject(name);
        object.AddComponent(meshRenderer);
        object.AddComponent(new Rigidbody());
        object.GetTransform().GetPosition().Set(0f, 0.9f, 3f);
        AddObject(object);
    }

    private void loadMainCamera() {
        Camera cam = new Camera(new Matrix4f().InitPerspective((float) Math.toRadians(70.0f),
                (float) Window.GetWidth() / (float) Window.GetHeight(), 0.01f, 1000.0f));
        FreeLook freelook = new FreeLook(0.2f);
        FreeMove freemove = new FreeMove(10.0f);
        GameObject camera = new GameObject("MainCamera");
        camera.AddComponent(cam);
        camera.AddComponent(freelook);
        camera.AddComponent(freemove);
        AddObject(camera);
    }

    public void SetupLighting() {
        GameObject obj = new GameObject("PointLight");
        obj.AddComponent(new PointLight(new Vector3f(0.3f, 0.2f, 0.4f), sunIntensity, new Attenuation(0.2f, 0.7f, 0.2f)));
        obj.AddComponent(new LookAtComponent());
        AddObject(obj);
    }

    public void LoadGameElements() {
        setupSun();
        SetupLighting();
        LoadGameMesh("plane", "plane3.obj", "white.png");
        LoadRotatableGameMesh("monkey", "monkey3.obj", "bricks.jpg");
        //LoadPhysicsObject("monkey", "monkey3.obj", "bricks.jpg");
        loadMainCamera();
        DefaultGameObjects.createCube(this);
    }

    public void handleInput() {

    }

    public void LoadGameSounds() {
        AudioMaster.init();
        AudioMaster.setListener();
        String path = "../res/audio/" + "JetEngine.wav";
        buffer = AudioMaster.loadSound(path);
        source = new AudioSource();
        source.play(buffer);
    }

    public void setupPhysics() {

    }

    @Override
    public void Init() {
        LoadGameElements();
        // LoadGameSounds();
    }

}
