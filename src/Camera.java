
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Camera extends Rectangle {
    public Point gameCenter;

    public Point gameCenterTopLeft;
    private Point prevGameCenterTopLeft;

    public Point gameCenterTopRight;
    private Point prevGameCenterTopRight;

    public Point gameCenterBottomLeft;
    private Point prevGameCenterBottomLeft;

    public Point gameCenterBottomRight;
    private Point prevGameCenterBottomRight;

    private Player player;
    private Cell[][] cellMap;

    private final double xOffset;
    private final double yOffset;

    public Camera(double width, double height, Player player, Cell[][] cellMap) {
        super(width, height);
        setFill(Color.GRAY);
        this.player = player;
        this.cellMap = cellMap;
        xOffset = (width - Cell.sideLength) / 2;
        yOffset = (height - Cell.sideLength) / 2;

        gameCenter = player.gameCenter.returnCopy();

        updateCornerPoints();
        prevGameCenterTopLeft = gameCenterTopLeft.returnCopy();
        prevGameCenterTopRight = gameCenterTopRight.returnCopy();
        prevGameCenterBottomLeft = gameCenterBottomLeft.returnCopy();
        prevGameCenterBottomRight = gameCenterBottomRight.returnCopy();
    }

    private void updateCornerPoints() {
        gameCenterTopLeft = gameCenter.returnCopy()
                .translateX(-xOffset)
                .translateY(-yOffset);

        gameCenterTopRight = gameCenter.returnCopy()
                .translateX(xOffset)
                .translateY(-yOffset);

        gameCenterBottomLeft = gameCenter.returnCopy()
                .translateX(-xOffset)
                .translateY(yOffset);

        gameCenterBottomRight = gameCenter.returnCopy()
                .translateX(xOffset)
                .translateY(yOffset);
    }

    public void updateGamePosition() {
        gameCenter = player.gameCenter.returnCopy();
        updateCornerPoints();

        determineBehaviourAtCell(gameCenterTopLeft, prevGameCenterTopLeft);

        gameCenter.set(gameCenterTopLeft.x + xOffset, gameCenterTopLeft.y + yOffset);
        updateCornerPoints();

        determineBehaviourAtCell(gameCenterTopRight, prevGameCenterTopRight);
        gameCenter.set(gameCenterTopRight.x - xOffset, gameCenterTopRight.y + yOffset);
        updateCornerPoints();

        determineBehaviourAtCell(gameCenterBottomLeft, prevGameCenterBottomLeft);
        gameCenter.set(gameCenterBottomLeft.x + xOffset, gameCenterBottomLeft.y - yOffset);
        updateCornerPoints();

        determineBehaviourAtCell(gameCenterBottomRight, prevGameCenterBottomRight);
        gameCenter.set(gameCenterBottomRight.x - xOffset, gameCenterBottomRight.y - yOffset);
        updateCornerPoints();

        prevGameCenterTopLeft = gameCenterTopLeft.returnCopy();
        prevGameCenterTopRight = gameCenterTopRight.returnCopy();
        prevGameCenterBottomLeft = gameCenterBottomLeft.returnCopy();
        prevGameCenterBottomRight = gameCenterBottomRight.returnCopy();
    }

    private void determineBehaviourAtCell(Point point, Point prevPoint) {
        Cell pointCell = cellMap[(int) point.y / Cell.sideLength][(int) point.x / Cell.sideLength];

        if (!(pointCell.type.equals("CameraOutOfBounds"))) {
            boolean[] collisionOccurrences = new boolean[] { false, false, false, false };
            final int T, L, R, B;
            T = 0;
            L = 1;
            R = 2;
            B = 3;
            int collisionType = 0;

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (Math.abs(i) != Math.abs(j)) {
                        Cell cell = cellMap[pointCell.rowIndex + i][pointCell.rowIndex + j];
                        if (cell.type.equals("CameraBoundary") && intersectionOccursAt(cell, point)) {
                            handleCollision(cell, point, prevPoint);
                            collisionOccurrences[collisionType] = true;
                            collisionType++;
                        }
                    }
                }
            }

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (Math.abs(i) == Math.abs(j) && i != 0) {
                        Cell cell = cellMap[pointCell.rowIndex + i][pointCell.rowIndex + j];
                        if (cell.type.equals("CameraBoundary") && intersectionOccursAt(cell, point)) {
                            if (i == -1 && j == -1 && !(collisionOccurrences[L] || collisionOccurrences[T]))
                                handleCollision(cell, point, prevPoint);
                            else if (i == -1 && j == 1 && !(collisionOccurrences[R] || collisionOccurrences[T]))
                                handleCollision(cell, point, prevPoint);
                            else if (i == 1 && j == -1 && !(collisionOccurrences[L] || collisionOccurrences[B]))
                                handleCollision(cell, point, prevPoint);
                            else if (i == 1 && j == 1 && !(collisionOccurrences[R] || collisionOccurrences[B]))
                                handleCollision(cell, point, prevPoint);
                        }
                    }
                }
            }
        } else {
            point = correctPoint(point, prevPoint);
        }
    }

    /**
     * @param point
     * @param prevPoint
     * @return Point
     */
    private Point correctPoint(Point point, Point prevPoint) {
        Point newPoint = new Point(point.x, prevPoint.y);
        if (!cellMap[(int) newPoint.y / Cell.sideLength][(int) newPoint.x / Cell.sideLength].type
                .equals("CameraOutOfBounds"))
            return newPoint;

        newPoint = new Point(prevPoint.x, point.y);
        if (!cellMap[(int) newPoint.y / Cell.sideLength][(int) newPoint.x / Cell.sideLength].type
                .equals("CameraOutOfBounds"))
            return newPoint;

        return prevPoint.returnCopy();
    }

    /**
     * @param c
     * @param point
     * @return boolean
     */
    private boolean intersectionOccursAt(Cell c, Point point) {
        return Math.abs(point.x - c.getGameCenterX()) < c.getWidth()
                && Math.abs(point.y - c.getGameCenterY()) < c.getHeight();
    }

    /**
     * @param c
     * @param point
     * @param prevPoint
     */
    private void handleCollision(Cell c, Point point, Point prevPoint) {
        double angle = Math.atan2(prevPoint.y - c.getGameCenterY(), prevPoint.x - c.getGameCenterX());

        if (Math.abs(angle) < Cell.criticalAngle) { // Left collision
            point.x = c.getGameCenterX() + Cell.sideLength;
        } else if (angle >= Cell.criticalAngle && angle <= Math.PI - Cell.criticalAngle) { // Top Collision
            point.y = c.getGameCenterY() + Cell.sideLength;
        } else if (Math.abs(angle) > Math.PI - Cell.criticalAngle) { // Right Collision
            point.x = c.getGameCenterX() - Cell.sideLength;
        } else { // Bottom Collision
            point.y = c.getGameCenterY() - Cell.sideLength;
        }
    }

}
