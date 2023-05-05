package PushBox;

import java.util.ArrayList;

public class Array<T> {
  private ArrayList<
    ArrayList<
      ArrayList<T>
    >
  > matrix;  // 3D matrix
  // Accessing said matrix => [layer][x][y]

  public Array() {
    matrix = new ArrayList<>();
  }

  

  ArrayList<ArrayList<T>> getLayer(int layer) {
    return matrix.get(layer);
  }

  boolean push(int x, int y) {
    if(m)
  }
}