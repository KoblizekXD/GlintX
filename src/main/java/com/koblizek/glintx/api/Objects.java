package com.koblizek.glintx.api;

import com.koblizek.glintx.api.shaders.ShaderManager;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public final class Objects {
    private Objects() {}

    public static void drawQuad(float x, float y, float width, float height) {
        glBegin(GL_QUADS);
            glVertex2d(1/x, 1/y);
            glVertex2d(1/x, 1/(y + height));
            glVertex2d(1/(x + width), 1/(y + height));
            glVertex2d(1/(x + width), 1/y);
        glEnd();
    }
    public static void drawTriangle() {
        glTranslated();
        float vertices[] = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f,  0.5f, 0.0f
        };
        int vbo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        int program = ShaderManager.createProgram();
        // TODO: Memory leak
        glAttachShader(program, ShaderManager.load(GL_VERTEX_SHADER, "src/main/resources/shaders/vertex_shader.glsl"));
        glAttachShader(program, ShaderManager.load(GL_FRAGMENT_SHADER, "src/main/resources/shaders/fragment_shader.glsl"));
        glLinkProgram(program);
        glUseProgram(program);

    }
}
