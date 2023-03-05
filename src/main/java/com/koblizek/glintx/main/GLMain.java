package com.koblizek.glintx.main;

import com.koblizek.glintx.api.Objects;
import com.koblizek.glintx.api.display.Window;
import com.koblizek.glintx.api.display.WindowPosition;
import com.koblizek.glintx.api.input.Key;
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
    private Window window;

    public void run() {
        LOGGER.info("Executing GlintX with LWJGL bindings on version " + Version.getVersion());

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);

        // Terminate GLFW and free the error callback
        Window.terminateAPI(gui);
        LOGGER.warn("GLFWErrorCallback#free can produce NullPointerException");
        glfwSetErrorCallback(null).free();
        LOGGER.error("Process ended with exit code 0");
    }

    private void init() {
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        Window.initializeWindowAPI();

        window = Window.createNewWindow(1024, 704, "ano");
        this.handle = window.getHandle();
        window.addKeyPressEvent(key -> {
            LOGGER.info("Key: {} has been pressed!", key);
        });
        window.addKeyDownEvent(key -> {
            if (key == Key.KEY_D) {
                glTranslatef(5, 0, 0);
            }
            if (key == Key.KEY_S) {
                glTranslatef(0, 5, 0);
            }
            if (key == Key.KEY_A) {
                glTranslatef(-5, 0, 0);
            }
            if (key == Key.KEY_W) {
                glTranslatef(0, -5, 0);
            }
        });
        window.addMouseButtonPressEvent(LOGGER::info);
        window.setKeyCallbacks();

        // Get the thread stack and push a new frame
        window.setPosition(WindowPosition.CENTER);

        window.setContext();
        // Enable v-sync
        window.toggleVSync(true);

        //set icon image
        window.setIcon(GLImage.loadImage("src/main/resources/icon.png"));
        // Make the window visible
        window.show();
    }

    private void setupCoordinateSystem(int screenWidth, int screenHeight) {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, screenWidth, screenHeight, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    private void loop() {
        LOGGER.info("Creating capabilities");
        GL.createCapabilities();

        setupCoordinateSystem(window.getWidth(), window.getHeight());

        gui.start(handle);
        glClearColor(.5f, .5f, .5f, 0.0f);
        while ( !glfwWindowShouldClose(handle) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            //renders gui
            gui.render();

            Objects.drawQuad(10, 10, 100, 100);

            glfwPollEvents();

            glfwSwapBuffers(handle); // swap the color buffers
        }
    }
}
