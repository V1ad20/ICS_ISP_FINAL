import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TrainScene extends Scene {
    public boolean started = false;
    public boolean finished = false;

    private static Group g = new Group();
    private int prevTime = 0;

    public TrainScene() {
        super(g, 640, 640);

        CharacterSpeechBox trainDialogue = new CharacterSpeechBox("src/TrainScene/TrainScene.txt", false);
        ImageView background = new ImageView(new Image("TrainScene/Train.gif"));
        background.setScaleX(1.6);
        background.setScaleY(1.6);

        g.getChildren().addAll(background, trainDialogue);

        addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.SPACE) {
                if (trainDialogue.activated && !trainDialogue.completed) {
                    trainDialogue.timesSpacePressed++;
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long nanos) {
                int millis = (int) nanos / 1000000;
                if (started && !finished) {
                    System.out.println(millis);
                    if (!trainDialogue.activated && (int) Math.floor(millis * 0.0005) > prevTime) {
                        trainDialogue.activated = true;
                    }

                    if (trainDialogue.activated && !trainDialogue.completed) {
                        trainDialogue.display(millis);
                    } else if (trainDialogue.completed) {
                        finished = true;
                    }

                } else {
                    prevTime = millis;
                }
            }
        };

        timer.start();
    }
}
