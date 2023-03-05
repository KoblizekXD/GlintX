package com.koblizek.glintx.api.display;

import com.koblizek.glintx.api.input.Key;
import com.koblizek.glintx.api.input.InputState;
import com.koblizek.glintx.api.input.Mouse;
import com.koblizek.glintx.api.resource.image.GLImage;
import com.koblizek.glintx.util.InvokableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFWImage;
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
    public static final InvokableList<Key> keyDownEventList = new InvokableList<>();
    public static final InvokableList<Key> keyUpEventList = new InvokableList<>();

    public Window(long handle) {
        if (handle == NULL)
            LOGGER.error("NULL Exception: handle(long) cannot be null");
        this.handle = handle;
    }
    public void setSize(int width, int height) {
        glfwSetWindowSize(handle, width, height);
    }
    public void toggleVSync(boolean option) {
        LOGGER.warn("Toggling VSync can cause a frame drop");
        glfwSwapInterval((option ? 1 : 0));
    }
    public void setTitle(String title) {
        glfwSetWindowTitle(handle, title);
    }

    public void addKeyPressEvent(Consumer<Key> consumer) {
        keyPressEventList.add(consumer);
    }
    public void addKeyDownEvent(Consumer<Key> consumer) {
        keyDownEventList.add(consumer);
    }
    public void addKeyUpEvent(Consumer<Key> consumer) {
        keyUpEventList.add(consumer);
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
            if (action == InputState.HELD.getLwjgl3Value()) {
                keyDownEventList.invokeAll(Key.getKeyById(key));
            }
            if (action == InputState.RELEASE.getLwjgl3Value()) {
                keyUpEventList.invokeAll(Key.getKeyById(key));
            }
        });
    }
    public void setMouseCallbacks() {
        glfwSetMouseButtonCallback(handle, (window, button, action, mods) -> {

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
    public void setContext() {
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
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);

        return new Window(glfwCreateWindow(width, height, title, NULL, NULL));
    }
    public void setIcon(GLImage glImage) {
        GLFWImage image = GLFWImage.malloc();
        GLFWImage.Buffer imagebf = GLFWImage.malloc(1);
        image.set(glImage.getWidth(), glImage.getHeight(), glImage.getImage());
        imagebf.put(0, image);
        glfwSetWindowIcon(handle, imagebf);
        imagebf.free();
    }
    public void setAttribute(int attribute, int value) {
        glfwSetWindowAttrib(handle, attribute, value);
    }
}
