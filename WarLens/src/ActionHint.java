
/*
 * @author Sean Yang
 * @author Vlad Surdu
 * @author Ana-Maria Bangala
 * @version 5.0.1
 * Teacher: Ms. Krasteva
 * Course Code: ICS4U0/P
 */
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Class that prompts the user to press enter and interact with environment
 */
public class ActionHint extends Text {

    /* Appearance Variables */
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
