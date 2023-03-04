package com.koblizek.glintx.api.resource.image;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;

/**
 * placeholder from
 * https://discourse.glfw.org/t/set-window-icon-in-lwjgl-3-1/863/4
 */
public class GLImage {

    public static final Logger LOGGER = LogManager.getLogger();

    public ByteBuffer getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return heigh;
    }

    private ByteBuffer image;
    private int width, heigh;

    GLImage(int width, int heigh, ByteBuffer image) {
        this.image = image;
        this.heigh = heigh;
        this.width = width;
    }
    public static GLImage loadImage(String path) {
        ByteBuffer image;
        int width, heigh;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            image = stbi_load(path, w, h, comp, 4);
            if (image == null) {
                LOGGER.fatal("Error While loading image");
            }
            width = w.get();
            heigh = h.get();
        }
        return new GLImage(width, heigh, image);
    }
}
