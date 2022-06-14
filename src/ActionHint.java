import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ActionHint extends Text {

    private final double x = 20;
    private final double y = 570;
    private final double wrappingWidth = 600;

    public ActionHint() {
        super();
        setX(x);
        setY(y);
        setWrappingWidth(wrappingWidth);
        setTextAlignment(TextAlignment.CENTER);
        setFill(Color.WHITE);
        setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
    }
}
