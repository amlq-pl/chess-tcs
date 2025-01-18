package pl.tcs.tcschess;

import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.tcs.tcschess.scenes.SceneNames;

import java.util.HashMap;

public class SceneManager {
    private final Stage primaryStage;
    private Scene previous = null;
    private Scene current = null;
    private final HashMap<SceneNames, Scene> scenes = new HashMap<>();

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void loadScene(SceneNames sceneName, Scene scene) {
        if (!scenes.containsKey(sceneName)) {
            scenes.put(sceneName, scene);
        }
    }

    public boolean switchToPreviousScene() {
        if (previous != null) {
            Scene temp = current;
            current = previous;
            previous = temp;

            primaryStage.setScene(current);

            return true;
        }

        return false;
    }

    public void switchScene(SceneNames sceneName) {
        System.out.printf("Switching to scene %s.\n", sceneName);
        previous = primaryStage.getScene();
        Scene scene = scenes.get(sceneName);
        primaryStage.setScene(scene);
        current = scene;
    }
}
