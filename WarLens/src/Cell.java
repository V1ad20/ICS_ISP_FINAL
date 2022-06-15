/*  @author Sean Yang
 * @author Vlad Surdu
 * @author Ana-Maria Bangala
 * @version 5.0.1
 * Teacher: Ms. Krasteva
 * Course Code: ICS4U0/P
 * 
 */

import javafx.scene.shape.Rectangle;

/** Class that makes up the grid in which the player interacts with */
public class Cell extends Rectangle {

    /* Side length */
    public static final int sideLength = 64;
    /* Angle Used for collision calculations */
    public static final double criticalAngle = Math.toRadians(45);

    /** type of cell */
    public String type;
    /* row index */
    public int rowIndex;
    /** column index */
    public int colIndex;

    public Cell(int rowIndex, int colIndex, String type) {
        super(sideLength, sideLength);
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;

        this.type = type;
    }

    /**
     * @return double
     *         returns center game x coordinates
     */
    public double getGameCenterX() {
        return colIndex * (double) sideLength + getWidth() / 2;
    }

    /**
     * @return double
     *         returns center game y coordinates
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
