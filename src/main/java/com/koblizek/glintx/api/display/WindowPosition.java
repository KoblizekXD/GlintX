package com.koblizek.glintx.api.display;

public enum WindowPosition {
    CENTER(2);
    private final int division;

    WindowPosition(int division) {
        this.division = division;
    }

    public int getDivision() {
        return division;
    }
}
