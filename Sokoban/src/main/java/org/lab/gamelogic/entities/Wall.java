package org.lab.gamelogic.entities;

import org.lab.gamelogic.Map;
import org.lab.gamelogic.MapEntity;
import org.lab.gamelogic.Position;
import org.lab.gamelogic.movement.Direction;

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
