package com.blaze.engine.rendering.resourceManagement;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class TextureResource {

    private int m_id;
    private int m_refCount;

    public TextureResource() {
        this.m_id = GL11.glGenTextures();
        this.m_refCount = 1;
    }

    @Override
    protected void finalize() {
        GL15.glDeleteBuffers(m_id);
    }

    public void AddReference() {
        m_refCount++;
    }

    public boolean RemoveReference() {
        m_refCount--;
        return m_refCount == 0;
    }

    public int GetId() {
        return m_id;
    }
}
