package org.lab.gamelogic;

import org.lab.gamelogic.movement.Direction;

public abstract class MapEntity {
    // pointer
    protected Position position;

    public MapEntity() {

    }

    public MapEntity(Position position) {
        position.pushEntity(this);
        this.position = position;
    }
    public Position getPosition() {
        return this.position;
    }
    public void setPosition(Position pos) {
        if(this.position != null) {
            this.position.popEntity(this);
        }

        pos.pushEntity(this);
        this.position = pos;
    }

    public abstract boolean pushBy(Map map, MapEntity element, Direction dir);
}

