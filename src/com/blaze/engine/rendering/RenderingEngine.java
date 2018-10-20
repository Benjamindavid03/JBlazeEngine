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
package com.blaze.engine.rendering;

import com.blaze.engine.components.camera.Camera;
import com.blaze.engine.components.lights.BaseLight;
import com.blaze.engine.core.GameObject;
import com.blaze.engine.core.Transform;
import com.blaze.engine.core.Vector3f;
import com.blaze.engine.rendering.resourceManagement.MappedValues;
import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_VERSION;

public class RenderingEngine extends MappedValues {

    private HashMap<String, Integer> m_samplerMap;
    private ArrayList<BaseLight> m_lights;
    private BaseLight m_activeLight;

    private Shader m_forwardAmbient;
    private Camera m_mainCamera;

    public RenderingEngine() {
        super();
        m_lights = new ArrayList<BaseLight>();
        m_samplerMap = new HashMap<String, Integer>();
        m_samplerMap.put("diffuse", 0);
        m_samplerMap.put("normalMap", 1);
        m_samplerMap.put("dispMap", 2);

        AddVector3f("ambient", new Vector3f(0.1f, 0.1f, 0.1f));

        m_forwardAmbient = new Shader("forward-ambient");

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        GL11.glFrontFace(GL11.GL_CW);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        //
        // glEnable(GL_DEPTH_CLAMP);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public void UpdateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType) {
        throw new IllegalArgumentException(uniformType + " is not a supported type in RenderingEngine");
    }

    public void Render(GameObject object) {
        if (GetMainCamera() == null) {
            System.err.println("Error! Main camera not found. This is very very big bug, and game will crash.");
        }
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        object.RenderAll(m_forwardAmbient, this);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glDepthMask(false);
        GL11.glDepthFunc(GL11.GL_EQUAL);

        for (BaseLight light : m_lights) {
            m_activeLight = light;
            object.RenderAll(light.GetShader(), this);
        }

        GL11.glDepthFunc(GL11.GL_LESS);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static String GetOpenGLVersion() {
        return GL11.glGetString(GL_VERSION);
    }

    public void AddLight(BaseLight light) {
        m_lights.add(light);
    }

    public void AddCamera(Camera camera) {
        m_mainCamera = camera;
    }

    public int GetSamplerSlot(String samplerName) {
        return m_samplerMap.get(samplerName);
    }

    public BaseLight GetActiveLight() {
        return m_activeLight;
    }

    public Camera GetMainCamera() {
        return m_mainCamera;
    }

    public void SetMainCamera(Camera mainCamera) {
        this.m_mainCamera = mainCamera;
    }
}
