package com.blaze.engine.rendering;

import com.blaze.engine.core.Math.Vector3f;
import com.blaze.engine.core.Util;
import com.blaze.engine.rendering.meshLoading.IndexedModel;
import com.blaze.engine.rendering.meshLoading.OBJModel;
import com.blaze.engine.rendering.resourceManagement.MeshResource;
import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class Mesh {

    private static HashMap<String, MeshResource> s_loadedModels = new HashMap<String, MeshResource>();
    private MeshResource m_resource;
    private String m_fileName;

    public Mesh(String fileName) {
        this.m_fileName = fileName;
        MeshResource oldResource = s_loadedModels.get(fileName);

        if (oldResource != null) {
            m_resource = oldResource;
            m_resource.AddReference();
        } else {
            LoadMesh(fileName);
            s_loadedModels.put(fileName, m_resource);
        }
    }

    public Mesh(Vertex[] vertices, int[] indices) {
        this(vertices, indices, false);
    }

    public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
        m_fileName = "";
        AddVertices(vertices, indices, calcNormals);
    }

    @Override
    protected void finalize() {
        if (m_resource.RemoveReference() && !m_fileName.isEmpty()) {
            s_loadedModels.remove(m_fileName);
        }
    }

    private void AddVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
        if (calcNormals) {
            CalcNormals(vertices, indices);
        }

        m_resource = new MeshResource(indices.length);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m_resource.GetVbo());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Util.CreateFlippedBuffer(vertices), GL15.GL_STATIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, m_resource.GetIbo());
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.CreateFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
    }

    public void Draw() {
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m_resource.GetVbo());
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, Vertex.SIZE * 4, 0);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Vertex.SIZE * 4, 12);
        GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, Vertex.SIZE * 4, 20);
        GL20.glVertexAttribPointer(3, 3, GL11.GL_FLOAT, false, Vertex.SIZE * 4, 32);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, m_resource.GetIbo());
        GL11.glDrawElements(GL11.GL_TRIANGLES, m_resource.GetSize(), GL11.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
    }

    private void CalcNormals(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i += 3) {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vector3f v1 = vertices[i1].GetPos().Sub(vertices[i0].GetPos());
            Vector3f v2 = vertices[i2].GetPos().Sub(vertices[i0].GetPos());

            Vector3f normal = v1.Cross(v2).Normalized();

            vertices[i0].SetNormal(vertices[i0].GetNormal().Add(normal));
            vertices[i1].SetNormal(vertices[i1].GetNormal().Add(normal));
            vertices[i2].SetNormal(vertices[i2].GetNormal().Add(normal));
        }

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].SetNormal(vertices[i].GetNormal().Normalized());
        }
    }

    private Mesh LoadMesh(String fileName) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        if (!ext.equals("obj")) {
            System.err.println("Error: '" + ext + "' file format not supported for mesh data.");
            new Exception().printStackTrace();
            System.exit(1);
        }

        OBJModel test = new OBJModel("./res/models/" + fileName);
        IndexedModel model = test.ToIndexedModel();

        ArrayList<Vertex> vertices = new ArrayList<Vertex>();

        for (int i = 0; i < model.GetPositions().size(); i++) {
            vertices.add(new Vertex(model.GetPositions().get(i),
                    model.GetTexCoords().get(i),
                    model.GetNormals().get(i),
                    model.GetTangents().get(i)));
        }

        Vertex[] vertexData = new Vertex[vertices.size()];
        vertices.toArray(vertexData);

        Integer[] indexData = new Integer[model.GetIndices().size()];
        model.GetIndices().toArray(indexData);

        AddVertices(vertexData, Util.ToIntArray(indexData), false);

        return this;
    }
}
