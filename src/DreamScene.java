import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javafx.geometry.Insets;
/**
 * DreamScene is the class that handles displaying the second part of the
 * game where Gleb has his dream. The class is completely self contained
 * and uses assets found in the 'resources' folder. It contains everything
 * from the animation found in the dream scene to the text boxes, text box
 * functionality and animation, and the questions functionality.
 * 
 * @author Sean Yang
 * @author Vlad Surdu
 * @author Ana-Maria Bangala
 * @version 5.0.1
 * Teacher: Ms. Krasteva
 * Course Code: ICS4U0/P
 */
public class DreamScene extends Scene {
    int checkTime; //This variable is the checkTime that the animationTimers use to check for passage of time
    int curTime; //The current time of the specified animationTimer
    int disInt; //This is a distance integer that is used to control the amount of a sentence to display
    int curIndex; //The current index of the array that holds all the sentence fragmants
    String currentString; //The current string that is being displayed
    boolean keyEventActive; //Whether or not the space bar is able to be pressed in relation to text display
    boolean animationLocked; //Set to true when text is being displayed, set to false when not - variable used to control events
    int sceneState; //State machine used to determine what stage of scene2 the program is on
    private static Group root = new Group();

    
    /** This method controls the fade in animation of a node
     * @param root the node that the animation is applied to
     * @param time the amount of time in milliseconds the transition should take
     */
    public void fadeIn(Node root, int time) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(time));
        ft.setNode(root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
    
    /**
     * constructor for the DreamScene
     * @throws IOException handles IOException from FileReader
     */
    public DreamScene() throws IOException {
        super(root, 640, 640);
        Timer timer = new Timer();
        Scene scene2 = this;

        textTool("src/resources/scene2TextPart1.txt", root, this);

        Image frame1 = new Image("resources/characters/mainCharacter/frame1.png");
        Image frame2 = new Image("resources/characters/mainCharacter/frame2.png");
        Image frame3 = new Image("resources/characters/mainCharacter/frame3.png");
        Image frame4 = new Image("resources/characters/mainCharacter/frame4.png");

        ImageView testChar = new ImageView();
        ImageView vignette = new ImageView(new Image("resources/vignette.png"));

        testChar.setX(-10);
        testChar.setY(400);
        testChar.setPreserveRatio(true);
        testChar.setScaleX(1.5);
        testChar.setScaleY(1.5);
        testChar.setEffect(new Glow(0.5));

        vignette.setFitWidth(640);
        vignette.setFitHeight(506);
        vignette.setPreserveRatio(false);
        vignette.setEffect(new Glow(0.5));

        root.getChildren().add(testChar);
        root.getChildren().add(vignette);

        ArrayList<Image> rightMoveFrames = new ArrayList<Image>();
        rightMoveFrames.add(frame1);
        rightMoveFrames.add(frame2);
        rightMoveFrames.add(frame3);
        rightMoveFrames.add(frame4);

        AnimationTimer runningRightAnim = new AnimationTimer() {
            @Override
            public void handle(long nanos) {
                int milis = (int) (nanos / 1000000);
                curTime = (int) Math.floor(0.004 * milis);
                if (checkTime != curTime) {
                    testChar.setImage(rightMoveFrames.get(curIndex));
                    curIndex++;
                    if (curIndex == rightMoveFrames.size()) {
                        curIndex = 0;
                    }
                    checkTime = curTime;
                }
            }
        };

        TranslateTransition charMove = new TranslateTransition();
        charMove.setDuration(Duration.millis(5000));
        charMove.setNode(testChar);
        charMove.setByX(700);
        charMove.setCycleCount(1);
        charMove.setAutoReverse(false);

        AnimationTimer scene2Anim1 = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                if (!animationLocked) {
                    checkTime = 0;
                    curTime = 0;
                    curIndex = 0;
                    runningRightAnim.start();
                    charMove.play();
                    this.stop();
                }
            }
        };
        scene2Anim1.start();

        ImageView returnedTest = new ImageView(new Image("resources/returnedTest.png"));
        returnedTest.setPreserveRatio(true);
        returnedTest.setScaleX(3);
        returnedTest.setScaleY(3);
        returnedTest.setX(280);
        returnedTest.setY(210);

        GaussianBlur gausBlur = new GaussianBlur();
        gausBlur.setRadius(15);
        returnedTest.setEffect(gausBlur);

        root.getChildren().add(returnedTest);

        returnedTest.setVisible(false);

        RotateTransition returnedTestRotate = new RotateTransition();
        returnedTestRotate.setAxis(Rotate.Z_AXIS);
        returnedTestRotate.setByAngle(360);
        returnedTestRotate.setDuration(Duration.millis(30000));
        returnedTestRotate.setCycleCount(99999);
        returnedTestRotate.setAutoReverse(false);
        returnedTestRotate.setNode(returnedTest);

        AnimationTimer scene2Anim2 = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                if (!animationLocked) {
                    scene2QSet1(root, scene2);
                    this.stop();
                }
            }
        };

        TimerTask runTextPart2 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            textTool("src/resources/scene2TextPart2.txt", root, scene2);
                            returnedTest.setVisible(true);
                            returnedTestRotate.play();
                            fadeIn(returnedTest, 5000);
                            scene2Anim2.start();
                        } catch (IOException e) {
                            System.out.println("FATAL ERROR: file scene2TextPart2 - NOT FOUND");
                        }
                    }
                });
            };
        };

        charMove.setOnFinished(event -> {
            vignette.setVisible(false);
            scene2.setFill(Color.BLACK);
            runningRightAnim.stop();
            timer.schedule(runTextPart2, 1000);
        });

        AnimationTimer part2Text = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                if (sceneState == 9) {
                    try {
                        textTool("src/resources/scene2TextPart3.txt", root, scene2);
                        this.stop();
                    } catch (IOException e) {
                    }
                }
            }
        };
        part2Text.start();
    }

    
    /** 
     * First set of questions to show to the user
     * @param root Root Group
     * @param scene Current Scene
     */
    public void scene2QSet1(Group root, Scene scene) {

        sceneState = 1;

        Text question = new Text("When you are stressed, should you: (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set1/option1.png")));
        option1.setLayoutX(150);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set1/option2.png")));
        option2.setLayoutX(270);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        Button option3 = new Button();
        option3.setGraphic(new ImageView(new Image("resources/buttons/set1/option3.png")));
        option3.setLayoutX(390);
        option3.setLayoutY(575);
        option3.setPadding(Insets.EMPTY);
        root.getChildren().add(option3);

        root.requestFocus();

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                question.setText(
                        "Correct! When you are stressed, taking deep breathes will help you stay calm! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 1) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option3.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            option3.setDisable(true);
                            scene2QSet2(root, scene);
                        }
                    }
                });
            }
        });

        option3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });
    }

    
    /** 
     * Second set of questions to show the user
     * @param root Root Group
     * @param scene Current scene
     */
    public void scene2QSet2(Group root, Scene scene) {

        sceneState = 2;

        Text question = new Text("When you are stressed, should you: (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set2/option1.png")));
        option1.setLayoutX(150);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set2/option2.png")));
        option2.setLayoutX(270);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        Button option3 = new Button();
        option3.setGraphic(new ImageView(new Image("resources/buttons/set2/option3.png")));
        option3.setLayoutX(390);
        option3.setLayoutY(575);
        option3.setPadding(Insets.EMPTY);
        root.getChildren().add(option3);

        root.requestFocus();

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option3.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                question.setText(
                        "Correct! Not resting can cause you to be even more stressed! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 2) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option3.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            option3.setDisable(true);
                            scene2QSet3(root, scene);
                        }
                    }
                });
            }
        });
    }

    
    /** 
     * Third set of questions to show the user
     * @param root Root Group
     * @param scene Current Scene
     */
    public void scene2QSet3(Group root, Scene scene) {

        sceneState = 3;

        Text question = new Text("When you are stressed, should you: (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set3/option1.png")));
        option1.setLayoutX(150);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set3/option2.png")));
        option2.setLayoutX(270);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        Button option3 = new Button();
        option3.setGraphic(new ImageView(new Image("resources/buttons/set3/option3.png")));
        option3.setLayoutX(390);
        option3.setLayoutY(575);
        option3.setPadding(Insets.EMPTY);
        root.getChildren().add(option3);

        root.requestFocus();

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option3.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                question.setText(
                        "Correct! Always remember that you are never alone! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 3) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option3.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            option3.setDisable(true);
                            scene2QSet4(root, scene);
                        }
                    }
                });
            }
        });
    }

    
    /** 
     * Fourth set of questions to show the user
     * @param root Root Group
     * @param scene Current Scene
     */
    public void scene2QSet4(Group root, Scene scene) {

        sceneState = 4;

        Text question = new Text("When you feel hopeless, should you: (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set4/option1.png")));
        option1.setLayoutX(150);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set4/option2.png")));
        option2.setLayoutX(270);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        Button option3 = new Button();
        option3.setGraphic(new ImageView(new Image("resources/buttons/set4/option3.png")));
        option3.setLayoutX(390);
        option3.setLayoutY(575);
        option3.setPadding(Insets.EMPTY);
        root.getChildren().add(option3);

        root.requestFocus();

        option3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                question.setText(
                        "Correct! When you feel hopeless, talk to people with past experiences for advice! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 4) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option3.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            option3.setDisable(true);
                            scene2QSet5(root, scene);
                        }
                    }
                });
            }
        });
    }

    
    /** 
     * Fifth set of questions to show the user
     * @param root Root Group
     * @param scene Current Scene
     */
    public void scene2QSet5(Group root, Scene scene) {

        sceneState = 5;

        Text question = new Text("Should you use the Internet as help? (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set5/option1.png")));
        option1.setLayoutX(150);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set5/option2.png")));
        option2.setLayoutX(270);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        Button option3 = new Button();
        option3.setGraphic(new ImageView(new Image("resources/buttons/set5/option3.png")));
        option3.setLayoutX(390);
        option3.setLayoutY(575);
        option3.setPadding(Insets.EMPTY);
        root.getChildren().add(option3);

        root.requestFocus();

        option3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                question.setText(
                        "Correct! Although the Internet can be helpful, make sure to only use trusted sources! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 5) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option3.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            option3.setDisable(true);
                            scene2QSet6(root, scene);
                        }
                    }
                });
            }
        });
    }

    
    /** 
     * Sixth set of questions to show the user
     * @param root Root Group 
     * @param scene Current Scene
     */
    public void scene2QSet6(Group root, Scene scene) {

        sceneState = 6;

        Text question = new Text(
                "Is it a good idea to only use Internet sources that provide negative information? (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set6/option1.png")));
        option1.setLayoutX(200);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set6/option2.png")));
        option2.setLayoutX(340);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        root.requestFocus();

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                question.setText(
                        "Correct! It is important to balance staying informed and staying positive! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 6) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            scene2QSet7(root, scene);
                        }
                    }
                });
            }
        });
    }

    
    /** 
     * Seventh set of questions to show the user
     * @param root Root Group
     * @param scene Current Scene
     */
    public void scene2QSet7(Group root, Scene scene) {

        sceneState = 7;

        Text question = new Text(
                "Is an official government website a good Internet source? (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set6/option1.png")));
        option1.setLayoutX(200);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set6/option2.png")));
        option2.setLayoutX(340);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        root.requestFocus();

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                question.setText(
                        "Correct! Government websites are one of the most reliable sources of information! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 7) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            scene2QSet8(root, scene);
                        }
                    }
                });
            }
        });
    }

    
    /**
     * Eighth and final set of questions to show the user 
     * @param root Root Group
     * @param scene Current Scene
     */
    public void scene2QSet8(Group root, Scene scene) {

        sceneState = 8;

        Text question = new Text(
                "If an Internet source gives information that doesn't match your opinion, should you trust it? (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set6/option1.png")));
        option1.setLayoutX(200);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set6/option2.png")));
        option2.setLayoutX(340);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        root.requestFocus();

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                question.setText(
                        "Correct! Don't avoid information that doesn't match your opinion! The information may be true! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 8) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            sceneState = 9;
                        }
                    }
                });
            }
        });
    }

    
    /** 
     * TextTool method is the method used to display and animate the text shown to the user
     * @param filepath the path of the file containing the text to be displayed
     * @param root Root Group
     * @param scene Current Scene
     * @throws IOException handles exception from file
     */
    public void textTool(String filepath, Group root, Scene scene) throws IOException {

        ArrayList<String> speakerCache = new ArrayList<String>();
        ArrayList<String> textCache = new ArrayList<String>();

        checkTime = 0;
        curTime = 0;
        disInt = 0;
        curIndex = 0;
        keyEventActive = false;
        animationLocked = true;

        Scanner sc = new Scanner(new File(filepath));

        while (sc.hasNext()) {
            speakerCache.add(sc.nextLine());
            textCache.add(sc.nextLine());
        }
        sc.close();

        currentString = textCache.get(curIndex);

        ImageView textBox = new ImageView(new Image("resources/textBox.png"));
        textBox.setX(0);
        textBox.setY(506);
        root.getChildren().add(textBox);

        Text speakerText = new Text();
        speakerText.setX(20);
        speakerText.setY(540);
        speakerText.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        speakerText.setWrappingWidth(600);
        speakerText.setTextAlignment(TextAlignment.CENTER);
        speakerText.setFill(Color.WHITE);
        root.getChildren().add(speakerText);

        Text text = new Text();
        text.setX(20);
        text.setY(570);
        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        text.setWrappingWidth(600);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
        root.getChildren().add(text);

        ImageView arrow = new ImageView(new Image("resources/arrow.png"));
        arrow.setX(590);
        arrow.setY(605);
        arrow.setPreserveRatio(true);
        arrow.setFitHeight(28);
        arrow.setFitWidth(28);
        arrow.setVisible(false);
        root.getChildren().add(arrow);

        AnimationTimer playText = new AnimationTimer() {

            @Override
            public void handle(long nanos) {
                int milis = (int) (nanos / 1000000);
                curTime = (int) Math.floor(0.03 * milis);
                if (disInt == currentString.length()) {
                    arrow.setVisible(true);
                    curIndex++;
                    keyEventActive = true;
                    this.stop();
                } else if (checkTime != curTime) {
                    text.setText(currentString.substring(0, disInt + 1));
                    speakerText.setText(speakerCache.get(curIndex));
                    checkTime = curTime;
                    disInt++;
                    keyEventActive = true;
                }
            }
        };

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    if (curIndex == textCache.size()) {
                        speakerText.setVisible(false);
                        text.setVisible(false);
                        arrow.setVisible(false);
                        keyEventActive = false;
                        animationLocked = false;
                        playText.stop();
                        if (sceneState == 9) {
                            sceneState = 10;
                        }
                    } else if (keyEventActive) {
                        if (disInt < currentString.length()) {
                            disInt = currentString.length();
                            text.setText(currentString);
                        } else {
                            disInt = 0;
                        }
                        currentString = textCache.get(curIndex);
                        arrow.setVisible(false);
                        keyEventActive = false;
                        playText.start();
                    }
                }
            }
        });
        playText.start();
    }
}
