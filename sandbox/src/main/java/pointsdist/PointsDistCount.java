package pointsdist;

public class PointsDistCount {
    public static void main(String[] args) {
        Point point1 = new Point(2, 2);
        Point point2 = new Point(3, 3);
        Point point3 = new Point(-1, 1);
        Point point4 = new Point(0, 14);
        Point point5 = new Point(-7, 34);

        System.out.println("Расстояние между точками, вычисленное статической функцией:");
        System.out.println("между точками " + point1.getCoordsText() + " и " + point2.getCoordsText() + " = "
                + distance(point1, point2));
        System.out.println("между точками " + point3.getCoordsText() + " и " + point4.getCoordsText() + " = "
                + distance(point3, point4));
        System.out.println("между точками " + point1.getCoordsText() + " и " + point5.getCoordsText() + " = "
                + distance(point1, point5));

        System.out.println("\nРасстояние между точками, вычисленное методом класса:");
        System.out.println("между точками " + point1.getCoordsText() + " и " + point2.getCoordsText() + " = "
                + point1.distance(point2));
        System.out.println("между точками " + point3.getCoordsText() + " и " + point4.getCoordsText() + " = "
                + point3.distance(point4));
        System.out.println("между точками " + point1.getCoordsText() + " и " + point5.getCoordsText() + " = "
                + point1.distance(point5));
    }

    public static double distance(Point p1, Point p2) {
        double distX = Math.abs(p1.x - p2.x);
        double distY = Math.abs(p1.y - p2.y);

        return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
    }
}
