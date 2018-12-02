package com.blaze.engine.rendering.resourceManagement;

import org.lwjgl.opengl.GL15;

public class MeshResource {

    private int m_vbo;
    private int m_ibo;
    private int m_size;
    private int m_refCount;

    public MeshResource(int size) {
        m_vbo = GL15.glGenBuffers();
        m_ibo = GL15.glGenBuffers();
        this.m_size = size;
        this.m_refCount = 1;
    }

    @Override
    protected void finalize() {
        GL15.glDeleteBuffers(m_vbo);
        GL15.glDeleteBuffers(m_ibo);
    }

    public void AddReference() {
        m_refCount++;
    }

    public boolean RemoveReference() {
        m_refCount--;
        return m_refCount == 0;
    }

    public int GetVbo() {
        return m_vbo;
    }

    public int GetIbo() {
        return m_ibo;
    }

    public int GetSize() {
        return m_size;
    }
}
