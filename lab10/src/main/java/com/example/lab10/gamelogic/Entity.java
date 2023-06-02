package com.example.lab10.gamelogic;

import com.example.lab10.Constants;
import com.example.lab10.gamelogic.movement.Direction;

import java.io.Serializable;

public abstract class Entity implements MapIcon, Serializable {
    // pointer
    protected Position position;

    public Entity() {

    }

    public Entity(Position position) {
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

    public abstract boolean pushBy(Map map, Entity element, Direction dir);
}

