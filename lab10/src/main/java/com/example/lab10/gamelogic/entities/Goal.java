package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.Map;
import com.example.lab10.gamelogic.Entity;
import com.example.lab10.gamelogic.MapIcon;
import com.example.lab10.gamelogic.Position;
import com.example.lab10.gamelogic.movement.Direction;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Goal extends Entity implements MapIcon {
    static Image mapIcon;
    Color boarderColor;

    public Goal() {}
    public Goal(Position position) {
        super(position);
    }

    @Override
    public boolean pushBy(Map map, Entity element, Direction dir) {
        return false;
    }

    public Image getImage() { return mapIcon; }
    public Color getBorderColor() { return this.boarderColor; }

    static public void setIcon(String str, double width, double height) {
        mapIcon = new Image(str, width, height, true, true);
    }

    @Override
    public String toString() {
        return "Entity [Goal] " + super.position;
    }
}
