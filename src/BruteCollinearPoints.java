import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * Created by orps on 10/22/16.
 */


public class BruteCollinearPoints {

    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {

        if (points == null)
            throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            for (int j = i+1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();

        LineSegment[] localSegments = new LineSegment[points.length + 1];
        int segmentsSize = 0;

        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                for (int k = j + 1; k < points.length; k++)
                    for (int m = k + 1; m < points.length; m++) {
                        Point p1 = points[i];
                        Point p2 = points[j];
                        Point p3 = points[k];
                        Point p4 = points[m];

                        Comparator<Point> cp = p1.slopeOrder();
                        if (cp.compare(p2, p3) == 0 && cp.compare(p3, p4) == 0)
                        {
                            Point[] array = new Point[] {p2, p3, p4};
                            Point minPoint = p1;
                            Point maxPoint = p1;

                            for (Point p: array) {
                                if (minPoint.compareTo(p) > 0)
                                    minPoint = p;

                                if (maxPoint.compareTo(p) < 0)
                                    maxPoint = p;
                            }

                            LineSegment segment = new LineSegment(minPoint, maxPoint);
                            localSegments[segmentsSize++] = segment;
                        }
                    }
        segments = new LineSegment[segmentsSize];
        for (int i = 0; i < segmentsSize; i++)
            segments[i] = localSegments[i];
    }

    public int numberOfSegments()
    {
        return segments.length;
    }

    public LineSegment[] segments()
    {
        return segments.clone();
    }

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

        BruteCollinearPoints fcp = new BruteCollinearPoints(points);
        LineSegment[] segments = fcp.segments();
        for (int i = 0; i < fcp.numberOfSegments(); i++) {
            LineSegment sg = segments[i];
            StdOut.println(sg.toString());
        }
    }
}
