package com.blaze.engine.rendering;

import com.blaze.engine.components.environments.TextureData;
import com.blaze.engine.core.Util;
import com.blaze.engine.rendering.resourceManagement.TextureResource;
import de.matthiasmann.twl.utils.PNGDecoder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {

    private static HashMap<String, TextureResource> s_loadedTextures = new HashMap<String, TextureResource>();
    private TextureResource m_resource;
    private String m_fileName;

    public Texture(String fileName) {
        this.m_fileName = fileName;
        TextureResource oldResource = s_loadedTextures.get(fileName);

        if (oldResource != null) {
            m_resource = oldResource;
            m_resource.AddReference();
        } else {
            m_resource = LoadTexture(fileName);
            s_loadedTextures.put(fileName, m_resource);
        }
    }

    @Override
    protected void finalize() {
        if (m_resource.RemoveReference() && !m_fileName.isEmpty()) {
            s_loadedTextures.remove(m_fileName);
        }
    }

    public void Bind() {
        Bind(0);
    }

    public void Bind(int samplerSlot) {
        assert (samplerSlot >= 0 && samplerSlot <= 31);
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + samplerSlot);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, m_resource.GetId());
    }

    public int GetID() {
        return m_resource.GetId();
    }

    private static TextureResource LoadTexture(String fileName) {
        try {
            BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

            ByteBuffer buffer = Util.CreateByteBuffer(image.getHeight() * image.getWidth() * 4);
            boolean hasAlpha = image.getColorModel().hasAlpha();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x];

                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) ((pixel) & 0xFF));
                    if (hasAlpha) {
                        buffer.put((byte) ((pixel >> 24) & 0xFF));
                    } else {
                        buffer.put((byte) (0xFF));
                    }
                }
            }

            buffer.flip();

            TextureResource resource = new TextureResource();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, resource.GetId());

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

            return resource;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public static TextureData decodeTextureFile(String fileName) {
        int width = 0;
        int height = 0;
        ByteBuffer buffer = null;
        try {
            FileInputStream in = new FileInputStream(fileName);
            PNGDecoder decoder = new PNGDecoder(in);
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Tried to load texture " + fileName + ", didn't work");
            System.exit(-1);
        }
        return new TextureData(buffer, width, height);
    }
}
