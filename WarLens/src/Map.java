import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The Map class extends the ImageView class and its purpose is to display the playable map
 * 
 * @author Sean Yang
 * @author Vlad Surdu
 * @author Ana-Maria Bangala
 * @version 5.0.1
 * Teacher: Ms. Krasteva
 * Course Code: ICS4U0/P
 */
public class Map extends ImageView {

    private static Image[] maps = new Image[] {
            new Image("SubwayScene/Maps/Ukraine1.png"),
            new Image("SubwayScene/Maps/Ukraine2a.png"),
            new Image("SubwayScene/Maps/Ukraine2b.png"),
            new Image("SubwayScene/Maps/Ukraine3.png"),
    };

    public boolean canShow = true; //Checks if the map should be shown
    public int mapIndex; //Holds the index for the map

    /**
     * Constructor for the map class
     * @param width //Width of the map in pixels
     * @param height //Height of the map in pixels
     * @param mapIndex //Map Index
     * @param isShowing //Checks if the map is showing
     */
    public Map(double width, double height, int mapIndex, boolean isShowing) {
        super(maps[mapIndex]);
        this.mapIndex = mapIndex;

        setX(width / 2 - getImage().getWidth() / 2);
        setY(height / 2 - getImage().getHeight() / 2);
        // setX(100);
        // setY(100);

        setVisible(isShowing);
    }

    /**
     * Updates the map
     */
    public void updateMap() {
        setImage(maps[mapIndex]);
    }
}
