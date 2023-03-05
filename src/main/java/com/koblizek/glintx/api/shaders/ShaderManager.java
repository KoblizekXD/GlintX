package com.koblizek.glintx.api.shaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import java.io.*;
import java.nio.file.Files;

import static org.lwjgl.opengl.GL20.*;

public final class ShaderManager {
    public static final Logger LOGGER = LogManager.getLogger();
    public static int load(int type, String path) {
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(file.toPath());
        } catch (IOException e) {
            LOGGER.error("Failed to load shader: ");
        }
        assert reader != null;
        String shader = reader.lines().toString();

        LOGGER.info("Compiling shader...");

        int v = glCreateShader(type);
        glShaderSource(v, shader);
        glCompileShader(v);
        return v;
    }
    public static int createProgram() {
        return glCreateProgram();
    }
}
