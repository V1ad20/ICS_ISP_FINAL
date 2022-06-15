import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * InstructionsLoader is the loader class for the InstructionsMenu
 * 
 * @author Sean Yang
 * @author Vlad Surdu
 * @author Ana-Maria Bangala
 * @version 5.0.1
 * Teacher: Ms. Krasteva
 * Course Code: ICS4U0/P
 */

<<<<<<< HEAD
public class InstructionsLoader {
    boolean finished = false; //Checks if the class has finished running


    /**
     * the load method loads the entire scene and all of its functionality
     * @return returns the scene and all contained nodes
=======
    /**
     * @return Scene
>>>>>>> 29fee8c3ffe1c040ceb31d568959c25e695d7318
     * @throws IOException
     */
    public Scene load() throws IOException {
        Parent companyRoot = FXMLLoader.load(getClass().getResource("resources/instructionsMenu.fxml"));
        Scene scene = new Scene(companyRoot, 640, 640);

        Button returnMenu = (Button) scene.lookup("#return-menu");

        returnMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            finished = true;
        });
        return scene;
    }
}
