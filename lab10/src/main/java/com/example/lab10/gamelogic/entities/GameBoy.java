package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.Position;

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
