package com.example.lab10.gamelogic;

import java.io.Serializable;

public class SerializableColor implements Serializable {
    private double red;
    private double green;
    private double blue;
    private double opacity;

    public SerializableColor(javafx.scene.paint.Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacity = color.getOpacity();
    }

    public javafx.scene.paint.Color toJavaFXColor() {
        return new javafx.scene.paint.Color(red, green, blue, opacity);
    }

    public void setColor(javafx.scene.paint.Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacity = color.getOpacity();
    }

    public void setColor(double red, double green, double blue, double opacity) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
    }

    public double getRed() { return this.red; }
    public double getGreen() { return this.green; }
    public double getBlue() { return this.blue; }
}