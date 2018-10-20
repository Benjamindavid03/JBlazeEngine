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
package com.blaze.engine.core;

import com.blaze.engine.components.GameComponent;
import com.blaze.engine.core.enums.GameObjectType;
import com.blaze.engine.rendering.RenderingEngine;
import com.blaze.engine.rendering.Shader;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class GameObject {

    /**
     * The type of the game object
     */
    public GameObjectType type = GameObjectType.GAMEOBJECT;
    private String m_name;
    private ArrayList<GameObject> m_children;
    private ArrayList<GameComponent> m_components;
    private Transform m_transform;
    private CoreEngine m_engine;

    public ArrayList<GameComponent> getComponents() {
        return m_components;
    }

    public GameObject() {
        m_name = "Unknown";
        m_children = new ArrayList<GameObject>();
        m_components = new ArrayList<GameComponent>();
        m_transform = new Transform();
        m_engine = null;
    }

    public GameObject(String name) {
        m_name = name;
        m_children = new ArrayList<GameObject>();
        m_components = new ArrayList<GameComponent>();
        m_transform = new Transform();
        m_engine = null;
    }

    public GameObject AddChild(GameObject child, String name) {
        this.m_name = name;
        m_children.add(child);
        child.SetEngine(m_engine);
        child.GetTransform().SetParent(m_transform);

        return this;
    }

    public GameObject AddChild(GameObject child) {
        m_children.add(child);
        child.SetEngine(m_engine);
        child.GetTransform().SetParent(m_transform);

        return this;
    }

    public GameObject AddComponent(GameComponent component) {
        m_components.add(component);
        component.SetParent(this);

        return this;
    }

    public String getName() {
        return m_name;
    }

    public void InputAll(float delta) {
        Input(delta);

        for (GameObject child : m_children) {
            child.InputAll(delta);
        }
    }

    public void UpdateAll(float delta) {
        Update(delta);

        for (GameObject child : m_children) {
            child.UpdateAll(delta);
        }
    }

    public void RenderAll(Shader shader, RenderingEngine renderingEngine) {
        Render(shader, renderingEngine);

        for (GameObject child : m_children) {
            child.RenderAll(shader, renderingEngine);
        }
    }

    public void Input(float delta) {
        m_transform.Update();

        for (GameComponent component : m_components) {
            component.Input(delta);
        }
    }

    public void Update(float delta) {
        for (GameComponent component : m_components) {
            component.Update(delta);
        }
    }

    public void Render(Shader shader, RenderingEngine renderingEngine) {
        for (GameComponent component : m_components) {
            component.Render(shader, renderingEngine);
        }
    }

    public ArrayList<GameObject> GetAllAttached() {
        ArrayList<GameObject> result = new ArrayList<GameObject>();

        for (GameObject child : m_children) {
            result.addAll(child.GetAllAttached());
        }
        result.add(this);
        return result;
    }

    public Transform GetTransform() {
        return m_transform;
    }

    public void SetEngine(CoreEngine engine) {
        if (this.m_engine != engine) {
            this.m_engine = engine;

            for (GameComponent component : m_components) {
                component.AddToEngine(engine);
            }

            for (GameObject child : m_children) {
                child.SetEngine(engine);
            }
        }
    }

    public String getType() {
        return type.toString();
    }
}
