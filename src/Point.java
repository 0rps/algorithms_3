import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * Created by orps on 10/22/16.
 */
public class Point implements Comparable<Point> {

    private class PointComparator implements Comparator<Point>
    {
        private Point point;

        public PointComparator(Point point)
        {
            this.point = point;
        }

        @Override
        public int compare(Point pt, Point t1) {

            double s1 = this.point.slopeTo(pt);
            // StdOut.println("second point = " + t1.toString());
            double s2 = this.point.slopeTo(t1);

            long i1 = Math.round(s1 * 10000.0);
            long i2 = Math.round(s2 * 10000.0);

            int result = 1;
//            if (point.x == t1.x)
//                return 0;
//            else if (point.x < t1.x)
//                return -1;
//            else
//                return 1;
            if (i1 == i2)
                result = 0;
            else if (i1 < i2)
                result = -1;

            // StdOut.printf("comparator result = %d\n---------------\n", result);

            return result;
        }
    }

    private int x;
    private int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() { return ("(" + x + ", " + y + ")"); }

    public int compareTo(Point that)
    {
        if (y == that.y) {
            if (x == that.x)
                return 0;

            if (x < that.x)
                return -1;

        } else if (y < that.y) {
            return -1;
        }

        return 1;
    }


    public double slopeTo(Point that)
    {
        return slopeTo(that, false);
    }

    private double slopeTo(Point that, boolean isShow)
    {
        double result;

        if (compareTo(that) == 0) {
            // if (isShow) StdOut.println("SlopeTo: is equal, returning -inf");
            result = Double.NEGATIVE_INFINITY;
        } else if (that.y == y) {
            // if (isShow) StdOut.println("SlopeTo: at one y-axis, returning 0");
            result =  0.0;
        } else if (that.x == x) {
            // if (isShow) StdOut.println("SlopeTo: at one x-axis, +inf");
            result = Double.POSITIVE_INFINITY;
        } else {
            result = (double) (that.y - y) / (double) (that.x - x);
            // if (isShow) StdOut.printf("SlopeTo: %f\n", result);
        }

        return result;
    }

    public Comparator<Point> slopeOrder()
    {
        return new PointComparator(this);
    }
}