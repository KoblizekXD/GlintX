package com.koblizek.glintx.main;

import com.koblizek.glintx.api.display.Window;
import com.koblizek.glintx.api.display.WindowPosition;
import com.koblizek.glintx.api.resource.image.GLImage;
import com.koblizek.glintx.api.shaders.GLShader;
import com.koblizek.glintx.imgui.GLGui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


public class GLMain {
    public static final Logger LOGGER = LogManager.getLogger();

    // The window handle
    private long handle;
    private final GLGui gui = new GLGui();
    private Window window;
    private int shaderprogram;
    private int VBO,VAO,EBO;

    public void run() {
        LOGGER.info("Executing GlintX with LWJGL bindings on version " + Version.getVersion());
        init();
        loop();
        // Free the window callbacks and destroy the window
        window.destroy();
        // Terminate GLFW and free the error callback
        LOGGER.warn("GLFWErrorCallback#free can produce NullPointerException");
        Window.terminateAPI(gui);
        LOGGER.error("Process ended with exit code 0");
    }


    private GLShader shader = new GLShader("vertex_shader.vsh","fragment_shader.fsh");
    private void init() {
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        Window.initializeWindowAPI();
        // create the window
        window = Window.createNewWindow(1024, 704, "GlintX");
        this.handle = window.getHandle();
        window.addKeyPressEvent(key -> {
            LOGGER.info("Key: {} has been pressed!", key);
        });
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
        GL.createCapabilities();
        shader.init();

        // delete shaders from memory
        float vertices[] = {
                0.5f,  0.5f, 0.0f,  // top right
                0.5f, -0.5f, 0.0f,  // bottom right
                -0.5f, -0.5f, 0.0f,  // bottom left
                -0.5f,  0.5f, 0.0f   // top left
        };
        int indices[] = {  // note that we start from 0!
                0, 1, 3,   // first triangle
                1, 2, 3    // second triangle
        };
        // gen objects
        VBO = glGenBuffers();
        VAO = glGenVertexArrays();
        EBO = glGenBuffers();
        //bind vertex array
        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        //bind element buffer
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        //set the vertex attributes pointers
        glVertexAttribPointer(0, 3,GL_FLOAT,false,3*4,0);
        glEnableVertexAttribArray(0);
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void setupCoordinateSystem(int screenWidth, int screenHeight) {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, screenWidth, screenHeight, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    private void loop() {
        LOGGER.info("Creating capabilities");
        gui.start(handle);
        setupCoordinateSystem(window.getWidth(),window.getHeight());
        glClearColor(.5f, .5f, .5f, 0.0f);
        while (!window.shouldCloseWindow()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            //renders gui
            gui.render();
            shader.bind();
            glBindVertexArray(VAO);
            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
            shader.unbind();
            Window.invokeEvents();
            window.switchBufferState();
        }
    }
}
