package lab8.step2;

public class Circle extends GeometricObject {
    private double radius;

    public Circle(int color, double radius, double x, double y) {
        super(color);
        this.radius = radius;
        this.setX(x);
        this.setY(y);
    }

    public Circle(int color, String radius, String x, String y) {
        super(color);
        this.radius = Double.parseDouble(radius);
        this.setX(Double.parseDouble(x));
        this.setY(Double.parseDouble(y));
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
        return "C " + this.getX() + " " + this.getY() + " " + radius;
    }

}
