package com.koblizek.glintx;

import com.koblizek.glintx.main.GLMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.apache.logging.log4j.LogManager.getLogger;

public class Main {
    public static void main(String[] args) {
        // starts the application
        try {
            new GLMain().run();
        } catch (Exception e) {
            getLogger().fatal("An unhandled exception occurred:", e);
        }
    }
}
