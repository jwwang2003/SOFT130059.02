package lab8.step2;

import java.util.ArrayList;

public class Canvas {
    private ArrayList<GeometricObject> gobjects = new ArrayList<>();

    public GeometricObject add(GeometricObject object) {
        gobjects.add(object);
        return object;
    }

    public double getTotalArea() {
        double totalArea = 0;
        for (GeometricObject o : gobjects) {
            totalArea += o.getArea();
        }
        return totalArea;
    }

    public GeometricObject getNearestObjectOf(GeometricObject obj) {
        double cur = Double.MAX_VALUE;
        GeometricObject g = null;

        for(GeometricObject gobject : gobjects) {
            if(gobject.equals(obj)) continue;
            double t = gobject.compareCoordinate(obj);
            
            if(t < cur) {
                g = gobject;
                cur = t;
            }
        }

        return g;
    }
}
