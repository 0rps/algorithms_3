import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.Comparator;

/**
 * Created by orps on 10/22/16.
 */
public class FastCollinearPoints {

    private LineSegment[] segments;
    private int segmentsCount;

    private Point[] startPoints;
    private Point[] endPoints;
    private int pointCount;

    public static void main(String[] args)
    {
        String filename = args[0];

        In in = new In(filename);
        int[] ints = in.readAllInts();

        int count = ints[0];
        if (ints.length < (count*2 + 1))
            throw new java.lang.IllegalArgumentException();

        Point[] points = new Point[count];
        for (int i = 0; i < count; i++) {
            Point point = new Point(ints[i*2+1], ints[i*2+2]);
            for (int j = 0; j < i; j++)
                if (points[j].compareTo(point) == 0)
                    throw new java.lang.IllegalArgumentException();
            points[i] = point;
        }

//        Point[] points = new Point[] {
//                new Point(14, 10),
//                new Point(14, 15),
//                new Point(19, 10),
//                new Point(18, 10),
//                new Point(32, 10),
//                new Point(70, 10),
//                new Point(60, 10),
//                new Point(20, 20),
//                new Point(10,10),
//                new Point(30, 30),
//                new Point(40, 40)
//        };
//        StdOut.println();
//        for (int i = 0; i < points.length; i++) {
//            StdOut.print(points[i]);
//            StdOut.print(" ");
//        }
//        StdOut.println();
//        sort(points, points[0].slopeOrder(), points[0], 0, points.length-1);
//        StdOut.println();
//        for (int i = 0; i < points.length; i++) {
//            StdOut.print(points[i]);
//            StdOut.print(" ");
//        }

        FastCollinearPoints fcp = new FastCollinearPoints(points);
        LineSegment[] segments = fcp.segments();
        for (int i = 0; i < fcp.numberOfSegments(); i++)
        {
            LineSegment sg = segments[i];
            StdOut.println(sg.toString());
        }

    }

    private static void sort(Point[] points, Comparator<Point> cp, int start, int stop)
    {
        if (start >= stop)
            return;

        Point middlePoint = points[start];
        int i = start;
        int j = stop+1;

        while (true)
        {
            while (cp.compare(points[--j], middlePoint) > 0) {
                if (j == start) break;
            }

            while (cp.compare(points[++i], middlePoint) < 0) {
                if (i == stop) break;
            }

            if (i >= j)
                break;

            swap(points, i, j);
        }

        swap(points, j, start);
        sort(points, cp, start, j-1);
        sort(points, cp, j+1, stop);
    }

    private static void swap(Point[] points, int i, int j) {
        Point temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }

    public FastCollinearPoints(Point[] points) {

        if (points == null)
            throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            for (int j = i+1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();

        segments = new LineSegment[points.length];
        segmentsCount = 0;

        startPoints = new Point[points.length];
        endPoints = new Point[points.length];
        pointCount = 0;

        for (int i = 0; i < points.length; i++) {
            Point footPoint = points[i];
            Point[] otherPoints = excludePoints(points, i);
            if (otherPoints.length < 3)
                return;
            sort(otherPoints, footPoint.slopeOrder(), 0, otherPoints.length - 1);
            extractSegments(otherPoints, footPoint);
        }

        LineSegment[] copy = new LineSegment[segmentsCount];
        for (int i = 0; i < segmentsCount; i++)
            copy[i] = segments[i];
        segments = copy;
        startPoints = null;
        endPoints = null;
    }

    public LineSegment[] segments() {

        return segments.clone();
    }

    public int numberOfSegments() {
        return segmentsCount;
    }

    private void extractSegments(Point[] points, Point footPoint) {
        if (points.length < 3)
            return;

        Point point0 = points[0];
        Comparator<Point> cp = footPoint.slopeOrder();
        int size = 1;
        int start = 0;

        for (int i = 1; i < points.length; i++) {
            Point curPoint = points[i];
            if (cp.compare(point0, curPoint) != 0) {
                if (size > 2) {
                    extractAndAddSegment(points, footPoint, start, size);
                }
                point0 = curPoint;
                start = i;
                size = 0;
            }
            size++;
        }

        if (size > 2) {
            extractAndAddSegment(points, footPoint, start, size);
        }
    }

    private void extractAndAddSegment(Point[] points, Point footPoint, int start, int size)
    {
        Point minPoint = footPoint;
        Point maxPoint = footPoint;
        for (int i = 0; i < size; i++)
        {
            Point curPoint = points[i + start];
            if (curPoint.compareTo(minPoint) == -1)
                minPoint = curPoint;

            if (curPoint.compareTo(maxPoint) == 1)
                maxPoint = curPoint;
        }

        boolean flag = true;
        for (int i = 0; i < pointCount; i++) {
            if (minPoint.compareTo(startPoints[i]) == 0 && maxPoint.compareTo(endPoints[i]) == 0) {
                flag = false;
                break;
            }
        }

        if (flag) {
            segments[segmentsCount++] = new LineSegment(minPoint, maxPoint);
            startPoints[pointCount] = minPoint;
            endPoints[pointCount] = maxPoint;
            pointCount += 1;
        }
    }

    private Point[] excludePoints(Point[] points, int notInclude)
    {
        Point[] result = new Point[points.length - 1];
        int k = 0;
        for(int i = 0; i < points.length; i++)
        {
            if (notInclude != i)
                result[k++] = points[i];
        }
        return result;
    }

}
