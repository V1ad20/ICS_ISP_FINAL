import java.io.IOException;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Inventory extends Group {

    private ImageView inventory;
    private ImageView item;
    private Image[] items;
    private Point center;

    public boolean canShow = true;
    public int inventoryIndex;

    public Inventory(double width, double height, int inventoryIndex, boolean isShowing) {
        super();

        this.inventoryIndex = inventoryIndex;
        inventory = new ImageView();
        inventory.setImage(new Image("SubwayScene/InventoryBox.png"));
        inventory.setScaleX(2);
        inventory.setScaleY(2);

        items = new Image[] {
                new Image("SubwayScene/Trade/Water_Bottle.png"),
                new Image("SubwayScene/Trade/Loaf_of_Bread.png"),
                new Image("SubwayScene/Trade/Teddy_Bear.png"),
                new Image("SubwayScene/Trade/Blanket.png")
        };

        item = new ImageView(items[inventoryIndex]);
        item.setScaleX(2);
        item.setScaleY(2);

        center = new Point(width / 2, height - inventory.getImage().getHeight());

        inventory.setX(center.x - inventory.getImage().getWidth() / 2);
        inventory.setY(center.y - inventory.getImage().getHeight() / 2);

        item.setX(center.x - item.getImage().getWidth() / 2);
        item.setY(center.y - item.getImage().getHeight() / 2);

        setVisible(isShowing);

        getChildren().addAll(inventory, item);
    }

    public void updateItem() {
        item.setImage(items[inventoryIndex]);
    }

}
