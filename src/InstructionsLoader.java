import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class InstructionsLoader {
    boolean finished = false;

    public Scene load() throws IOException {
        Parent companyRoot = FXMLLoader.load(getClass().getResource("resources/instructionsMenu.fxml"));
        Scene companyScene = new Scene(companyRoot, 640, 640);

        Button returnMenu = (Button) companyScene.lookup("#return-menu");

        returnMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            finished = true;
        });
        return companyScene;
    }   
}
