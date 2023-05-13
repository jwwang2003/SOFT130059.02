package lab8.step2;

public class Rectangle extends GeometricObject {
    private double x, y, height, width;

    public Rectangle(int color, double x, double y, double height, double width) {
        super(color);
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        super.setX(x + (height/2.0));
        super.setY(y + (width/2.0));
    }

    public Rectangle(int color, String x, String y, String height, String width) {
        super(color);
        this.height = Double.parseDouble(height);
        this.width = Double.parseDouble(width);
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        super.setX(this.x - (this.height/2.0));
        super.setY(this.y + (this.width/2.0));
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
