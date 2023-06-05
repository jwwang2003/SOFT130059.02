package com.example.lab10.gamelogic;

import java.io.Serializable;

public class SerializableColor implements Serializable {
    private final double red;
    private final double green;
    private final double blue;
    private final double opacity;

    public SerializableColor(javafx.scene.paint.Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacity = color.getOpacity();
    }

    public javafx.scene.paint.Color toJavaFXColor() {
        return new javafx.scene.paint.Color(red, green, blue, opacity);
    }
}