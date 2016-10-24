/******************************************************************************
 *
 * Compilation: javac BruteCollinearPoints.java -d out/production/Collinear/
 * Execution: java BruteCollinearPoints.java
 * Dependencies: Point
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nando on 5/23/16.
 */
public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] opoints) {
        Point[] points = opoints.clone();
        validate(points);
        int N = points.length;
        segments = new ArrayList<LineSegment>();
        Arrays.sort(points);
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                for (int k = j+1; k < N; k++) {
                    for (int l = k+1; l < N; l++) {
                        if (points[i].compareTo(points[j]) <0 && points[j].compareTo(points[k]) < 0 && points[k].compareTo(points[l]) < 0) {
                            double slopeP = points[i].slopeTo(points[j]);
                            double slopeQ = points[i].slopeTo(points[k]);
                            double slopeR = points[i].slopeTo(points[l]);
                            if (slopeP == slopeQ && slopeP == slopeR) {
                                segments.add(new LineSegment(points[i], points[l]));
                            }
                        }
                    }
                }
            }
        }
    }

    private void validate(Point[] points) {
        ArrayList<Point> set = new ArrayList<Point>();
        if (points == null)
            throw new NullPointerException("Points can't be null");
        for (Point p: points) {
            if (p == null)
                throw new NullPointerException("Points can't be null");

            if (contains(p, set))
                throw new IllegalArgumentException("Can't have duplicate value");
            else
                set.add(p);
        }
    }

    private boolean contains(Point p, ArrayList<Point> set) {
        for (Point x : set) {
            if (x.compareTo(p) == 0)
                return true;
        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] seg = new LineSegment[segments.size()];
        return segments.toArray(seg);
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
//        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        System.out.println(collinear.numberOfSegments());
    }
}