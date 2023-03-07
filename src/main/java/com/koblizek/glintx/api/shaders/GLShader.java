package com.koblizek.glintx.api.shaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import static org.lwjgl.opengl.GL20.*;

public class GLShader {
    public static final Logger LOGGER = LogManager.getLogger();
    private String fragcontent;
    private String vertcontent;
    private int shaderid;

    public GLShader(String vertexShader, String fragmentShader) {
        Path fragpath = Paths.get("src/main/resources/shaders/frag/"+fragmentShader);
        Path vertpath = Paths.get("src/main/resources/shaders/vert/"+vertexShader);
        //read frag shader
        try {
            byte[] bytes = Files.readAllBytes(fragpath);
            fragcontent = new String(bytes);
        } catch (IOException e) {
            LOGGER.fatal("GLShader#"+fragmentShader+" is non-existant");
        }
        // do the same for vertex shader
        try {
            byte[] bytes = Files.readAllBytes(vertpath);
            vertcontent = new String(bytes);
        } catch (IOException e) {
            LOGGER.fatal("GLShader#"+vertexShader+" is non-existant");

        }
    }
    public void init() {
        // create and compile vertex shader
        int vertexshader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexshader, vertcontent);
        glCompileShader(vertexshader);
        int success = glGetShaderi(vertexshader, GL_COMPILE_STATUS);
        if (success != 1) {
            LOGGER.fatal("Shader compilation failed: " +"\n"+glGetShaderInfoLog(vertexshader));
        }
        // create and compile fragment shader
        int fragshader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragshader, fragcontent);
        glCompileShader(fragshader);
        int succ = glGetShaderi(fragshader, GL_COMPILE_STATUS);
        if (succ != 1) {
            LOGGER.fatal("Shader compilation failed: "+"\n"+glGetShaderInfoLog(fragshader));
        }
        //attach those to a usable shader
        shaderid = glCreateProgram();
        glAttachShader(shaderid, vertexshader);
        glAttachShader(shaderid, fragshader);
        glLinkProgram(shaderid);
        int succe = glGetShaderi(shaderid, GL_LINK_STATUS);
        if (succe == 1) {
            LOGGER.fatal("Shader linking failed: "+"\n"+glGetShaderInfoLog(shaderid));
        }
    }

    public void bind() {
        glUseProgram(shaderid);
    }
    public void unbind() {
        glUseProgram(0);
    }
}
