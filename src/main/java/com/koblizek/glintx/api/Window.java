package com.koblizek.glintx.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private final long handle;
    private static final Logger LOGGER = LogManager.getLogger();

    public Window(long handle) {
        if (handle == NULL)
            LOGGER.error("NULL Exception: handle(long) cannot be null");
        this.handle = handle;
    }
    public void setSize(int width, int height) {
        glfwSetWindowSize(handle, width, height);
    }
    public void enableVerticalSynchronization() {
        glfwSwapInterval(1);
    }
    public void disableVerticalSynchronization() {
        glfwSwapInterval(0);
    }
}
