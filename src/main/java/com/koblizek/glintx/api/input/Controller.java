package com.koblizek.glintx.api.input;

import java.util.Arrays;

public enum Controller {
            JOYSTICK_1(0),
            JOYSTICK_2(1),
            JOYSTICK_3(2),
            JOYSTICK_4(3),
            JOYSTICK_5(4),
            JOYSTICK_6(5),
            JOYSTICK_7(6),
            JOYSTICK_8(7),
            JOYSTICK_9(8),
            JOYSTICK_10(9),
            JOYSTICK_11(10),
            JOYSTICK_12(11),
            JOYSTICK_13(12),
            JOYSTICK_14(13),
            JOYSTICK_15(14),
            JOYSTICK_16(15),
            JOYSTICK_LAST (Controller.JOYSTICK_16.getLwjgl3_int()),
            GAMEPAD_BUTTON_A(0),
            GAMEPAD_BUTTON_B(1),
            GAMEPAD_BUTTON_X(2),
            GAMEPAD_BUTTON_Y(3),
            GAMEPAD_BUTTON_LEFT_BUMPER(4),
            GAMEPAD_BUTTON_RIGHT_BUMPER(5),
            GAMEPAD_BUTTON_BACK(6),
            GAMEPAD_BUTTON_START(7),
            GAMEPAD_BUTTON_GUIDE(8),
            GAMEPAD_BUTTON_LEFT_THUMB(9),
            GAMEPAD_BUTTON_RIGHT_THUMB(10),
            GAMEPAD_BUTTON_DPAD_UP(11),
            GAMEPAD_BUTTON_DPAD_RIGHT(12),
            GAMEPAD_BUTTON_DPAD_DOWN(13),
            GAMEPAD_BUTTON_DPAD_LEFT(14),
            GAMEPAD_BUTTON_LAST(Controller.GAMEPAD_BUTTON_DPAD_LEFT.getLwjgl3_int()),
            GAMEPAD_BUTTON_CROSS(Controller.GAMEPAD_BUTTON_A.getLwjgl3_int()),
            GAMEPAD_BUTTON_CIRCLE(Controller.GAMEPAD_BUTTON_B.getLwjgl3_int()),
            GAMEPAD_BUTTON_SQUARE(Controller.GAMEPAD_BUTTON_X.getLwjgl3_int()),
            GAMEPAD_BUTTON_TRIANGLE(Controller.GAMEPAD_BUTTON_Y.getLwjgl3_int()),

            GAMEPAD_AXIS_LEFT_X(0),
            GAMEPAD_AXIS_LEFT_Y(1),
            GAMEPAD_AXIS_RIGHT_X(2),
            GAMEPAD_AXIS_RIGHT_Y(3),
            GAMEPAD_AXIS_LEFT_TRIGGER(4),
            GAMEPAD_AXIS_RIGHT_TRIGGER(5),
            GAMEPAD_AXIS_LAST(Controller.GAMEPAD_AXIS_RIGHT_TRIGGER.getLwjgl3_int()),
            HAT_CENTERED(0),
            HAT_UP(1),
            HAT_RIGHT(2),
            HAT_DOWN(4),
            HAT_LEFT(8),
            HAT_RIGHT_UP(Controller.HAT_RIGHT.getLwjgl3_int() | Controller.HAT_UP.getLwjgl3_int()),
            HAT_RIGHT_DOWN(Controller.HAT_RIGHT.getLwjgl3_int() | Controller.HAT_DOWN.getLwjgl3_int()),
            HAT_LEFT_UP(Controller.HAT_LEFT.getLwjgl3_int()  | Controller.HAT_UP.getLwjgl3_int()),
            HAT_LEFT_DOWN(Controller.HAT_LEFT.getLwjgl3_int()  |Controller. HAT_DOWN.getLwjgl3_int());

    private final int lwjgl3_int;

    Controller(int lwjgl3_int) {
        this.lwjgl3_int = lwjgl3_int;
    }

    public int getLwjgl3_int() {
        return lwjgl3_int;
    }
    public static Controller getKeyById(int id) {
        return Arrays.stream(Controller.values()).filter(key -> key.lwjgl3_int == id).findFirst().orElse(null);
    }
}
