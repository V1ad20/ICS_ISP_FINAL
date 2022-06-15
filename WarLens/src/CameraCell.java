/**
 * @author Sean Yang
 * @author Vlad Surdu
 * @author Ana-Maria Bangala
 * @version 5.0.1
 *          Teacher: Ms. Krasteva
 *          Course Code: ICS4U0/P
 * 
 */

public class CameraCell extends Cell {

    /* Used to position the background image properly */
    public Point correctPlayerGameCenter;

    /** Type of camer cell (horizontal, vertical, ect) */
    public String cameraCellType;

    public CameraCell(int row, int col, String type, String cameraCellType, int correctY, int correctX) {
        super(row, col, type);
        this.cameraCellType = cameraCellType;
        correctPlayerGameCenter = new Point(correctX, correctY);
    }

}
