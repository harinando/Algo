import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

/**
 * Created by nando on 6/5/16.
 */
public class PointTest {

    private static final double DELTA = 1e-15;
    private Point point;
    private Comparator<Point> cmp;
    private ArrayList<Point> vpoints;

    @Before
    public void before() {
        point = new Point(1, 1);
        cmp = point.slopeOrder();
        vpoints =  new ArrayList<Point>();
        vpoints.add(new Point(1000, 17000));
        vpoints.add(new Point(1000, 27000));
        vpoints.add(new Point(1000, 28000));
        vpoints.add(new Point(1000, 31000));
    }


    @Test
    public void testPositiveSlope() throws Exception {
        assertEquals(1.0, point.slopeTo(new Point(10, 10)), DELTA);
    }

    @Test
    public void testNegativeSlope() throws Exception {
        assertEquals(-1.0, point.slopeTo(new Point(0, 2)), DELTA);
    }

    @Test
    public void testHorizontalSlope() throws Exception {
        // +0.0 if the line segment connecting the two points is horizontal;
        assertEquals(+0.0, point.slopeTo(new Point(10, 1)), DELTA);
    }

    @Test
    public void testVerticalSlope() throws  Exception {
        // Double.POSITIVE_INFINITY if the line segment is vertical;
        assertEquals(Double.POSITIVE_INFINITY, point.slopeTo(new Point(1, 10)), DELTA);
    }

    @Test
    public void testDegenerateLineSlope() throws  Exception {
        // Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
        // Treat the slope of a degenerate line segment (between a point and itself) as negative infinity.
        assertEquals(Double.NEGATIVE_INFINITY, point.slopeTo(point), DELTA);
    }

    @Test
    public void testPointShouldBeEqual() throws Exception {
        assertEquals(0, point.compareTo(new Point(1, 1)));      // same point Same point --- x0 == x1 & y0 == y1
    }

    @Test
    public void testPointShouldBeSmaller() throws  Exception {
        assertEquals(-1, point.compareTo(new Point(1, 2)));   // smaller --- y0 < y1
    }

    @Test
    public void testPointShouldBeGreater() throws  Exception {
        assertEquals(1, point.compareTo(new Point(1, 0)));       // Bigger  --- y0 > y1
    }

    @Test
    public void testCompareToBreakingTiesWithX() throws Exception {
        assertEquals(-1, point.compareTo(new Point(2, 1)));      // smaller --- y0 == y1 && x0 < x1
        assertEquals(1, point.compareTo(new Point(0, 0)));       // Bigger  --- y0 == y1 && x0 > x1
    }

    /*

     .       .

     |   *   .

     _   -   -    -    -
     */
    @Test
    public void testSlopeOrder() throws Exception {
    }

    @Test
    public void slopeOrderShouldBeSmaller() throws Exception {
        assertEquals(1, cmp.compare(new Point(0, 0), new Point(2, 1)));  // Greater slope
    }

    @Test
    public void slopeOrderShouldBeGreater() throws Exception {
        assertEquals(-1, cmp.compare(new Point(0,0), new Point(1, 2)));  // Less slope
    }

    @Test
    public void slopeOrderShouldBeSame() throws Exception {
        assertEquals(0, cmp.compare(new Point(0,0), new Point(2, 2)));   // Same slope
    }

}