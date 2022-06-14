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

    public Scene load() throws IOException {
        Parent companyRoot = FXMLLoader.load(getClass().getResource("resources/mainMenu.fxml"));
        Scene companyScene = new Scene(companyRoot, 640, 640);

        Button newGame = (Button) companyScene.lookup("#new-game");

        newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            finished = true;
        });
        return companyScene;
    }
}
