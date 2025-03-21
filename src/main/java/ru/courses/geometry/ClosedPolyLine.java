package ru.courses.geometry;

public class ClosedPolyLine extends PolyLine implements Measurable{
    public ClosedPolyLine(Point... points) {
        super(points);
    }

    @Override
    public double getLength() {
        double sum = super.getLength();
        if (points.length > 1) {
            double lenX = points[0].x - points[points.length - 1].x;
            double lenY = points[0].y - points[points.length - 1].y;
            sum += Math.sqrt(lenX * lenX + lenY * lenY);
        }

        return sum;
    }
}
