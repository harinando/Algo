import org.junit.Test;

/**
 * Created by nando on 9/25/16.
 */
public class FastCollinearPointsTest {


    @Test (expected = NullPointerException.class)
    public void shouldThrowNullPointerException() throws Exception {
        new FastCollinearPoints(null);
    }

    @Test (expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionOnNullPoints() throws Exception {
        Point[] points = {
                new Point(1, 1),
                new Point(2, 1),
                null
        };
        new FastCollinearPoints(points);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() throws Exception {
        Point[] points = {
                new Point(1, 1),
                new Point(2, 1),
                new Point(1, 1)
        };
        new FastCollinearPoints(points);
    }

    @Test
    public void testNumberOfSegments() throws Exception {
    }

    @Test
    public void testSegments() throws Exception {
    }


}