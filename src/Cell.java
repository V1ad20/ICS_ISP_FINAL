
import org.w3c.dom.css.RGBColor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    public static final int sideLength = 64;
    public static final double criticalAngle = Math.toRadians(45);
    // public static double topLeftCornerMapX;
    // public static double topLeftCornerMapY;

    public String type;
    public int rowIndex;
    public int colIndex;

    public Cell(int rowIndex, int colIndex, String type) {
        super(sideLength, sideLength);
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;

        this.type = type;
    }

    /**
     * @return double
     */
    public double getGameCenterX() {
        return colIndex * (double) sideLength + getWidth() / 2;
    }

    /**
     * @return double
     */
    public double getGameCenterY() {
        return rowIndex * (double) sideLength + getHeight() / 2;
    }

    /**
     * @return String
     */
    public String toString() {
        return "\"" + type + "\"[" + rowIndex + "," + colIndex + "]";
    }
}
