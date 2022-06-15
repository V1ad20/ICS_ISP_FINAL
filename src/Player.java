import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 * The Player class extends the ImageView class and is responsible for the player character
 * 
 * @author Sean Yang
 * @author Vlad Surdu
 * @author Ana-Maria Bangala
 * @version 5.0.1
 * Teacher: Ms. Krasteva
 * Course Code: ICS4U0/P
 */
public class Player extends ImageView {

    // Scene derived variables
    private double sceneWidth;
    private double sceneHeight;

    // Time variables
    private int currentTime = 0;

    // Motion Variables
    private int verticalMotion;
    private int horizontalMotion;
    private int stepSize = 5;
    public boolean canMove = true;

    // Apperance Variables
    private int prevTime = 0;
    public Image[] imgArr;
    private Image[] rightFacingImages = {
            new Image("Player/StandingFacingRight.png"),
            new Image("Player/RunningRightFrame1.png"),
            new Image("Player/RunningRightFrame2.png"),
            new Image("Player/RunningRightFrame3.png"),
            new Image("Player/RunningRightFrame2.png"),
    };
    private Image[] leftFacingImages = {
            new Image("Player/StandingFacingLeft.png"),
            new Image("Player/RunningLeftFrame1.png"),
            new Image("Player/RunningLeftFrame2.png"),
            new Image("Player/RunningLeftFrame3.png"),
            new Image("Player/RunningLeftFrame2.png"),
    };
    private int imgIndex = 0;

    // Position Variables
    public Point gameCenter;
    public Point refGameCenter;
    private Point prevGameCenter;
    public Cell[][] cellMap;
    public Cell currentCell;

