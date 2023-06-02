package org.lab.gamelogic.entities;

import org.lab.gamelogic.Entity;
import org.lab.gamelogic.Map;
import org.lab.gamelogic.Position;
import org.lab.gamelogic.movement.Direction;

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
        return "Entity [Space] " + super.position;
    }
}
