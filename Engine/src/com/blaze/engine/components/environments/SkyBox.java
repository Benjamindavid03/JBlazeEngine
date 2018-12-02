package com.blaze.engine.components.environments;

import com.blaze.engine.components.GameComponent;
import com.blaze.engine.components.rendering.MeshRenderer;
import com.blaze.engine.core.GameObject;
import com.blaze.engine.rendering.Material;
import com.blaze.engine.rendering.Mesh;
import com.blaze.engine.rendering.Texture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 *
 * @author Benjamin
 */
public class SkyBox extends GameComponent {

    public SkyBox(GameObject sceneRoot, String objModel, String textureFile) {
        super();
        try {
            Mesh skyBoxMesh = new Mesh(objModel);
            Texture tex = new Texture(textureFile);
            Material material = new Material(tex, 0.5f, 0.2f, 1f, 0.6f);
            MeshRenderer meshRenderer = new MeshRenderer(skyBoxMesh, material);
            GameObject planeObject = new GameObject("sky");
            planeObject.AddComponent(meshRenderer);
            SetParent(sceneRoot);
        } catch (Exception exc) {
        }
    }

    public int loadCubeMap(String[] textureFiles) {

        int texID = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);
        for (int i = 0; i < textureFiles.length; i++) {
            TextureData data = Texture.decodeTextureFile("res/" + textureFiles[i] + ".png");
            GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, data.getWidth(), data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
        }
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        //textureFiles.add(texID);
        return texID;
    }

}
