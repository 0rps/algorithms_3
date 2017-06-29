import edu.princeton.cs.algs4.StdOut;

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
        public int compare(Point point, Point t1) {

            StdOut.println("------------\nComparator: zero point = " + this.point.toString());
            StdOut.println("first point = " + point.toString());
            double s1 = this.point.slopeTo(point);
            StdOut.println("second point = " + t1.toString());
            double s2 = this.point.slopeTo(t1);

            int result = 1;
//            if (point.x == t1.x)
//                return 0;
//            else if (point.x < t1.x)
//                return -1;
//            else
//                return 1;
            if (s1 == s2)
                result = 0;
            else if (s1 < s2)
                result = -1;
            else
                result = 1;

            StdOut.printf("comparator result = %d\n---------------\n", result);

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

    public void draw() { }
    public void drawTo(Point that) { }

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

    public double slopeTo(Point that, boolean isShow)
    {
        double result;

        if (compareTo(that) == 0) {
            if (isShow) StdOut.println("SlopeTo: is equal, returning -inf");
            result = Double.NEGATIVE_INFINITY;
        } else if (that.y == y) {
            if (isShow) StdOut.println("SlopeTo: at one y-axis, returning 0");
            result =  0.0;
        } else if (that.x == x) {
            if (isShow) StdOut.println("SlopeTo: at one x-axis, +inf");
            result = Double.POSITIVE_INFINITY;
        } else {
            result = (double) (that.y - y) / (double) (that.x - x);
            if (isShow) StdOut.printf("SlopeTo: %f\n", result);
        }

        return result;
    }

    public Comparator<Point> slopeOrder()
    {
        return new PointComparator(this);
    }
}