package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.Position;
import javafx.scene.image.Image;

import java.io.Serializable;

public class GameBoy extends Movable implements Serializable {
    static Image mapIcon;

    public GameBoy() {}
    public GameBoy(Position position) {
        super(position);
    }

    public Image getImage() { return mapIcon; }

    static public void setIcon(String str, double width, double height) {
        mapIcon = new Image(str, width, height, true, true);
    }

    @Override
    public String toString() {
        return "Entity [GameBoy] " + super.getPosition();
    }
}
