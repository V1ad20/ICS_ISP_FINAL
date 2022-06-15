import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** Class that controls the logic for the backgorund image movement */
public class BackgroundImage extends ImageView {

    /* Player object */
    public Player player;

    /* Coordinates for controlling position */
    public Point offset;
    public Point translateComponent;

    /* Rectangle array for drawing grid */
    public Rectangle[][] rectangleGrid;

    public BackgroundImage(double offsetX, double offsetY, Player player, Image img) {
        super(img);
        this.player = player;
        offset = new Point(offsetX, offsetY);
        translateComponent = new Point();
        rectangleGrid = new Rectangle[player.cellMap.length][player.cellMap[0].length];
    }

    /**
     * Updates position of background
     */
    public void updatePosition() {
        translateComponent.set(offset.x - player.refGameCenter.x, offset.y - player.refGameCenter.y);
        setTranslateX(translateComponent.x);
        setTranslateY(translateComponent.y);
    }

    /**
     * Updates the grid overlay (used in debugging)
     */
    public void updateGridOverlay() {
        for (Rectangle[] rectRow : rectangleGrid) {
            for (Rectangle rect : rectRow) {
                rect.setTranslateX(translateComponent.x);
                rect.setTranslateY(translateComponent.y);
            }
        }
    }

    /**
     * @param g - Group object to which the rectangles in the rectangle array are
     *          assigned to
     */
    public void initializeGridOverlay(Group g) {
        for (int row = 0; row < player.cellMap.length; row++) {
            for (int col = 0; col < player.cellMap[0].length; col++) {
                Cell cell = player.cellMap[row][col];
                Rectangle rect = new Rectangle(cell.colIndex * Cell.sideLength,
                        cell.rowIndex * Cell.sideLength,
                        Cell.sideLength,
                        Cell.sideLength);
                String cameraType = "";
                try {
                    CameraCell cameraCell = (CameraCell) cell;
                    cameraType = cameraCell.cameraCellType;
                } catch (Exception e) {
                }
                String type = cell.type;
                if (!cameraType.equals("") && type.equals("Default")) {
                    type = cameraType;
                }
                switch (type) {
                    case "Horizontal":
                        rect.setFill(Color.rgb(255, 0, 0, 0.5));
                        break;
                    case "Vertical":
                        rect.setFill(Color.rgb(0, 255, 0, 0.5));
                        break;
                    case "Corner":
                        rect.setFill(Color.rgb(255, 255, 0, 0.5));
                        break;
                    case "PlayerBoundary":
                        rect.setFill(Color.rgb(0, 0, 0, 0.5));
                        break;
                    case "FamilyArea":
                        rect.setFill(Color.rgb(255, 0, 255, 0.5));
                        break;
                    default:
                        rect.setFill(Color.rgb(0, 0, 0, 0));
                        break;
                }
                g.getChildren().add(rect);
                rectangleGrid[row][col] = rect;
            }

        }

    }
}
