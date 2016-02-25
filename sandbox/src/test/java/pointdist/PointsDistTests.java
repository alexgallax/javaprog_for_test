package pointdist;

import org.testng.Assert;
import org.testng.annotations.Test;
import pointsdist.Point;

public class PointsDistTests {

    @Test
    public void testPointsDist1() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);

        double controlDist = countCheckDist(0, 0, 1, 1);

        Assert.assertEquals(p1.distance(p2), controlDist);
    }

    @Test
    public void testPointsDist2() {
        Point p1 = new Point(-1, -1);
        Point p2 = new Point(1, 1);

        double controlDist = countCheckDist(-1, -1, 1, 1);

        Assert.assertEquals(p1.distance(p2), controlDist);
    }

    @Test
    public void testPointsDist3() {
        Point p1 = new Point(-1000, 0);
        Point p2 = new Point(0, 1000);

        double controlDist = countCheckDist(-1000, 0, 0, 1000);

        Assert.assertEquals(p1.distance(p2), controlDist);
    }

    @Test
    public void testPointsDist4() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);

        double controlDist = countCheckDist(0, 0, 0, 0);

        Assert.assertEquals(p1.distance(p2), controlDist);
    }

    @Test
    public void testPointsDist5() {
        Point p1 = new Point(237, 29);
        Point p2 = new Point(2, 10235);

        double controlDist = countCheckDist(237, 29, 2, 10235);

        Assert.assertEquals(p1.distance(p2), controlDist);
    }

    private double countCheckDist(int point1X, int point1Y, int point2X, int point2Y) {
        double distX = Math.abs(point1X - point2X);
        double distY = Math.abs(point1Y - point2Y);

        return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
    }
}
