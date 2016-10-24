import org.junit.Test;

/**
 * Created by nando on 9/24/16.
 */
public class BruteCollinearPointsTest {


    @Test (expected = NullPointerException.class)
    public void shouldThrowNullPointerException() throws Exception {
        new BruteCollinearPoints(null);
    }

    @Test (expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionOnNullPoints() throws Exception {
        Point[] points = {
                new Point(1, 1),
                new Point(2, 1),
                null
        };
        new BruteCollinearPoints(points);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() throws Exception {
        Point[] points = {
                new Point(1, 1),
                new Point(2, 1),
                new Point(1, 1)
        };
        new BruteCollinearPoints(points);
    }

    @Test
    public void testNumberOfSegments() throws Exception {
    }

    @Test
    public void testSegments() throws Exception {
    }
}