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

public class CharacterSpeechBox extends SpeechBox {

    public CharacterSpeechBox(String path, boolean activated) {
        super(path, activated);
    }

    public void display(int millis) {
        TextBoxContent textBoxContent = contents[currentIndex];
        if (!isVisible()) {
            setVisible(true);
            prevTime = (int) Math.floor(0.03 * millis);
        }
        if (timesSpacePressed == 0) {
            int currentTime = (int) Math.floor(0.03 * millis);
            if (letterIndex < textBoxContent.phrase.length()) {
                if (currentTime != prevTime) {
                    letterIndex++;
                    prevTime = currentTime;
                    characterTalkingText.setText(textBoxContent.characterTalking);
                    phraseText.setText(textBoxContent.phrase.substring(0, letterIndex));
                }
            } else {
                timesSpacePressed++;
            }
        } else if (timesSpacePressed == 1) {
            phraseText.setText(textBoxContent.phrase);
        } else {
            if (currentIndex < contents.length - 1) {
                currentIndex++;
                textBoxContent = contents[currentIndex];
                characterTalkingText.setText(textBoxContent.characterTalking);
                prevTime = (int) Math.floor(0.03 * millis);
                timesSpacePressed = 0;
                letterIndex = 0;
            } else {
                completed = true;
                setVisible(false);
            }
        }
    }
}
