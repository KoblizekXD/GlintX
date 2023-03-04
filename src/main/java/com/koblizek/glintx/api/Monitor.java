package com.koblizek.glintx.api;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;

public class Monitor {
    private final long monitorHandle;
    private final GLFWVidMode vidMode;

    public Monitor(long monitorHandle) {
        this.monitorHandle = monitorHandle;
        this.vidMode = glfwGetVideoMode(monitorHandle);
    }
    public static Monitor getMainMonitor() {
        return new Monitor(glfwGetPrimaryMonitor());
    }
    public int getWindowHeight() {
        return vidMode.height();
    }
    public int getWindowWidth() {
        return vidMode.width();
    }
}
