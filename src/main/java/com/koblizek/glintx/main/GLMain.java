package com.koblizek.glintx.main;

import com.koblizek.glintx.api.Window;
import com.koblizek.glintx.api.WindowPosition;
import com.koblizek.glintx.imgui.GuiWrapper;
import org.apache.logging.log4j.LogManager;
import com.koblizek.glintx.api.resource.image.GLImage;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.glfw.GLFWImage;
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

    private final GLImage icon = GLImage.loadImage("src/main/resources/icon.png");


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
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(IoBuilder.forLogger().buildPrintStream()).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        LOGGER.info("Initializing GLFW...");
        if ( !glfwInit() )
            LOGGER.fatal("Unable to initialize GLFW");

        Window window1 = Window.createNewWindow(300, 300, "ano");
        this.window = window1.getHandle();
        if ( window == NULL )
            LOGGER.fatal("Failed to create the GLFW window");
        window1.addKeyPressEvent(key -> {
            LOGGER.info("Key: {} has been pressed!", key);
        });
        window1.setKeyCallbacks();

        // Get the thread stack and push a new frame
        window1.setPosition(WindowPosition.CENTER);

        window1.glfw_context_set();
        // Enable v-sync
        window1.toggleVSync(true);

        //set icon image
        GLFWImage image = GLFWImage.malloc(); GLFWImage.Buffer imagebf = GLFWImage.malloc(1);
        image.set(icon.getWidth(), icon.getHeight(), icon.getImage());
        imagebf.put(0, image);
        glfwSetWindowIcon(window, imagebf);
        // Make the window visible
        LOGGER.warn("Showing window");
        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();
        gui.start(window);
        glClearColor(.5f, .5f, .5f, 0.0f);
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            //renders gui
            gui.render();

            glfwSwapBuffers(window); // swap the color buffers

            glfwPollEvents();
        }
    }
}
