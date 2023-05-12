package lab8.step2;

abstract public class GeometricObject extends Coordinate {
    private int color;

    public GeometricObject(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public double compareCoordinate(Coordinate c) {
        return compareCoordinate(this, c);
    }

    abstract public double getArea();

}
