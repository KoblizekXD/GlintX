package com.koblizek.glintx.api.display;

import com.koblizek.glintx.api.input.Key;
import com.koblizek.glintx.api.input.InputState;
import com.koblizek.glintx.api.input.Mouse;
import com.koblizek.glintx.api.resource.image.GLImage;
import com.koblizek.glintx.imgui.GLGui;
import com.koblizek.glintx.util.InvokableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.function.Consumer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private final long handle;
    private static final Logger LOGGER = LogManager.getLogger();
    private IntBuffer width;
    private IntBuffer height;
    private int winWidth;
    private int winHeight;

    private static final InvokableList<Key> keyPressEventList = new InvokableList<>();
    public static final InvokableList<Key> keyDownEventList = new InvokableList<>();
    public static final InvokableList<Key> keyUpEventList = new InvokableList<>();
    public static final InvokableList<Mouse> mouseButtonPressEventList = new InvokableList<>();

    public Window(long handle, int width, int height) {
        this.winWidth = width;
        this.winHeight = height;
        if (handle == NULL)
            LOGGER.error("NULL Exception: handle(long) cannot be null");
        this.handle = handle;
    }
    public void setSize(int width, int height) {
        glfwSetWindowSize(handle, width, height);
    }
    public void toggleVSync(boolean option) {
        LOGGER.warn("!Toggling VSync can cause a frame drop!");
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
    public void addMouseButtonPressEvent(Consumer<Mouse> consumer) {
        mouseButtonPressEventList.add(consumer);
    }
    public int getWidth() {
        return winWidth;
    }
    public int getHeight() {
        return winHeight;
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
        glfwSetMouseButtonCallback(handle, (window, key, action, mods) -> {
            if (action == InputState.PRESS.getLwjgl3Value()) {
                mouseButtonPressEventList.invokeAll(Mouse.getKeyById(key));
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

        return new Window(glfwCreateWindow(width, height, title, NULL, NULL), width, height);
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
    public void destroy() {
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);
    }
    public static void initializeWindowAPI() {
        GLFWErrorCallback.createPrint(IoBuilder.forLogger().buildPrintStream()).set();
        LOGGER.info("Initializing window API(GLFW)...");
        if (!glfwInit())
            LOGGER.error("Error occurred while initializing window API");
    }
    public static void terminateAPI(GLGui wrapper) {
        LOGGER.warn("Terminating GLFW");
        wrapper.stop();
        glfwTerminate();

        glfwSetErrorCallback(null).free();
    }
    public static void invokeEvents() {
        glfwPollEvents();
    }
    public void switchBufferState() {
        glfwSwapBuffers(handle);
    }

    public boolean shouldCloseWindow() {
       return glfwWindowShouldClose(handle);
    }

}
