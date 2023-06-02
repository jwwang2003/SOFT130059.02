package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.Map;
import com.example.lab10.gamelogic.Entity;
import com.example.lab10.gamelogic.MapIcon;
import com.example.lab10.gamelogic.Position;
import com.example.lab10.gamelogic.movement.Direction;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Wall extends Entity implements MapIcon {
    static Image wallIcon;
    Color boarderColor;

    public Wall() {}
    public Wall(Position position) {
        super(position);
    }

    @Override
    public boolean pushBy(Map map, Entity element, Direction dir) {
        return false;
    }

    public Image getImage() { return wallIcon; }
    public Color getBorderColor() { return this.boarderColor; }

    static public void setIcon(String str, double width, double height) {
        wallIcon = new Image(str, width, height, true, true);
    }

    @Override
    public String toString() {
        return "Entity [Wall] " + super.position;
    }
}
