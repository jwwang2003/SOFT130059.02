package lab8.step1;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    
    String buffer = "";

    ArrayList<Point> points = new ArrayList<>();
    
    while(!buffer.equals("END")) {
      buffer = in.nextLine();

      Pattern pattern = Pattern.compile("\\{([−-]?[0-9]*\\.?[0-9]+), ([−-]?[0-9]*\\.?[0-9]+)\\}");
      Matcher m = pattern.matcher(buffer);

      if(m.find()) {
        points.add(new Point(Float.parseFloat(m.group(1)), Float.parseFloat(m.group(2))));
      }
    }

    for(int i = 0; i < points.size(); ++i) {
      Point base = points.get(i);
      double cur = Double.MAX_VALUE;
      Point closest = null;
      for(int j = 0; j < points.size(); ++j) {
        Point target = points.get(j);
        double diff = base.compareCoordinate(target);
        if(diff == 0) continue;
        if(diff < cur) {
          closest = target;
          cur = diff;
        }
      }

      if(closest != null)
        System.out.println("{" + closest.getX() + "," + closest.getY() + "}");
    }

    in.close();
    return;
  }
}
