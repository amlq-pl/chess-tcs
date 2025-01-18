package pl.tcs.tcschess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.tcs.tcschess.scenes.LandingScene;
import pl.tcs.tcschess.scenes.MainScene;
import pl.tcs.tcschess.scenes.SceneNames;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager manager = new SceneManager(stage);
        Scene landingScene = new LandingScene(manager);
        Scene mainScene = new MainScene(manager);

        manager.loadScene(SceneNames.LANDING, landingScene);
        manager.loadScene(SceneNames.MAIN, mainScene);

        stage.setResizable(false);

        manager.switchScene(SceneNames.LANDING);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}