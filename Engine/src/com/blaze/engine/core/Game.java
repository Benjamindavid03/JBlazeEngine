package com.blaze.engine.core;

import com.blaze.engine.rendering.RenderingEngine;
import java.util.ArrayList;

public abstract class Game {

    private GameObject m_root;

    public void Init() {
    }

    public void Input(float delta) {
        GetRootObject().InputAll(delta);
    }

    public void Update(float delta) {
        GetRootObject().UpdateAll(delta);
    }

    public void Render(RenderingEngine renderingEngine) {
        renderingEngine.Render(GetRootObject());
    }

    public GameObject GetGameObject(String name) {
        GameObject temp = new GameObject();
        ArrayList<GameObject> list = GetRootObject().GetAllAttached();
        for (int i = 0; i < list.size(); i++) {
            if (name == list.get(i).getName()) {
                temp = list.get(i);
                break;
            }
        }
        return temp;
    }

    public ArrayList<GameObject> GetGameObjects() {
        ArrayList<GameObject> list = GetRootObject().GetAllAttached();
        return list;
    }

    public GameObject GetGameObject(int index) {
        ArrayList<GameObject> list = GetRootObject().GetAllAttached();
        return list.get(index);
    }

    public void AddGameObject(GameObject object) {
        GetRootObject().AddChild(object);
    }

    public GameObject GetRootObject() {
        if (m_root == null) {
            m_root = new GameObject();
        }

        return m_root;
    }

    public void SetEngine(CoreEngine engine) {
        GetRootObject().SetEngine(engine);
    }
}
