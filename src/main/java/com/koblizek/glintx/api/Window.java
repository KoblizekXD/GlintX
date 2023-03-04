package com.koblizek.glintx.api;

import com.koblizek.glintx.api.input.Key;
import com.koblizek.glintx.api.input.InputState;
import com.koblizek.glintx.util.InvokableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private final long handle;
    private static final Logger LOGGER = LogManager.getLogger();
    private IntBuffer width;
    private IntBuffer height;

    private static final InvokableList<Key> keyPressEventList = new InvokableList<>();

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

    public void addKeyPressEvent(Consumer<Key> consumer) {
        keyPressEventList.add(consumer);
    }
    public int getWidth() {
        return width.get(0);
    }
    public int getHeight() {
        return height.get(0);
    }

    public void setKeyCallbacks() {
        glfwSetKeyCallback(handle, (window, key, scancode, action, mods) -> {
            if (action == InputState.PRESS.getLwjgl3Value()) {
                keyPressEventList.invokeAll(Key.getKeyById(key));
            }
        });
    }
    public void setPosition(WindowPosition position) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            width = stack.mallocInt(1);
            height = stack.mallocInt(1);
        }
        glfwGetWindowSize(handle, width, height);
        glfwSetWindowPos(handle, (Monitor.getMainMonitor().getWindowWidth() - getWidth()) / position.getDivision(),
                (Monitor.getMainMonitor().getWindowHeight() - getHeight()) / position.getDivision());
    }
    public void setPosition(int division) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            width = stack.mallocInt(1);
            height = stack.mallocInt(1);
        }
        glfwGetWindowSize(handle, width, height);
        glfwSetWindowPos(handle, (Monitor.getMainMonitor().getWindowWidth() - getWidth()) / division,
                (Monitor.getMainMonitor().getWindowHeight() - getHeight()) / division);
    }
    public void show() {
        glfwShowWindow(handle);
    }
    public void glfw_context_set() {
        glfwMakeContextCurrent(handle);
    }
    public void setResizable(boolean b) {
        glfwWindowHint(GLFW_RESIZABLE, (b ? GLFW_TRUE : GLFW_FALSE));
    }

    public long getHandle() {
        return handle;
    }

    public static Window createNewWindow(int width, int height, String title) {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);

        return new Window(glfwCreateWindow(width, height, title, NULL, NULL));
    }
}
