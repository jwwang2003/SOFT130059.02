package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.Map;
import com.example.lab10.gamelogic.Entity;
import com.example.lab10.gamelogic.movement.Direction;
import javafx.scene.image.Image;

public class Obstacle extends Entity implements Overlap {
    static Image mapIcon;

    public Obstacle() {}

    @Override
    public boolean pushBy(Map map, Entity element, Direction dir) {
        return !(element instanceof Box);
    }

    public Image getImage() { return mapIcon; }

    static public void setIcon(String str, double width, double height) {
        mapIcon = new Image(str, width, height, true, true);
    }

    @Override
    public String toString() {
        return "Entity [Obstacle] " + super.getPosition();
    }
}
