/******************************************************************************
 *
 * Compilation: javac FastCollinearPoints.java -d out/production/Collinear/
 * Execution: java FastCollinearPoints.java
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
public class FastCollinearPoints {

    private ArrayList<LineSegment> segments;
    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        validate(points);
        segments = new ArrayList<LineSegment>();
        Point[] dups = points.clone();
        int N = dups.length;
        for(Point p: points) {
            Arrays.sort(dups);
            Arrays.sort(dups, p.slopeOrder());

            int start = 0;
            int end = 0;
            double[] slopes = new double[N];

            // Check if any 3 or more adjacent points is collinear...
            ArrayList<Point> collinearPoints = new ArrayList<Point>();
            for (int i = 1; i < N; i++) {
                if (p.slopeTo(dups[i]) == p.slopeTo(dups[i-1])) {
                    collinearPoints.add(dups[i]);
                    end = i;
                } else {
                    addSegment(p, dups, start, end);
                    start = i;
                    collinearPoints = new ArrayList<Point>();
                }
            }

            if (start < end) {
                addSegment(p, dups, start, end);
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

    /*
    *  TODO: Reorganize the code so that it's natural to count the length and add p at the end of the loop.
    *  Collect the collinarPoints and check wether to add the segment at the end or not...
     */
    private void addSegment(Point p, Point[] dups, int start, int end) {
        int max_length = end - start;
        if (max_length >= 2 && p.compareTo(dups[start]) < 0) {          // Only Add segment if point p is the first one to avoid permutation
            Point starting = p.compareTo(dups[start]) < 0 ? p : dups[start];
            Point ending = p.compareTo(dups[end]) > 0 ? p : dups[end];
            LineSegment segment = new LineSegment(starting, ending);
            segments.add(segment);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }


    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        System.out.println(collinear.numberOfSegments());
    }

}
