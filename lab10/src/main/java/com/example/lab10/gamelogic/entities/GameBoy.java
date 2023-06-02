package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.MapIcon;
import com.example.lab10.gamelogic.Position;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class GameBoy extends Movable implements Serializable {
    static Image mapIcon;
    private Color borderColor;

    public GameBoy() {}
    public GameBoy(Position position) {
        super(position);
    }

    public GameBoy(Position position, Color borderColor) {
        super(position);
        this.borderColor = borderColor;
    }

    public Image getImage() { return mapIcon; }
    public Color getBorderColor() { return this.borderColor; }

    public void setBorderColor(Color c) { borderColor = c; }

    static public void setIcon(String str, double width, double height) {
        mapIcon = new Image(str, width, height, true, true);
    }

    @Override
    public String toString() {
        return "Entity [GameBoy] " + super.position;
    }
}