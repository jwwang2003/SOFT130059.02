package com.example.lab10.gamelogic;

import com.example.lab10.gamelogic.entities.GameBoy;
import com.example.lab10.gamelogic.entities.Space;
import com.example.lab10.gamelogic.movement.Direction;

import java.util.ArrayList;
import java.util.List;

public class Map {
    List<Position> mapPositions = new ArrayList<>();
    private Position finalPosition;
    private GameBoy gameBoy;

    public Map(List<Position> elements, Position finalPosition, GameBoy gameBoy) {
        this.mapPositions.addAll(elements);
        this.finalPosition = finalPosition;
        this.gameBoy = gameBoy;
//        this.getPosition(gameBoy.getPosition()).pushEntity(gameBoy);
    }

    public MapEntity getElementAt(Position pos) {
        Position position = this.getPosition(pos);

        if(position.getAllEntities().size() == 0) return new Space(pos);
        return position.peak();
    }

    public Position getPosition(Position pos) {
        Position position = (mapPositions.stream().filter(
                (_pos) -> _pos.equals(pos)
        ).findFirst().orElse(null));

        if(position == null) {
            mapPositions.add(pos);
            position = pos;
        }

        return position;
    }

    public void moveGameBoy(Direction dir) {
        boolean result = gameBoy.pushBy(this, null, dir);
    }
}
