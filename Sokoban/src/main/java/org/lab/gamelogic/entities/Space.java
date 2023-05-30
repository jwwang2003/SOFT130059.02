package org.lab.gamelogic.entities;

import org.lab.gamelogic.Map;
import org.lab.gamelogic.MapEntity;
import org.lab.gamelogic.Position;
import org.lab.gamelogic.movement.Direction;

public class Space extends MapEntity {
    public Space() {}
    public Space(Position position) {
        super(position);
    }

    @Override
    public boolean pushBy(Map map, MapEntity element, Direction dir) {
        return true;
    }

    @Override
    public String toString() {
        return "Entity [Space] " + super.position;
    }
}
