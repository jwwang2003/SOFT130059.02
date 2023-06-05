package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.*;
import com.example.lab10.gamelogic.movement.Direction;
import javafx.scene.image.Image;

public class Box extends Movable {
    static Image mapIcon;
    public Box() {}

    public Box(Position position) {
        super(position);
    }

    @Override
    public boolean pushBy(Map map, Entity element, Direction dir) {
        if (element instanceof Box)
            return false;
        return super.pushBy(map, element, dir);
    }

    public String toString() {
        return "Entity [Box] " + super.getPosition();
    }

    public Image getImage() { return mapIcon; }

    static public void setIcon(String str, double width, double height) {
        mapIcon = new Image(str, width, height, true, true);
    }
}
