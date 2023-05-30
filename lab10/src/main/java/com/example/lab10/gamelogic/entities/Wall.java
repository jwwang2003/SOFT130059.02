package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.Map;
import com.example.lab10.gamelogic.MapEntity;
import com.example.lab10.gamelogic.Position;
import com.example.lab10.gamelogic.movement.Direction;

public class Wall extends MapEntity {
    public Wall() {}
    public Wall(Position position) {
        super(position);
    }

    @Override
    public boolean pushBy(Map map, MapEntity element, Direction dir) {
        return false;
    }

    @Override
    public String toString() {
        return "Entity [Wall] " + super.position;
    }
}
