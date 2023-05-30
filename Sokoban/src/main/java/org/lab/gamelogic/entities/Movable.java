package org.lab.gamelogic.entities;

import org.lab.gamelogic.MapEntity;
import org.lab.gamelogic.Map;
import org.lab.gamelogic.Position;
import org.lab.gamelogic.movement.Direction;

public abstract class Movable extends MapEntity {
    public Movable() {}
    public Movable(Position position) {
        super(position);
    }

    void moveTo(Direction dir) {
        position = position.at(dir);
    }
    @Override
    public boolean pushBy(Map map, MapEntity element, Direction dir) {
        Position target = map.getPosition(this.getPosition().at(dir));
        Position current = map.getPosition(this.getPosition());
        MapEntity nextElement = map.getElementAt(this.getPosition().at(dir));

        MapEntity targetElement = map.getElementAt(target);
        MapEntity currentElement = this;

        if (nextElement.pushBy(map, currentElement, dir)) {
            target.popEntity(targetElement);
            nextElement = current.popEntity();

            if(!(nextElement instanceof Space))
                target.pushEntity(nextElement);
            this.moveTo(dir);
            return true;
        } else {
            return false;
        }
    }
}
