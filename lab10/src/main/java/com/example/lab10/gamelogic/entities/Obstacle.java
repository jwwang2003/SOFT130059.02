package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.Map;
import com.example.lab10.gamelogic.MapEntity;
import com.example.lab10.gamelogic.Position;
import com.example.lab10.gamelogic.movement.Direction;

public class Obstacle extends MapEntity {
    public Obstacle() {}
    public Obstacle(Position position) {
        super(position);
    }
    @Override
    public boolean pushBy(Map map, MapEntity element, Direction dir) {
        if (element instanceof Box) {
            return false;
        } else
            return true;
    }

    @Override
    public String toString() {
        return "Entity [Obstacle] " + super.position;
    }
}
