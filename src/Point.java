
public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        set(x, y);
    }

    public Point() {
    }

    /**
     * @return Point
     */
    public Point returnCopy() {
        return new Point(x, y);
    }

    /**
     * @return String
     */
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * @param transX
     * @return Point
     */
    public Point translateX(double transX) {
        x += transX;
        return this;
    }

    /**
     * @param transY
     * @return Point
     */
    public Point translateY(double transY) {
        y += transY;
        return this;
    }

    /**
     * @param x
     * @param y
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
