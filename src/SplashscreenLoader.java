import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Arc;
import javafx.util.Duration;

public class SplashscreenLoader {
    boolean finished = false;

    
    /** 
     * @return Scene
     * @throws IOException
     */
    // Parent cR = loadParent();

    public Scene load() throws IOException {
        Parent companyRoot = FXMLLoader.load(getClass().getResource("resources/splashScreen/companyLogo.fxml"));
        Scene companyScene = new Scene(companyRoot, 640, 640);
        Arc arc = (Arc) companyScene.lookup("#arc");

        fadeIn(companyRoot, 3000);

        AnimationTimer timer2 = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                if (arc.getLength() > 0) {
                    arc.setLength(arc.getLength() - 3);
                } else {
                    this.stop();
                    finished = true;
                }
            }
        };

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                if (arc.getLength() < 360) {
                    arc.setLength(arc.getLength() + 3);
                } else {
                    timer2.start();
                    fadeOut(companyRoot, 2000);
                    this.stop();
                }

            }
        };
        timer.start();
        return companyScene;
    }

    
    /** 
     * @param root
     * @param time
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
     * @param root
     * @param time
     */
    public void fadeOut(Node root, int time) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(time));
        ft.setNode(root);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
    }

    
    /** 
     * @return Parent
     */
    private static Parent loadParent() {
        Object obj = new Object();
        try {
            return FXMLLoader.load(Object.class.getResource("resources/splashScreen/companyLogo.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
