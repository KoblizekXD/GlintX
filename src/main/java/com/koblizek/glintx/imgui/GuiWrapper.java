package com.koblizek.glintx.imgui;

import imgui.ImGui;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

public class GuiWrapper {
    private final ImGuiImplGlfw imguiGlfw;
    private final ImGuiImplGl3 imguiGl3;

    private long glfwPointer;

    public GuiWrapper() {
        imguiGlfw = new ImGuiImplGlfw();
        imguiGl3 = new ImGuiImplGl3();
    }

    public void start(long windowContext) {
        //sets current window and setups imgui for rendering onto existing window
        if (glfwPointer == 0)
        glfwPointer = windowContext;
        ImGui.createContext();
        imguiGlfw.init(glfwPointer, true);
        imguiGl3.init();
    }

    public void render() {
        imguiGlfw.newFrame();
        ImGui.newFrame();
        //rendering done here
        ImGui.render();
        imguiGl3.renderDrawData(ImGui.getDrawData());
    }


    public void stop() {
        imguiGl3.dispose();
        imguiGlfw.dispose();
        ImGui.destroyContext();
    }
}
