package org.lab.gamelogic.entities;

import org.lab.gamelogic.MapEntity;
import org.lab.gamelogic.Map;
import org.lab.gamelogic.Position;
import org.lab.gamelogic.movement.Direction;

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
