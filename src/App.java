import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    HouseScene houseScene;
    DreamScene dreamScene;
    SubwayScene subwayScene;
    TrainScene trainScene;
    SplashscreenLoader splashscreenLoader;
    MainMenuLoader mainMenuLoader;
    InstructionsLoader instructionsLoader;

    Scene splashscreen;
    Scene mainMenu;
    Scene instructionsMenu;
    Scene leaderboardMenu;
    Scene usernameScene;
    Scene prevScene;
    Scene currentScene;

    boolean canShowStage = false;

    String currentUsername = "";
    String currentTime = "";

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    /**
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        prevScene = null;

        splashscreenLoader = new SplashscreenLoader();
        mainMenuLoader = new MainMenuLoader();
        instructionsLoader = new InstructionsLoader();

        houseScene = new HouseScene();
        dreamScene = new DreamScene();
        subwayScene = new SubwayScene();
        trainScene = new TrainScene();

        splashscreen = splashscreenLoader.load();
        mainMenu = mainMenuLoader.load();
        instructionsMenu = instructionsLoader.load();

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

                if (instructionsLoader.finished) {
                    currentScene = mainMenu;
                    instructionsLoader.finished = false;
                    mainMenuLoader.goToInstruct = false;
                }

                if (mainMenuLoader.goToInstruct) {
                    currentScene = instructionsMenu;
                }

                if (mainMenuLoader.finished) {
                    houseScene.started = true;
                    currentScene = houseScene;
                }

                if (houseScene.paused) {
                    currentScene = dreamScene;
                }

                if (dreamScene.sceneState == 10) {
                    if (!houseScene.resumed) {
                        houseScene.player.gameCenter.set(4 * Cell.sideLength, 4 * Cell.sideLength);
                        houseScene.background.setImage(houseScene.img2);
                        houseScene.cellMap = houseScene.createMap(22, 22);
                        CellArrayTextParser.parseAndApply("src/HouseScene/HouseSceneMap2.txt",
                                houseScene.cellMap);
                        houseScene.player.cellMap = houseScene.cellMap;
                        houseScene.player.canMove = true;
                    }
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
