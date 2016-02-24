package pointsdist;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p) {
        double distX = Math.abs(this.x - p.x);
        double distY = Math.abs(this.y - p.y);

        return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
    }

    public String getCoordsText() {
        return ("(" + this.x + ", " + this.y + ")");
    }
}
