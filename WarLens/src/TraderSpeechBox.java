public class TraderSpeechBox extends SpeechBox {

    public int goodNum;

    public TraderSpeechBox(String path, boolean activated, int goodNum) {
        super(path, activated);
        this.goodNum = goodNum;
    }

    /**
     * @param millis
     * @param playerGoodNum
     */
    public void display(int millis, int playerGoodNum) {
        TextBoxContent textBoxContent = contents[currentIndex];
        if (!isVisible()) {
            setVisible(true);
            characterTalkingText.setText(textBoxContent.characterTalking);
            prevTime = (int) Math.floor(0.03 * millis);
        }
        if (timesSpacePressed == 0) {
            int currentTime = (int) Math.floor(0.03 * millis);
            if (letterIndex < textBoxContent.phrase.length()) {
                if (currentTime != prevTime) {
                    letterIndex++;
                    prevTime = currentTime;
                    phraseText.setText(textBoxContent.phrase.substring(0, letterIndex));
                }
            } else {
                timesSpacePressed++;
            }
        } else if (timesSpacePressed == 1) {
            phraseText.setText(textBoxContent.phrase);
        } else {
            if (currentIndex == 0) {
                if (playerGoodNum + 1 == goodNum) {
                    currentIndex += 2;
                } else {
                    currentIndex++;
                }
                textBoxContent = contents[currentIndex];
                characterTalkingText.setText(textBoxContent.characterTalking);
                prevTime = (int) Math.floor(0.03 * millis);
                timesSpacePressed = 0;
                letterIndex = 0;
            } else {
                if (playerGoodNum + 1 != goodNum) {
                    activated = false;
                    currentIndex = 0;
                    timesSpacePressed = 0;
                    letterIndex = 0;

                    phraseText.setText("");
                    characterTalkingText.setText("");
                } else {
                    completed = true;
                }
                setVisible(false);
            }
        }
    }

}
