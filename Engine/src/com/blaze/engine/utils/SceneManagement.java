package com.blaze.engine.utils;

import com.blaze.engine.components.rendering.MeshRenderer;
import com.blaze.engine.core.Game;
import com.blaze.engine.core.GameObject;
import com.blaze.engine.rendering.Material;
import com.blaze.engine.rendering.Mesh;
import com.blaze.engine.rendering.Texture;

/**
 *
 * @author Ben
 */
public class SceneManagement {

    public static void createCube(Game g) {
        Material material = new Material(new Texture("bricks2.jpg"), 1, 8,
                new Texture("bricks2_normal.png"), new Texture("bricks2_disp.jpg"), 0.04f, -1.0f);

        GameObject temp = new GameObject("cube");
        MeshRenderer renderer = new MeshRenderer(new Mesh("cube.obj"), material);
        temp.AddComponent(renderer);
        g.AddGameObject(temp);
    }

    public static void createQuad(Game g) {
        GameObject temp = new GameObject("quad").AddComponent(new MeshRenderer(new Mesh("quad.obj"), new Material(new Texture("bricks.png"))));
        g.AddGameObject(temp);
    }

    public static void LoadMeshintoGame(Game game, String name, String meshName, String textureName) {
        Mesh mesh = new Mesh(meshName);
        Material material = new Material(new Texture(textureName));
        MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
        GameObject object = new GameObject(name);
        object.AddComponent(meshRenderer);
        object.GetTransform().GetPosition().Set(0, -1, 5);
        game.AddGameObject(object);
    }
}
