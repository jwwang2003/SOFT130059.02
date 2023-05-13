package lab8.step2;

import java.util.Scanner;

public class main {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    Canvas canvas = new Canvas();

    // Read input
    String buffer = "";
    while(!buffer.equals("END")) {
      buffer = in.nextLine();

      String[] parsed = buffer.split(" ");
      switch(parsed[0]) {
        case "C":
          canvas.add(new Circle(0, parsed[3], parsed[1], parsed[2]));
          break;
        case "R":
          canvas.add(new Rectangle(0, parsed[1], parsed[2], parsed[3], parsed[4]));
          break;
        default:
          break;
      }
    }

    for(GeometricObject obj: canvas.getAllGeometricObjects()) {
      System.out.println(obj);
    }

    in.close();
    return;
  }
}
