package pl.tcs.tcschess.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pl.tcs.tcschess.Board;
import pl.tcs.tcschess.SceneManager;

import java.util.Objects;

public class LandingScene extends Scene {
    private final SceneManager sceneManager;
    public LandingScene(SceneManager sceneManager) {
        super(new StackPane(), 1000, 1000); // Base layout and size
        this.sceneManager = sceneManager;
        initializeScene((StackPane) getRoot());
    }

    private void initializeScene(StackPane root) {
        root.setPadding(new Insets(20));
        Board board = new Board(1000 - 20 * 2);

        Label title = new Label("TCS Chess");
        title.getStyleClass().add("title");

        Button playButton = new Button("Play");
        playButton.getStyleClass().add("button");

        VBox vert = new VBox(10);
        vert.getChildren().addAll(title, playButton);

        vert.setAlignment(Pos.CENTER);

        try {
            this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        playButton.setOnAction(e -> sceneManager.switchScene(SceneNames.MAIN));

        root.getChildren().addAll(board, vert);
    }
}

