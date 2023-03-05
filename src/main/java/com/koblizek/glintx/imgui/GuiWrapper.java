package com.koblizek.glintx.imgui;

import imgui.ImGui;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

public class GuiWrapper {
    private final ImGuiImplGlfw imguiGlfw;
    private final ImGuiImplGl3 imguiGl3;
    public static final Logger LOGGER = LogManager.getLogger();
    private long glfwPointer;

    public GuiWrapper() {
        //initializes ImGui objects
        imguiGlfw = new ImGuiImplGlfw();
        imguiGl3 = new ImGuiImplGl3();
    }

    public void start(long windowContext) {
        //sets current window and setups ImGui for rendering onto existing window
        if (glfwPointer == 0)
        glfwPointer = windowContext;
        //sets the context
        ImGui.createContext();
        //maps the callbacks to glfw window
        imguiGlfw.init(glfwPointer, true);
        imguiGl3.init();
        LOGGER.info("ImGui successfully initialized!");
    }

    public void render() {
        if (glfwPointer == 0)
            LOGGER.fatal("GLFW window hasn't been created");
        imguiGlfw.newFrame();
        ImGui.newFrame();
        //rendering done here
        ImGui.render();
        imguiGl3.renderDrawData(ImGui.getDrawData());
    }

    public void stop() {
        //destroys everything
        LOGGER.warn("Destroying ImGui window");
        imguiGl3.dispose();
        imguiGlfw.dispose();
        ImGui.destroyContext();
    }
}
