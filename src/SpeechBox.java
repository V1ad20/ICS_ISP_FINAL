import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SpeechBox extends Group {
    private final int WIDTH = 640;
    private final int HEGHT = 134;

    public TextBoxContent[] contents;
    public ImageView textBox;
    public Text characterTalkingText;
    public Text phraseText;

    public int timesSpacePressed = 0;
    public int currentBlockIndex = 0;
    public int prevTime;
    public int letterIndex = 0;
    public int currentIndex = 0;
    public boolean activated;
    public boolean completed = false;

    static class TextBoxContent {
        public final String characterTalking;
        public final String phrase;

        public TextBoxContent(String characterTalking, String phrase) {
            this.characterTalking = characterTalking;
            this.phrase = phrase;
        }
    }

    public SpeechBox(String path, boolean activated) {
        super();
        setVisible(false);

        String rawData = "";
        try {
            Scanner sc = new Scanner(new File(path));
            while (sc.hasNext()) {
                rawData += sc.nextLine() + " ";

            }
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] phraseBlocks = rawData.split(" - ");

        contents = new TextBoxContent[phraseBlocks.length];

        for (int i = 0; i < contents.length; i++) {
            String[] phraseBlock = phraseBlocks[i].split(" \\* ");
            contents[i] = new TextBoxContent(phraseBlock[0], phraseBlock[1]);
        }

        textBox = new ImageView(new Image("CharacterSpeechBox/TextBox.png"));
        textBox.setX(0);
        textBox.setY(506);

        characterTalkingText = new Text();
        characterTalkingText.setX(20);
        characterTalkingText.setY(540);
        characterTalkingText.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        characterTalkingText.setWrappingWidth(600);
        characterTalkingText.setTextAlignment(TextAlignment.CENTER);
        characterTalkingText.setFill(Color.WHITE);

        phraseText = new Text();
        phraseText.setX(20);
        phraseText.setY(570);
        phraseText.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        phraseText.setWrappingWidth(600);
        phraseText.setTextAlignment(TextAlignment.CENTER);
        phraseText.setFill(Color.WHITE);

        getChildren().addAll(textBox, characterTalkingText, phraseText);

        this.activated = activated;
    }

}
