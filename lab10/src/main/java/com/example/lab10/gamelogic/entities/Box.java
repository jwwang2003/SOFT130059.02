package com.example.lab10.gamelogic.entities;

import com.example.lab10.gamelogic.Map;
import com.example.lab10.gamelogic.MapEntity;
import com.example.lab10.gamelogic.Position;
import com.example.lab10.gamelogic.movement.Direction;

public class Box extends Movable {
    public Box() {}
    public Box(Position position) {
        super(position);
    }

    @Override
    public boolean pushBy(Map map, MapEntity element, Direction dir) {
        if (element instanceof Box)
            return false; // 只能推动一个箱子
        return super.pushBy(map, element, dir);
    }

    public String toString() {
        return "Entity [Box] " + super.position;
    }
}
