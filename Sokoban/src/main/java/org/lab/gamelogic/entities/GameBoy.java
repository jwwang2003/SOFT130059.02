package org.lab.gamelogic.entities;

import org.lab.gamelogic.Position;

import java.io.Serializable;

public class GameBoy extends Movable {

    public GameBoy() {}
    public GameBoy(Position position) {
        super(position);
    }

    @Override
    public String toString() {
        return "Entity [GameBoy] " + super.position;
    }
}
