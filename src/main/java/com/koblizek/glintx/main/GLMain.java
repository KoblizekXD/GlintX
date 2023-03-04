package com.koblizek.glintx.main;

import com.koblizek.glintx.api.Window;
import com.koblizek.glintx.api.WindowPosition;
import com.koblizek.glintx.imgui.GuiWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GLMain {
    public static final Logger LOGGER = LogManager.getLogger();

    // The window handle
    private long window;
    private final GuiWrapper gui = new GuiWrapper();

    public void run() {
        LOGGER.info("Executing GlintX with LWJGL bindings on version " + Version.getVersion());

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        LOGGER.warn("GLFW termination started");
        gui.stop();
        glfwTerminate();
        glfwSetErrorCallback(null).free();
        LOGGER.error("Process ended with exit code 0");
    }

    private void init() {
        GLFWErrorCallback.createPrint(IoBuilder.forLogger().buildPrintStream()).set();
        LOGGER.info("Initializing GLFW...");
        if ( !glfwInit() )
            LOGGER.fatal("Unable to initialize GLFW");

        Window window1 = Window.createNewWindow(300, 300, "Hll");
        window = window1.getHandle();

        window1.addKeyPressEvent(key -> {
            LOGGER.info("Key: {} has been pressed!", key);
        });
        window1.setKeyCallbacks();

        window1.setPosition(WindowPosition.CENTER);
        window1.glfw_context_set();
        window1.toggleVSync(true);
        LOGGER.warn("Showing window");
        window1.show();
    }

    private void loop() {
        GL.createCapabilities();
        //findme :: starts rendering gui
        gui.start(window);
        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            //renders gui
            gui.render();

            glfwSwapBuffers(window); // swap the color buffers

            glfwPollEvents();
        }
    }



}
