package com.example.lab10.gamelogic.movement;

public class Displacement {
  // [row, col]
  // [y, x]
  public final static int[][] d = {
    {0, -1},  // left
    {1, 0},   // down
    {-1, 0},  // up
    {0, 1},   // right
  };

  static public int[] get(Direction dir) {
    return switch (dir) {
      case left -> d[0];
      case down -> d[1];
      case up -> d[2];
      case right -> d[3];
    };
  }

  static public int[] get(InputDirection iDir) {
    int index = iDir.ordinal();
    return d[index];
  }
}
