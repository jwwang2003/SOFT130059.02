package com.example.lab10.gamelogic;

import com.example.lab10.gamelogic.movement.Direction;
import com.example.lab10.gamelogic.movement.Displacement;
import com.example.lab10.gamelogic.vectorspace.TwoComponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position extends TwoComponents implements Serializable {
    public static Position UP = new Position(Displacement.get(Direction.up));
    public static Position DOWN = new Position(Displacement.get(Direction.down));
    public static Position LEFT = new Position(Displacement.get(Direction.left));
    public static Position RIGHT = new Position(Displacement.get(Direction.right));

    private final int row;
    private final int col;
    private final int hashCode;
    private final List<Entity> children = new ArrayList<>();
    static private final List<Position> globalPositions = new ArrayList<>();
    public Position(int row, int col, boolean isStatic) {
        this.col = col;
        this.row = row;
        this.hashCode = Objects.hash(row, col);

        if(isStatic)
            if(!globalPositions.contains(this)) globalPositions.add(this);
    }

    public Position(int []d) {
        row = d[0];
        col = d[1];
        this.hashCode = Objects.hash(row, col);
    }

    public Position(int row, int col, List<Entity> entities) {
        for(Entity entity : entities) {
            children.add(entity);
            entity.setPosition(this);
        }
//        this.children.addAll(entities);

        this.row = row;
        this.col = col;
        this.hashCode = Objects.hash(row, col);

        if(!globalPositions.contains(this)) globalPositions.add(this);
    }

    public int getRow() {
        return this.row;
    }

    public int getX() { return getRow(); }

    public int getCol() {
        return this.col;
    }

    public int getY() { return getCol(); }

    public List<Entity> getAllEntities() {
        return this.children;
    }

    public String getString() {
        return children.toString();
    }

    public Entity popEntity() {
        int ind = this.children.size() - 1;
        return ind > -1 ? this.children.remove(ind) : null;
    }

    public void popEntity(Entity entity) {
        this.children.remove(entity);
    }

    public void pushEntity(Entity entity) {
        this.children.add(entity);
    }
    public void _pushEntity(Entity entity) { this.children.add(0, entity); }

    public Entity peak() {
        int ind = this.children.size() - 1;
        return ind > -1 ? this.children.get(ind) : null;
    }

    public Position at(Direction dir) {
        Position move = switch (dir) {
            case up -> UP;
            case down -> DOWN;
            case left -> LEFT;
            case right -> RIGHT;
        };

        move = new Position(row + move.row, col + move.col, false);

        return move;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position p) {
            return p.getX() == this.row && p.getY() == this.col;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Position [" + getRow() + ", " + getCol() + "]";
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
