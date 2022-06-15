import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Arc;
import javafx.util.Duration;

public class MainMenuLoader {

    boolean finished = false;
    boolean goToInstruct = false;

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
