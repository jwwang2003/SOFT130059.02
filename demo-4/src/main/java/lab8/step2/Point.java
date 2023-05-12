package lab8.step2;

import java.lang.Math;

interface xComponent {
  public double getX();
  public void setX(double x);
}

interface yComponent {
  public double getY();
  public void setY(double y);
}

interface zComponent {
  public double getZ();
  public void setZ(double z);
}

abstract class Coordinate implements xComponent, yComponent {
  private double x;
  private double y;

  abstract double compareCoordinate(Coordinate c);
  static double compareCoordinate(Coordinate c1, Coordinate c2) {
    double t = Math.pow(c2.getX() - c1.getX(), 2) + Math.pow(c2.getY() - c1.getY(), 2);
    double displacement = Math.sqrt(t);

    return displacement;
  }

  public double getX() { return this.x; }
  public void setX(double x) { this.x = x; }
  
  public double getY() { return this.y; }
  public void setY(double y) { this.y = y; }
}

public class Point extends Coordinate {  
  public Point(double x, double y) {
    super.setX(x);
    super.setY(y);
  }

  public double compareCoordinate(Coordinate c) {
    return compareCoordinate(this, c);
  }
}
