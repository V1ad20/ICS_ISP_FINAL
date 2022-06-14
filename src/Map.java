import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Map extends ImageView {

    private static Image[] maps = new Image[] {
            new Image("SubwayScene/Maps/Ukraine1.png"),
            new Image("SubwayScene/Maps/Ukraine2a.png"),
            new Image("SubwayScene/Maps/Ukraine2b.png"),
            new Image("SubwayScene/Maps/Ukraine3.png"),
    };

    public boolean canShow = true;
    public int mapIndex;

    public Map(double width, double height, int mapIndex, boolean isShowing) {
        super(maps[mapIndex]);
        this.mapIndex = mapIndex;

        setX(width / 2 - getImage().getWidth() / 2);
        setY(height / 2 - getImage().getHeight() / 2);
        // setX(100);
        // setY(100);

        setVisible(isShowing);
    }

    public void updateMap() {
        setImage(maps[mapIndex]);
    }

    // public static void main(String[] args) {
    // Group g = new Group();
    // Map map = new Map(640, 640, 0, true);
    // g.getChildren().add(map);
    // Scene scene = new Scene(g, 640, 640);
    // Stage stage = new Stage();
    // stage.setScene(scene);
    // stage.show();

    // }
}
