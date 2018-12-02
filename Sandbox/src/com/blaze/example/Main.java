package com.blaze.example;

import com.blaze.engine.core.CoreEngine;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        //set the system property to load the dll file for running.
        System.setProperty("org.lwjgl.librarypath", new File("libraries").getAbsolutePath());
        com.blaze.engine.core.Game game = new com.blaze.game.PlainGame();
        CoreEngine engine = new CoreEngine(1024, 768, 60, game);
        engine.CreateWindow("Game Window");
        engine.Start();
    }
}
