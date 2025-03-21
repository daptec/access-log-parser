package ru.courses.geometry;

public class PolyLine implements Measurable{
    Point[] points;

    public PolyLine(Point... points) {
        this.points = points;
    }

    public double getLength() {
        double sum = 0, len1, len2;
        for (int i = 0; i < points.length - 1; i++) {
            len1 = points[i].x - points[i + 1].x;
            len2 = points[i].y - points[i + 1].y;
            sum += Math.sqrt(len1 * len1 + len2 * len2);
        }
        return sum;
    }
}
