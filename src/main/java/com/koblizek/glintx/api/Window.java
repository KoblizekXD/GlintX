package com.koblizek.glintx.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private final long handle;
    private Consumer<Integer> keyPressEvent;
    private Consumer<Integer> keyReleaseEvent;
    private static final Logger LOGGER = LogManager.getLogger();

    public Window(long handle) {
        if (handle == NULL)
            LOGGER.error("NULL Exception: handle(long) cannot be null");
        this.handle = handle;
    }
    public void setSize(int width, int height) {
        glfwSetWindowSize(handle, width, height);
    }
    public void toggleVSync(boolean option) {
        glfwSwapInterval((option ? 1 : 0));
    }
    public void setTitle(String title) {
        glfwSetWindowTitle(handle, title);
    }
    public void handleKeyPressEvent(Consumer<Integer> keyEvent) {
        keyPressEvent = keyEvent;
    }
    public void handleKeyReleaseEvent(Consumer<Integer> keyEvent) {
        keyReleaseEvent = keyEvent;
    }
    public void setKeyCallbacks() {
        glfwSetKeyCallback(handle, (window, key, scancode, action, mods) -> {
            if (keyPressEvent != null && action == GLFW_PRESS) {
                keyPressEvent.accept(key);
            } else if (keyReleaseEvent != null && action == GLFW_RELEASE) {
                keyReleaseEvent.accept(key);
            }
        });
    }
}
