package com.blaze.engine.rendering;

import com.blaze.engine.components.camera.Camera;
import com.blaze.engine.components.lights.BaseLight;
import com.blaze.engine.core.GameObject;
import com.blaze.engine.core.Math.Vector3f;
import com.blaze.engine.core.Transform;
import com.blaze.engine.rendering.resourceManagement.MappedValues;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
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
    private Vector3f ambientColor = new Vector3f(0.5f, 0.5f, 0.5f);

    public RenderingEngine() {
        super();
        m_lights = new ArrayList<BaseLight>();
        m_samplerMap = new HashMap<String, Integer>();
        m_samplerMap.put("diffuse", 0);
        m_samplerMap.put("normalMap", 1);
        m_samplerMap.put("dispMap", 2);

        AddVector3f("ambient", ambientColor);

        m_forwardAmbient = new Shader("forward-ambient");

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        GL11.glFrontFace(GL11.GL_CW);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

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
        //setupLight();
        GL11.glDepthFunc(GL11.GL_LESS);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    //This is a new try method. For shadows. Just for implementation.
    public void setupLight() {
        float[] lightPos = {1f, 1f, 1f, 0f};

        //material colors
        float[] matspecular = {1, 1, 1, 0};
        float[] matambient = {1, 1, 1, 0};
        float[] matdiffuse = {0.5f, 0.5f, 0.5f, 0};

        //light colors
        float[] lightspecular = {1f, 1f, 1f, 1};
        float[] lightdiffuse = {0.5f, 0.5f, 0.5f, 0};
        float[] lightambient = {0.1f, 0.1f, 0.1f, 0};

        GL11.glEnable(GL11.GL_LIGHT0);

        ByteBuffer temp = ByteBuffer.allocateDirect(16);
        temp.order(ByteOrder.nativeOrder());
        FloatBuffer lightPosition = (FloatBuffer) temp.asFloatBuffer().put(lightPos).flip();
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, (FloatBuffer) (lightPosition));

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, (FloatBuffer) temp.asFloatBuffer().put(lightspecular).flip());
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, (FloatBuffer) temp.asFloatBuffer().put(lightambient).flip());
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, (FloatBuffer) temp.asFloatBuffer().put(lightdiffuse).flip());

        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, (FloatBuffer) temp.asFloatBuffer().put(matspecular).flip());
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, (FloatBuffer) temp.asFloatBuffer().put(matdiffuse).flip());
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, (FloatBuffer) temp.asFloatBuffer().put(matambient).flip());

        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 2f);
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

    /**
     * @return the ambientColor
     */
    public Vector3f getAmbientColor() {
        return ambientColor;
    }

    /**
     * @param ambientColor the ambientColor to set
     */
    public void setAmbientColor(Vector3f ambientColor) {
        this.ambientColor = ambientColor;
    }
}
