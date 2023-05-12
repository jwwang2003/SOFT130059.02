package lab8.step2;

public class Rectangle extends GeometricObject {
    private double x, y, height, width;

    public Rectangle(int color, double x, double y, double height, double width) {
        super(color);
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    @Override
    public double getArea() {
        return height * width;
    }

    @Override
    public String toString() {
        return "R " + x + " " + y + " " + height + " " + width;
    }

}
