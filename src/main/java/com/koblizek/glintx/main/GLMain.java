package com.koblizek.glintx.main;

import com.koblizek.glintx.api.display.Window;
import com.koblizek.glintx.api.display.WindowPosition;
import com.koblizek.glintx.api.resource.image.GLImage;
import com.koblizek.glintx.imgui.GuiWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GLMain {
    public static final Logger LOGGER = LogManager.getLogger();

    // The window handle
    private long handle;
    private final GuiWrapper gui = new GuiWrapper();

    public void run() {
        LOGGER.info("Executing GlintX with LWJGL bindings on version " + Version.getVersion());

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);

        // Terminate GLFW and free the error callback
        LOGGER.warn("GLFW termination started");
        gui.stop();
        glfwTerminate();
        LOGGER.warn("GLFWErrorCallback#free can produce NullPointerException");
        glfwSetErrorCallback(null).free();
        LOGGER.error("Process ended with exit code 0");
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(IoBuilder.forLogger().buildPrintStream()).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        LOGGER.info("Initializing GLFW...");
        if ( !glfwInit() )
            LOGGER.fatal("Unable to initialize GLFW");

        Window window = Window.createNewWindow(300, 300, "ano");
        this.handle = window.getHandle();
        window.addKeyPressEvent(key -> {
            LOGGER.info("Key: {} has been pressed!", key);
        });
        window.setKeyCallbacks();

        // Get the thread stack and push a new frame
        window.setPosition(WindowPosition.CENTER);

        window.glfw_context_set();
        // Enable v-sync
        window.toggleVSync(true);

        //set icon image
        window.setIcon(GLImage.loadImage("src/main/resources/icon.png"));
        // Make the window visible
        window.show();
    }

    private void loop() {
        LOGGER.info("Creating capabilities");
        GL.createCapabilities();
        gui.start(handle);
        glClearColor(.5f, .5f, .5f, 0.0f);
        while ( !glfwWindowShouldClose(handle) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            //renders gui
            gui.render();

            glBegin(GL_QUADS);
                glVertex2f(-0.5f, 0.5f);
                glVertex2f(0.5f, 0.5f);
                glVertex2f(0.5f, -0.5f);
                glVertex2f(-0.5f, -0.5f);
            glEnd();

            glfwSwapBuffers(handle); // swap the color buffers

            glfwPollEvents();
        }
    }
}
