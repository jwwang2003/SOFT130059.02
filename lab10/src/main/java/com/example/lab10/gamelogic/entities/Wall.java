package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.Map;
import com.example.lab10.gamelogic.Entity;
import com.example.lab10.gamelogic.MapIcon;
import com.example.lab10.gamelogic.movement.Direction;
import javafx.scene.image.Image;

public class Wall extends Entity implements MapIcon {
    static Image wallIcon;

    public Wall() {}

    @Override
    public boolean pushBy(Map map, Entity element, Direction dir) {
        return false;
    }

    public Image getImage() { return wallIcon; }

    static public void setIcon(String str, double width, double height) {
        wallIcon = new Image(str, width, height, true, true);
    }

    @Override
    public String toString() {
        return "Entity [Wall] " + super.getPosition();
    }
}
