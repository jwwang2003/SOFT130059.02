package org.lab.gamelogic.entities;

import org.lab.gamelogic.*;
import org.lab.gamelogic.movement.Direction;

public class Box extends Movable {
    public Box() {}

    public Box(Position position) {
        super(position);
    }

    @Override
    public boolean pushBy(Map map, Entity element, Direction dir) {
        if (element instanceof Box)
            return false; // 只能推动一个箱子
        return super.pushBy(map, element, dir);
    }

    public String toString() {
        return "Entity [Box] " + super.position;
    }
}
