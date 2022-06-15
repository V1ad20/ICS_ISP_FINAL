
/*  @author Sean Yang
 * @author Vlad Surdu
 * @author Ana-Maria Bangala
 * @version 5.0.1
 * Teacher: Ms. Krasteva
 * Course Code: ICS4U0/P
 * 
 */
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 * HouseScene is the class responsible for displaying and running the first scene
 * of the game where Gleb is in his house. This class calls multiple other classes.
 * 
 * @author Sean Yang
 * @author Vlad Surdu
 * @author Ana-Maria Bangala
 * @version 5.0.1
 * Teacher: Ms. Krasteva
 * Course Code: ICS4U0/P
**/
public class HouseScene extends Scene {
    public Player player; //The player variable defines the object the user is controlling
    Cell[][] cellMap; //The cellMap array splits the actual map into an array for processing

    public Image img1 = new Image("HouseScene/Appartment1.jpg");
    public Image img2 = new Image("HouseScene/Appartment2.jpg");
    public BackgroundImage background;

    public boolean started; //Boolean to check for the scene start
    public boolean paused; //Check if the scene is temporarily paused for scene 2
    public boolean resumed; //Check when the scene is resumed
    public boolean finished; //And boolean for when the scene is finished

    private static Group g = new Group();

    public HouseScene() {
        super(g, 640, 640);

        cellMap = createMap(22, 22);
        CellArrayTextParser.parseAndApply("src/HouseScene/HouseSceneMap1.txt",
                cellMap);

        player = new Player(5.0 / 2 * Cell.sideLength, 27.0 / 2 * Cell.sideLength, 64, 64, true, cellMap);
        player.applyTo(this);

        background = new BackgroundImage(getWidth() / 2,
                getHeight() / 2,
                player, img1);

        CharacterSpeechBox glebsIntroMonologue = new CharacterSpeechBox("src/HouseScene/GlebsIntroMonologue.txt", true);

        // Change path for this
        CharacterSpeechBox parentEducation = new CharacterSpeechBox("src/HouseScene/ParentEducation.txt", false);

        ActionHint actionHint = new ActionHint();

        g.getChildren().addAll(background, player, glebsIntroMonologue, parentEducation, actionHint);
        // background.initializeGridOverlay(g);

        addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                if (player.currentCell.type.equals("ParentArea") && !parentEducation.activated) {
                    parentEducation.activated = true;

                    // Subject to change
                    player.gameCenter.set(17 * Cell.sideLength, 11 * Cell.sideLength);
                }
                if (player.currentCell.type.equals("SleepArea") && parentEducation.completed) {
                    player.canMove = false;
                    player.gameCenter.set(Cell.sideLength * 3.0 / 2, 3.0 / 2 * Cell.sideLength + 10);
                    paused = true;
                }
                if (player.currentCell.type.equals("Entrance") && resumed) {
                    finished = true;
                }

            }

            if (e.getCode() == KeyCode.SPACE) {
                if (glebsIntroMonologue.activated && !glebsIntroMonologue.completed) {
                    glebsIntroMonologue.timesSpacePressed++;
                }
                if (parentEducation.activated && !parentEducation.completed) {
                    parentEducation.timesSpacePressed++;
                }
            }
        });

        setFill(Color.BLACK);

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long nanos) {
                int millis = (int) nanos / 1000000;

                if (started && !paused) {
                    // Glebs Intro Monologue Handler
                    if (glebsIntroMonologue.activated) {
                        if (!glebsIntroMonologue.completed) {
                            player.canMove = false;
                            glebsIntroMonologue.display(millis);
                        } else {
                            player.canMove = true;
                        }
                    }

                    if (player.currentCell.type.equals("Default"))
                        actionHint.setVisible(false);

                    // Parent Education handler code
                    if (player.currentCell.type.equals("ParentArea")) {
                        if (!parentEducation.activated) {
                            actionHint.setVisible(true);
                            actionHint.setText("Talk");
                        } else {
                            actionHint.setVisible(false);
                            if (!parentEducation.completed) {
                                player.canMove = false;
                                parentEducation.display(millis);
                            }
                        }
                    }

                    // Going to bed/switching to dream scene code
                    if (player.currentCell.type.equals("SleepArea") && parentEducation.completed) {
                        if (!paused) {
                            actionHint.setVisible(true);
                            actionHint.setText("Sleep");
                        } else {
                            actionHint.setVisible(false);
                            player.canMove = false;
                            this.stop();
                        }
                    }

                    // Player/mapMovement
                    if (!paused)
                        player.updateGamePosition();
                    player.updateCostume(millis);
                    player.updateScreenPosition();
                    background.updatePosition();
                    // background.updateGridOverlay();
                }
            }
        };
        timer.start();

        AnimationTimer timer2 = new AnimationTimer() {
            @Override
            public void handle(long nanos) {
                int millis = (int) nanos / 1000000;
                if (resumed && !finished) {
                    if (player.currentCell.type.equals("Entrance")) {
                        actionHint.setVisible(true);
                        actionHint.setText("Leave");
                    } else {
                        actionHint.setVisible(false);
                    }

                    player.updateGamePosition();
                    player.updateCostume(millis);
                    player.updateScreenPosition();
                    background.updatePosition();
                }
            }
        };
        timer2.start();
    }

    /**
     * @param rows
     * @param cols
     * @return Cell[][]
     */
    public Cell[][] createMap(int rows, int cols) {
        Cell[][] cellMap = new Cell[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cellMap[row][col] = new Cell(row, col, "Default");
            }
        }

        return cellMap;
    }
}
