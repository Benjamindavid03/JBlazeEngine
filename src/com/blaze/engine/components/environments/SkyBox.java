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
package com.blaze.engine.components.environments;

import com.blaze.engine.components.GameComponent;
import com.blaze.engine.components.gameobjects.MeshRenderer;
import com.blaze.engine.core.GameObject;
import com.blaze.engine.rendering.Material;
import com.blaze.engine.rendering.Mesh;
import com.blaze.engine.rendering.Texture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 *
 * @author Benjamin
 * @version
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
