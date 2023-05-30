package oop.lab.gamelogic.movement;

public class Displacement {
  public final static int[][] d = {
    {0, -1},  // left
    {1, 0},   // down
    {-1, 0},  // up
    {0, 1},   // right
  };

  static public int[] get(Direction dir) {
    switch(dir) {
      case left:
        return d[0];
      case down:
        return d[1];
      case up:
        return d[2];
      case right:
        return d[3];
      default:
        return null;
    }
  }

  static public int[] get(InputDirection iDir) {
    int index = iDir.ordinal();
    return d[index];
  }

  static public int[] get(Character c) {
    return get(InputDirection.valueOf(Character.toString(c)));
  }
}