    public Player(double gameCenterX, double gameCenterY, double w, double h, boolean isFacingRight, Cell[][] cellMap) {
        super(new Image("Player/StandingFacingRight.png"));
        setFitWidth(w);
        setFitHeight(h);

        this.cellMap = cellMap;

        gameCenter = new Point(gameCenterX, gameCenterY);
        refGameCenter = new Point();

        prevGameCenter = new Point(gameCenterX, gameCenterY);

        verticalMotion = 0;
        horizontalMotion = 0;

        currentCell = getCurrentCell();

        if (isFacingRight)
            imgArr = rightFacingImages;
        else
            imgArr = leftFacingImages;
        // setFill(Color.RED);
    }

    
    /** 
     * @param event
     */
    private void setMotion(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                verticalMotion = -1;
                break;
            case DOWN:
                verticalMotion = 1;
                break;
            case RIGHT:
                horizontalMotion = 1;
                break;
            case LEFT:
                horizontalMotion = -1;
                break;
            default:
                break;
        }
    }

    
    /** 
     * @param event
     */
    private void cancelMotion(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                verticalMotion = 0;
                break;
            case DOWN:
                verticalMotion = 0;
                break;
            case RIGHT:
                horizontalMotion = 0;
                break;
            case LEFT:
                horizontalMotion = 0;
                break;
            default:
                break;
        }
    }

    
    /** 
     * @param scene
     */
    public void applyTo(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> setMotion(e));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> cancelMotion(e));

        sceneWidth = scene.getWidth();
        sceneHeight = scene.getHeight();
    }

    public void updateGamePosition() {
        if (canMove) {
            gameCenter.x += horizontalMotion * stepSize;
            gameCenter.y += verticalMotion * stepSize;

            determineBehaviourAtCell();

            prevGameCenter.x = gameCenter.x;
            prevGameCenter.y = gameCenter.y;
        }
    }

    public void updateScreenPosition() {
        determineRefGameCenter();
        setTranslateX((sceneWidth - getFitWidth()) / 2 + gameCenter.x - refGameCenter.x);
        setTranslateY((sceneHeight - getFitHeight()) / 2 + gameCenter.y - refGameCenter.y);
    }

    
    /** 
     * @param millis
     */
    public void updateCostume(int millis) {
        currentTime = (int) Math.floor(0.01 * millis);
        if (canMove && (verticalMotion != 0 || horizontalMotion != 0)) {
            if (horizontalMotion > 0)
                imgArr = rightFacingImages;
            else if (horizontalMotion < 0)
                imgArr = leftFacingImages;

            if (currentTime != prevTime) {
                imgIndex++;
                prevTime = currentTime;
                if (imgIndex > 4)
                    imgIndex = 1;
            }
            setImage(imgArr[imgIndex]);
        } else {
            imgIndex = 1;
            prevTime = currentTime;
            setImage(imgArr[0]);
        }

    }

    public void determineRefGameCenter() {
        try {
            CameraCell testCell = (CameraCell) getCurrentCell();
            if (testCell.cameraCellType.indexOf("Horizontal") > -1) {
                double cPGCy = testCell.correctPlayerGameCenter.y;
                refGameCenter.set(gameCenter.x, cPGCy);
            } else if (testCell.cameraCellType.indexOf("Vertical") > -1) {
                double cPGCx = testCell.correctPlayerGameCenter.x;
                refGameCenter.set(cPGCx, gameCenter.y);
            } else {
                double cPGCy = testCell.correctPlayerGameCenter.y;
                double cPGCx = testCell.correctPlayerGameCenter.x;
                refGameCenter.set(cPGCx, cPGCy);
            }
        } catch (Exception e) {
            refGameCenter = gameCenter.returnCopy();
        }
    }

    private void determineBehaviourAtCell() {
        currentCell = getCurrentCell();

        // Check collisions with non-diagonal cells (directions are relative to player)
        boolean topCollision = false; // "head" of player is hit ect. ect.
        boolean bottomCollision = false;
        boolean leftCollision = false;
        boolean rightCollision = false;

        Cell c = cellMap[currentCell.rowIndex - 1][currentCell.colIndex];
        if (c.type.equals("PlayerBoundary") && intersectionOccursAt(c)) {
            topCollision = true;
            handleCollision(c);
            c.setFill(Color.PINK);
        }
        c = cellMap[currentCell.rowIndex][currentCell.colIndex - 1];
        if (c.type.equals("PlayerBoundary") && intersectionOccursAt(c)) {
            leftCollision = true;
            handleCollision(c);
            c.setFill(Color.ORANGE);
        }
        c = cellMap[currentCell.rowIndex][currentCell.colIndex + 1];
        if (c.type.equals("PlayerBoundary") && intersectionOccursAt(c)) {
            rightCollision = true;
            handleCollision(c);
            c.setFill(Color.YELLOW);
        }
        c = cellMap[currentCell.rowIndex + 1][currentCell.colIndex];
        if (c.type.equals("PlayerBoundary") && intersectionOccursAt(c)) {
            bottomCollision = true;
            handleCollision(c);
            c.setFill(Color.PURPLE);
        }

        // Loops through adjacent cells
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                c = cellMap[currentCell.rowIndex + i][currentCell.colIndex + j];
                if (intersectionOccursAt(c) && c.type.equals("PlayerBoundary")) {
                    /**
                     * Collision checker and corrector
                     * (only diagonal cells because
                     * non-diagonal cells were dealt with already)
                     */
                    // Registers collisions with diagonal cells if there are no
                    // non-diagonal cells that would have prevented collisions
                    if (i == -1 && j == -1 && !topCollision && !leftCollision)
                        handleCollision(c);
                    else if (i == -1 && j == 1 && !topCollision && !rightCollision)
                        handleCollision(c);
                    else if (i == 1 && j == -1 && !bottomCollision && !leftCollision)
                        handleCollision(c);
                    else if (i == 1 && j == 1 && !bottomCollision && !rightCollision)
                        handleCollision(c);
                }
            }
        }
    }

    
    /** 
     * @param c
     * @return boolean
     */
    private boolean intersectionOccursAt(Cell c) {
        return Math.abs(gameCenter.x - c.getGameCenterX()) < (getFitWidth() + c.getWidth()) / 2
                && Math.abs(gameCenter.y - c.getGameCenterY()) < (getFitHeight() + c.getHeight()) / 2;
    }

    
    /** 
     * @param c
     */
    private void handleCollision(Cell c) {
        double angle = Math.atan2(prevGameCenter.y - c.getGameCenterY(), prevGameCenter.x - c.getGameCenterX());

        if (Math.abs(angle) < Cell.criticalAngle) { // Left collision
            gameCenter.x = c.getGameCenterX() + (Cell.sideLength + getFitWidth()) / 2;
        } else if (angle >= Cell.criticalAngle && angle <= Math.PI - Cell.criticalAngle) { // Top Collision
            gameCenter.y = c.getGameCenterY() + (Cell.sideLength + getFitHeight()) / 2;
        } else if (Math.abs(angle) > Math.PI - Cell.criticalAngle) { // Right Collision
            gameCenter.x = c.getGameCenterX() - (Cell.sideLength + getFitWidth()) / 2;
        } else { // Bottom Collision
            gameCenter.y = c.getGameCenterY() - (Cell.sideLength + getFitHeight()) / 2;
        }
    }

    
    /** 
     * @return Cell
     */
    public Cell getCurrentCell() {
        return cellMap[((int) gameCenter.y)
                / Cell.sideLength][((int) gameCenter.x) / Cell.sideLength];
    }

}
