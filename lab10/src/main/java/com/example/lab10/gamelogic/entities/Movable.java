package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.Map;
import com.example.lab10.gamelogic.Entity;
import com.example.lab10.gamelogic.Position;
import com.example.lab10.gamelogic.movement.Direction;

public abstract class Movable extends Entity {
    public Movable() {}
    public Movable(Position position) {
        super(position);
    }

    void moveTo(Direction dir) {
        super.setPosition(getPosition().at(dir));
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

            // when a box reaches the goal, it gets pushed under the goal with
            // _pushEntity, so that it disappears from the game map signifying that
            // the player has completed the task
            if(nextEntity instanceof Goal && curEntity instanceof Box) {
                // have to make sure that only the box gets pushed under the goal
                targetPosition = map.getPosition(this.getPosition().at(dir), false);
                targetPosition._pushEntity(curEntity);

                this.moveTo(dir);
                super.getPlayerSession().checkGameEnd();
            }
            else if((nextEntity instanceof Overlap) && curEntity instanceof Movable) {
                targetPosition = map.getPosition(this.getPosition().at(dir), true);
                targetPosition.pushEntity(curEntity);
                this.moveTo(dir);
            }
            return true;
        } else {
            return false;
        }
    }
}
