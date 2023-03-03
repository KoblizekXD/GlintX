package com.koblizek.glintx.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InvokableList<H> extends ArrayList<Consumer<H>> {
    public void invokeAll(H key) {
        for (Consumer<H> item : this) {
            item.accept(key);
        }
    }
}
