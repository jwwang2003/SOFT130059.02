package org.lab.gamelogic.entities;

import org.lab.gamelogic.Entity;
import org.lab.gamelogic.Map;
import org.lab.gamelogic.Position;
import org.lab.gamelogic.movement.Direction;

public class Obstacle extends Entity implements Overlap {

    public Obstacle() {}
    public Obstacle(Position position) {
        super(position);
    }

    @Override
    public boolean pushBy(Map map, Entity element, Direction dir) {
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
