package com.koblizek.glintx.util;

import org.apache.logging.log4j.LogManager;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GLErrorHandle {
    public static void handleLinking(int program, Consumer<String> reply) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer buffer = stack.mallocInt(1);
            glGetProgramiv(program, GL_LINK_STATUS, buffer);

            if (buffer.get(0) != 1) {
                reply.accept(glGetProgramInfoLog(program));
            } else {
                LogManager.getLogger().info("Linking was successful");
            }
        }
    }
}
