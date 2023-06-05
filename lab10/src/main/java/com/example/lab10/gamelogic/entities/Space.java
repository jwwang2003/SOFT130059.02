package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.Map;
import com.example.lab10.gamelogic.Entity;
import com.example.lab10.gamelogic.Position;
import com.example.lab10.gamelogic.movement.Direction;
import javafx.scene.image.Image;

public class Space extends Entity implements Overlap {
    public Space() {}
    public Space(Position position) {
        super(position);
    }

    @Override
    public boolean pushBy(Map map, Entity element, Direction dir) {
        return true;
    }

    @Override
    public String toString() {
        return "Entity [Space] " + super.getPosition();
    }

    @Override
    public Image getImage() {
        return null;
    }
}
