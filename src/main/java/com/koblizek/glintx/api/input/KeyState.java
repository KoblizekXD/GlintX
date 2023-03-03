package com.koblizek.glintx.api.input;

public enum KeyState {
    PRESS(1),
    RELEASE(0),
    HELD(2);

    private final int lwjgl3_value;

    KeyState(int lwjgl3_value) {
        this.lwjgl3_value = lwjgl3_value;
    }

    public int getLwjgl3Value() {
        return lwjgl3_value;
    }
}
