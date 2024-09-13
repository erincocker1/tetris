package org.example;

import java.awt.*;

public enum TileType {
    BLANK(Color.black),
    I(Color.white),
    L(Color.red),
    J(Color.blue),
    S(Color.magenta),
    Z(Color.yellow),
    O(Color.cyan),
    T(Color.pink);

    private final Color color;

    TileType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}

