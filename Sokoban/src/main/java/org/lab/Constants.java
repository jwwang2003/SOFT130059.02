package org.lab;

public class Constants {
    static public final double canvasWidth = 1000;
    static public final double canvasHeight = 1000;

    static public final int canvasRowCount = 10;
    static public final int canvasColCount = 10;

    static public double getCanvasBlockWidth() { return canvasWidth/canvasColCount; }
    static public double getCanvasBlockHeight() { return canvasHeight/canvasRowCount; }
}
