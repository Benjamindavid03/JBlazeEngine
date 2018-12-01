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

import com.blaze.engine.components.scripts.LookAtComponent;
import com.blaze.engine.components.camera.Camera;
import com.blaze.engine.components.camera.FreeLook;
import com.blaze.engine.components.camera.FreeMove;
import com.blaze.engine.components.environments.SkyBox;
import com.blaze.engine.components.rendering.MeshRenderer;
import com.blaze.engine.components.lights.DirectionalLight;
import com.blaze.engine.components.lights.PointLight;
import com.blaze.engine.components.lights.SpotLight;
import com.blaze.engine.core.Game;
import com.blaze.engine.core.GameObject;
import com.blaze.engine.core.Math.Matrix4f;
import com.blaze.engine.core.Math.Quaternion;
import com.blaze.engine.core.Math.Vector3f;
import com.blaze.engine.rendering.Attenuation;
import com.blaze.engine.rendering.Material;
import com.blaze.engine.rendering.Mesh;
import com.blaze.engine.rendering.RenderingEngine;
import com.blaze.engine.rendering.Shader;
import com.blaze.engine.rendering.Texture;
import com.blaze.engine.rendering.Window;

public class TestGame extends Game {

    public void createLight() {
        Vector3f color = new Vector3f(1, 1, 1);

        GameObject directionalLightObject = new GameObject();
        DirectionalLight directionalLight = new DirectionalLight(color, 0.7f);

        directionalLightObject.AddComponent(directionalLight);
        directionalLight.GetTransform().SetRotation(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-45)));

        AddGameObject(directionalLightObject);
    }

    public void Init() {
        createLight();
        Mesh mesh = new Mesh("plane3.obj");

        Material basicMat = new Material(new Texture("bricks.jpg"));

        Material material2 = new Material(new Texture("bricks.jpg"), 1, 8,
                new Texture("bricks_normal.jpg"), new Texture("bricks_disp.png"), 0.03f, -0.5f);

        Material material = new Material(new Texture("bricks2.jpg"), 1, 8,
                new Texture("bricks2_normal.png"), new Texture("bricks2_disp.jpg"), 0.04f, -1.0f);

        Mesh tempMesh = new Mesh("monkey3.obj");

        MeshRenderer meshRenderer = new MeshRenderer(mesh, basicMat);

        GameObject planeObject = new GameObject("plane");
        planeObject.AddComponent(meshRenderer);
        planeObject.GetTransform().GetPosition().Set(0, -1, 5);

        GameObject pointLightObject = new GameObject("PointLight");
        pointLightObject.AddComponent(new PointLight(new Vector3f(0, 1, 0), 0.4f, new Attenuation(0, 0, 1)));

        SpotLight spotLight = new SpotLight(new Vector3f(0, 1, 1), 0.4f,
                new Attenuation(0, 0, 0.4f), 0.7f);

        GameObject spotLightObject = new GameObject("SpotLight");
        spotLightObject.AddComponent(spotLight);

        spotLightObject.GetTransform().GetPosition().Set(5, 0, 5);
        spotLightObject.GetTransform().SetRotation(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(90.0f)));

        AddGameObject(planeObject);

        AddGameObject(pointLightObject);
        AddGameObject(spotLightObject);

        GameObject testMesh3 = new GameObject("Monkey2").AddComponent(new LookAtComponent()).AddComponent(new MeshRenderer(tempMesh, material));

        AddGameObject(
                //AddObject(
                new GameObject("MainCamera").AddComponent(new FreeLook(0.2f)).AddComponent(new FreeMove(10.0f))
                        .AddComponent(new Camera(new Matrix4f().InitPerspective((float) Math.toRadians(70.0f),
                                (float) Window.GetWidth() / (float) Window.GetHeight(), 0.01f, 1000.0f))));

        AddGameObject(testMesh3);

        testMesh3.GetTransform().GetPosition().Set(5, 5, 5);
        testMesh3.GetTransform().SetRotation(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(-70.0f)));

        AddGameObject(new GameObject("Monkey").AddComponent(new MeshRenderer(new Mesh("monkey3.obj"), material2)));

        // Setup  SkyBox
        float skyBoxScale = 100.0f;

        //skybox
        GameObject skybox = new GameObject();
        skybox.AddComponent(new SkyBox(this.GetRootObject(), "skybox.obj", "skybox.png"));
        skybox.Render(new Shader("forward-ambient"), new RenderingEngine());
        AddGameObject(skybox);

        //GetRootObject().AddComponent(new BaseLight(new Vector3f(0f, 0f, 0f), 1.0f));
    }

}
