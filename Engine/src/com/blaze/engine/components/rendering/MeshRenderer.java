package com.blaze.engine.components.rendering;

import com.blaze.engine.components.GameComponent;
import com.blaze.engine.rendering.Material;
import com.blaze.engine.rendering.Mesh;
import com.blaze.engine.rendering.RenderingEngine;
import com.blaze.engine.rendering.Shader;

public class MeshRenderer extends GameComponent {

    private Mesh m_mesh;
    private Material m_material;

    private Shader shader;

    public MeshRenderer(Mesh mesh, Material material) {
        this.m_mesh = mesh;
        this.m_material = material;
    }

    @Override
    public void Render(Shader shader, RenderingEngine renderingEngine) {
        this.shader = shader;
        shader.Bind();
        shader.UpdateUniforms(GetTransform(), m_material, renderingEngine);
        m_mesh.Draw();
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }
}
