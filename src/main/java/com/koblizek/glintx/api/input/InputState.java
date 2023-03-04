package com.koblizek.glintx.api.input;

public enum InputState {
    PRESS(1),
    RELEASE(0),
    HELD(2);

    private final int lwjgl3_value;

    InputState(int lwjgl3_value) {
        this.lwjgl3_value = lwjgl3_value;
    }

    public int getLwjgl3Value() {
        return lwjgl3_value;
    }
}
