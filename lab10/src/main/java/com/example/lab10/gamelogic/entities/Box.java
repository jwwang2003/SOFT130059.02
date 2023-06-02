package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.*;
import com.example.lab10.gamelogic.movement.Direction;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Box extends Movable {
    static Image mapIcon;
    Color boarderColor;
    public Box() {}

    public Box(Position position) {
        super(position);
    }

    public Box(Position position, Color boarderColor) {
        super(position);
        this.boarderColor = boarderColor;
    }

    @Override
    public boolean pushBy(Map map, Entity element, Direction dir) {
        if (element instanceof Box)
            return false; // 只能推动一个箱子
        return super.pushBy(map, element, dir);
    }

    public String toString() {
        return "Entity [Box] " + super.position;
    }

    public Image getImage() { return mapIcon; }
    public Color getBorderColor() { return this.boarderColor; }

    static public void setIcon(String str, double width, double height) {
        mapIcon = new Image(str, width, height, true, true);
    }
}