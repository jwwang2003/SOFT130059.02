package lab8.step2;

public class Circle extends GeometricObject {
    private double x, y;
    private double radius;

    public Circle(int color, double radius, double x, double y) {
        super(color);
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return "C " + x + " " + y + " " + radius;
    }

}
