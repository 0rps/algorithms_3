import edu.princeton.cs.algs4.StdOut;

import javax.sound.sampled.Line;
import java.util.Comparator;

/**
 * Created by orps on 10/22/16.
 */
public class FastCollinearPoints {

    LineSegment[] segments;
    int segmentsCount;

    public LineSegment[] segments() {
        return segments;
    }

    public int numberOfSegments() {
        return segmentsCount;
    }

    public FastCollinearPoints(Point[] points) {
        segments = new LineSegment[points.length];
        segmentsCount = 0;

        for (int i = 0; i < points.length; i++) {
            Point footPoint = points[i];
            Point[] otherPoints = excludePoints(points, i);
            if (otherPoints.length < 3)
                return;
            sort(otherPoints, footPoint.slopeOrder(), /*footPoint,*/ 0, otherPoints.length - 1);

//            StdOut.println();
//            StdOut.println();
//            StdOut.print(footPoint);
//            StdOut.println();
//            for (int j = 0; j < otherPoints.length; j++) {
//                StdOut.print(otherPoints[j]);
//                StdOut.print(" ");
//            }
//            StdOut.println();
//            for (int j = 0; j < otherPoints.length; j++) {
//                StdOut.print(footPoint.slopeTo(otherPoints[j]));
//                StdOut.print(" ");
//            }
//            StdOut.println();

            extractSegments(otherPoints, footPoint);
        }
    }

    static void swap(Point[] points, int i, int j) {
        Point temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }

    void extractSegments(Point[] points, Point footPoint) {
        if (points.length < 3)
            return;

        double slope = footPoint.slopeTo(points[0]);
        int size = 1;
        int start = 0;

        for (int i = 1; i < points.length; i++) {
            Point curPoint = points[i];
            if (slope != footPoint.slopeTo(curPoint)) {
                if (size > 2) {
                    addSegment(extractSegment(points, footPoint, start, size));
                }
                slope = footPoint.slopeTo(curPoint);
                start = i;
                size = 0;
            }
            size++;
        }

        if (size > 2) {
            addSegment(extractSegment(points, footPoint, start, size));
        }
    }

    LineSegment extractSegment(Point[] points, Point footPoint, int start, int size)
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

        return new LineSegment(minPoint, maxPoint);
    }

    void addSegment(LineSegment segment) {
        boolean toAdd = true;
        for (int i = 0; i < segmentsCount; i++) {
            LineSegment curSegment = segments[i];
            if (curSegment.equals(segment)) {
                toAdd = false;
                break;
            }
        }
        if (toAdd)
            segments[segmentsCount++] = segment;
    }

    Point[] excludePoints(Point[] points, int notInclude)
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

    static void sort(Point[] points, Comparator<Point> cp, /*Point cpp,*/ int start, int stop)
    {
        if (start >= stop)
            return;

        Point middlePoint = points[start];
        int i = start;
        int j = stop+1;

        while(true)
        {

            while (cp.compare(points[--j], middlePoint) == 1) {
                if (j == start) break;
            }

            while (cp.compare(points[++i], middlePoint) == -1) {
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


    public static void main(String args[ ])
    {
        Point[] points = new Point[] {
                new Point(14, 10),
                new Point(14, 15),
                new Point(19, 10),
                new Point(18, 10),
                new Point(32, 10),
                new Point(70, 10),
                new Point(60, 10),
                new Point(6, 7)
        };

//        StdOut.println();
//        for (int i = 0; i < points.length; i++) {
//            StdOut.print(points[i]);
//            StdOut.print(" ");
//        }
//        StdOut.println();
//        sort(points, points[0].slopeOrder(), points[0], 0, points.length-1);
//
//        StdOut.println();
//        for (int i = 0; i < points.length; i++) {
//            StdOut.print(points[i]);
//            StdOut.print(" ");
//        }


        FastCollinearPoints fcp = new FastCollinearPoints(points);
        LineSegment[] segments = fcp.segments();
        for(int i = 0; i < fcp.numberOfSegments(); i++)
        {
            LineSegment sg = segments[i];
            StdOut.println(sg.toString());
        }

    }


}
