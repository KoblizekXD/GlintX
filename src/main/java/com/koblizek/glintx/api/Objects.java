package com.koblizek.glintx.api;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public final class Objects {
    private Objects() {}

    public static void  drawQuad(float x, float y, float width, float height) {
        glBegin(GL_QUADS);
            glVertex2d(x, y);
            glVertex2d(x, (y + height));
            glVertex2d((x + width), (y + height));
            glVertex2d((x + width), y);
        glEnd();
    }

    public static void drawTriangle() {




    }
}
