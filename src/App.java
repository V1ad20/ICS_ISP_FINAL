import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {
    HouseScene houseScene;
    DreamScene dreamScene;
    SubwayScene subwayScene;
    TrainScene trainScene;
    SplashscreenLoader splashscreenLoader = new SplashscreenLoader();
    MainMenuLoader mainMenuLoader = new MainMenuLoader();
    Scene prevScene;
    Scene currentScene;
    boolean canShowStage = false;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        prevScene = null;

        Scene splashscreen = splashscreenLoader.load();
        Scene mainMenu = mainMenuLoader.load();
        houseScene = new HouseScene();
        dreamScene = new DreamScene();
        subwayScene = new SubwayScene();
        trainScene = new TrainScene();

        // splashscreenLoader.finished = true;
        // mainMenuLoader.finished = true;
        // houseScene.paused = true;

        currentScene = splashscreen;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long nanos) {
                if (splashscreenLoader.finished) {
                    currentScene = mainMenu;
                }

                if (mainMenuLoader.finished) {
                    houseScene.started = true;
                    currentScene = houseScene;
                }

                if (houseScene.paused) {
                    currentScene = dreamScene;
                }

                if (dreamScene.sceneState == 10) {
                    if (!houseScene.resumed)
                        houseScene.player.gameCenter.set(4 * Cell.sideLength, 4 * Cell.sideLength);
                    houseScene.resumed = true;
                    currentScene = houseScene;
                }

                if (houseScene.finished) {
                    subwayScene.started = true;
                    currentScene = subwayScene;
                }

                if (subwayScene.finished) {
                    trainScene.started = true;
                    currentScene = trainScene;
                }

                if (currentScene != prevScene) {
                    stage.setScene(currentScene);
                    prevScene = currentScene;
                }
            }
        };

        timer.start();
        stage.setScene(currentScene);
        stage.show();

    }

}
