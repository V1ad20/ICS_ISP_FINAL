/*  @author Sean Yang
 * @author Vlad Surdu
 * @author Ana-Maria Bangala
 * @version 5.0.1
 * Teacher: Ms. Krasteva
 * Course Code: ICS4U0/P
 * 
 */

/** Speech box for characters */
public class CharacterSpeechBox extends SpeechBox {

    public CharacterSpeechBox(String path, boolean activated) {
        super(path, activated);
    }

    /**
     * 
     * @param millis - used to keep time of the animations
     *               Animates and displays text that is assigned to a certain
     *               interaction
     */
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
