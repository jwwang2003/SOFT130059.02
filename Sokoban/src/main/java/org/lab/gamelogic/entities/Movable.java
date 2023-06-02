package org.lab.gamelogic.entities;

import org.lab.gamelogic.Entity;
import org.lab.gamelogic.Map;
import org.lab.gamelogic.Position;
import org.lab.gamelogic.movement.Direction;

public abstract class Movable extends Entity {
    public Movable() {}
    public Movable(Position position) {
        super(position);
    }

    void moveTo(Direction dir) {
        position = position.at(dir);
    }

    @Override
    public boolean pushBy(Map map, Entity element, Direction dir) {
        Position curPosition = map.getPosition(this.getPosition());
        Entity curEntity = map.getElementAt(curPosition);

        Position targetPosition = map.getPosition(this.getPosition().at(dir));
        Entity nextEntity = map.getElementAt(targetPosition);

        if (nextEntity != null && nextEntity.pushBy(map, curEntity, dir)) {
            curEntity = map.popPosition(curPosition);

            nextEntity = map.getElementAt(targetPosition);
            if(nextEntity instanceof Obstacle) targetPosition = map.getPosition(this.getPosition().at(dir), true);

            targetPosition = map.getPosition(this.getPosition().at(dir));
            if((nextEntity instanceof Overlap) && curEntity instanceof Movable) {
                targetPosition.pushEntity(curEntity);
            }
            this.moveTo(dir);
            return true;
        } else {
            return false;
        }
    }
}
