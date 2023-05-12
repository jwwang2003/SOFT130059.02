package lab8.step2;

import java.util.ArrayList;

public class main {
  public static void main(String[] args) {
    Canvas canvas = new Canvas();

    ArrayList<GeometricObject> arr = new ArrayList<>();
    arr.add(new Circle(0, 1, 1, 2));
    arr.add(new Rectangle(0, 2, 2, 8, 10));
    arr.add(new Rectangle(0, 2, 2.5, 1, 1));
    arr.add(new Circle(0, 3, 3, 9));

    canvas.add(arr.get(0));
    canvas.add(arr.get(1));
    canvas.add(arr.get(2));
    canvas.add(arr.get(3));

    for(GeometricObject obj: arr) {
      // System.out.println(obj);
      System.out.println(canvas.getNearestObjectOf(obj));
    }
  }
}
