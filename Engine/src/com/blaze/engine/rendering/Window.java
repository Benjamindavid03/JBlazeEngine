package com.blaze.engine.rendering;

import com.blaze.engine.core.Math.Vector2f;
import java.awt.Canvas;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window {

    public static void CreateWindow(int width, int height, String title, Canvas parent) {
        Display.setTitle(title);
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setVSyncEnabled(true);
            Display.setParent(parent);
            Display.create();
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void CreateWindow(int width, int height, String title) {
        Display.setTitle(title);
        try {
            Display.setVSyncEnabled(true);
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void Render() {
        Display.update();
    }

    public static void Dispose() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
    }

    public static boolean IsCloseRequested() {
        return Display.isCloseRequested();
    }

    public static int GetWidth() {
        return Display.getDisplayMode().getWidth();
    }

    public static int GetHeight() {
        return Display.getDisplayMode().getHeight();
    }

    public static String GetTitle() {
        return Display.getTitle();
    }

    public Vector2f GetCenter() {
        return new Vector2f(GetWidth() / 2, GetHeight() / 2);
    }
}
