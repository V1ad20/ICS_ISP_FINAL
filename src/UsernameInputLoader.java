import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class UsernameInputLoader extends Group{

    boolean finished = false;

    String passedUsername;

    TextFieldController textFieldControl = new TextFieldController();

    public Scene load() throws IOException {
        Parent companyRoot = FXMLLoader.load(getClass().getResource("resources/mainMenu.fxml"));
        Scene scene = new Scene(companyRoot, 640, 640);

        if(textFieldControl.finished){
            passedUsername = textFieldControl.getUsername();
            finished = true;
        }

        return scene;
    }
}
