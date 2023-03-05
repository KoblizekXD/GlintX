package com.koblizek.glintx.api.input;

import java.util.Arrays;

public enum Mouse {
    MOUSE_BUTTON_1(0),
    MOUSE_BUTTON_2(1),
    MOUSE_BUTTON_3(2),
    MOUSE_BUTTON_4(3),
    MOUSE_BUTTON_5(4),
    MOUSE_BUTTON_6(5),
    MOUSE_BUTTON_7(6),
    MOUSE_BUTTON_8(7),
    MOUSE_BUTTON_LAST(MOUSE_BUTTON_8.getLwjgl3_int()),
    MOUSE_BUTTON_LEFT(MOUSE_BUTTON_1.getLwjgl3_int()),
    MOUSE_BUTTON_RIGHT(MOUSE_BUTTON_2.getLwjgl3_int()),
    MOUSE_BUTTON_MIDDLE(MOUSE_BUTTON_3.getLwjgl3_int());

    private final int lwjgl3_int;

    Mouse(int lwjgl3_int) {
        this.lwjgl3_int = lwjgl3_int;
    }

    public int getLwjgl3_int() {
        return lwjgl3_int;
    }
    public static Mouse getKeyById(int id) {
        return Arrays.stream(Mouse.values()).filter(key -> key.lwjgl3_int == id).findFirst().orElse(null);
    }
}
