import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * The loader for the mainMenu. This class loads the functionality and graphics for the mainMenu
 * 
 * @author Sean Yang
 * @author Vlad Surdu
 * @author Ana-Maria Bangala
 * @version 5.0.1
 * Teacher: Ms. Krasteva
 * Course Code: ICS4U0/P
 */
public class MainMenuLoader {

    boolean finished = false; //Checks if the class has finished running (move onto the next scene)
    boolean goToInstruct = false; //Checks if the class should go to the instructions

    /**
     * @return Scene
     * @throws IOException
     */
    public Scene load() throws IOException {
        Parent companyRoot = FXMLLoader.load(getClass().getResource("resources/mainMenu.fxml"));
        Scene companyScene = new Scene(companyRoot, 640, 640);

        Button newGame = (Button) companyScene.lookup("#new-game");

        Button instructions = (Button) companyScene.lookup("#instructions");

        newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            finished = true;
        });

        instructions.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            goToInstruct = true;
        });

        return companyScene;
    }
}
