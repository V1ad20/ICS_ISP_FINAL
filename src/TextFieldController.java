import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class TextFieldController {

    @FXML
    private TextField textField;

    @FXML
    private Button submitButton;

    @FXML
    private Text warningText;

    private String username;

    boolean finished = false;

    public void submit(ActionEvent event){

        username = textField.getText();

        if(username.length() == 0){
            warningText.setText("USERNAME CANNOT BE EMPTY");
        }else if(username.length() > 8){
            warningText.setText("USERNAME CANNOT EXCEED 8 CHARACTERS");
        }else{
            finished = true;
        }
    }

    public String getUsername(){
        return username;
    }
}
