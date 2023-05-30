package oop.lab.gamelogic;

import java.util.Objects;

import oop.lab.gamelogic.movement.Direction;
import oop.lab.gamelogic.movement.Displacement;
import oop.lab.gamelogic.vectorspace.TwoComponents;

public class Position extends TwoComponents {
  public static Position UP = new Position(Displacement.get(Direction.up));
  public static Position DOWN = new Position(Displacement.get(Direction.down));
  public static Position LEFT = new Position(Displacement.get(Direction.left));
  public static Position RIGHT = new Position(Displacement.get(Direction.right));
  
  private int x, y;
  private int hashCode;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
    this.hashCode = Objects.hash(x, y);
  }

  public Position(int []d) {
    x = d[0];
    y = d[1];
    this.hashCode = Objects.hash(x, y);
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public Position at(Direction dir) {
    Position move = null;
    switch (dir) {
        case up:
            move = UP;
            break;
        case down:
            move = DOWN;
            break;
        case left:
            move = LEFT;
            break;
        case right:
            move = RIGHT;
            break;
    }
    return new Position(x + move.x, y + move.y);
  }

  @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position p = (Position) obj;
            return p.getX() == this.x && p.getY() == this.y;
        }
        return false;
    }

  @Override
  public String toString() {
      return "Position [x=" + getX() + ", y=" + getY() + "]";
  }

  @Override
    public int hashCode() {
        return this.hashCode;
    }
}
