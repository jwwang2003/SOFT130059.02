package org.lab.gamelogic.entities;

import org.lab.gamelogic.Entity;
import org.lab.gamelogic.Map;
import org.lab.gamelogic.Position;
import org.lab.gamelogic.movement.Direction;

public class Goal extends Entity {
    public Goal() {}
    public Goal(Position position) {
        super(position);
    }

    @Override
    public boolean pushBy(Map map, Entity element, Direction dir) {
        return false;
    }
    @Override
    public String toString() {
        return "Entity [Goal] " + super.position;
    }
}
