import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * Created by orps on 10/22/16.
 */


public class BruteCollinearPoints {

    LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {

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
        for(int i = 0; i < segmentsSize; i++)
            segments[i] = localSegments[i];
    }

    public int numberOfSegments()
    {
        return segments.length;
    }

    public LineSegment[] segments()
    {
        return segments;
    }

//    public static void main(String args[ ])
//    {
//        Point[] points = new Point[]
//                {
//                        new Point(14, 10),
//                        new Point(2, 2),
//                        new Point(19, 10),
//                        new Point(18, 10),
//                        new Point(32, 10),
//                        new Point(4, 4),
//                        new Point(3, 3)
//                };
//
//        BruteCollinearPoints fcp = new BruteCollinearPoints(points);
//        LineSegment[] segments = fcp.segments();
//        for(int i = 0; i < fcp.numberOfSegments(); i++)
//        {
//            LineSegment sg = segments[i];
//            StdOut.println(sg.toString());
//        }
//    }
}
