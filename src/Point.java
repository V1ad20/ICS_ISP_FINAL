
public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        set(x, y);
    }

    public Point() {
    }

    public Point returnCopy() {
        return new Point(x, y);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Point translateX(double transX) {
        x += transX;
        return this;
    }

    public Point translateY(double transY) {
        y += transY;
        return this;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
