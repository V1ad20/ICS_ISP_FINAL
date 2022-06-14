public class CameraCell extends Cell {

    public Point correctPlayerGameCenter;
    public String cameraCellType;

    public CameraCell(int row, int col, String type, String cameraCellType, int correctY, int correctX) {
        super(row, col, type);
        this.cameraCellType = cameraCellType;
        correctPlayerGameCenter = new Point(correctX, correctY);
    }

}
