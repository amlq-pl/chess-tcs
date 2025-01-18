package pl.tcs.tcschess.scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.tcs.tcschess.Board;
import pl.tcs.tcschess.SceneManager;

public class MainScene extends Scene {
    private final int boardSize = 640;
    private final SceneManager sceneManager;
    private final Board board = new Board(boardSize);

    public MainScene(SceneManager sceneManager) {
        super(new VBox(10)); // Base layout and size
        this.sceneManager = sceneManager;
        initializeScene((VBox) getRoot());
    }

    private void initializeScene(VBox root) {
        root.setPadding(new Insets(20));

        HBox upper = new HBox(10);
        upper.setPadding(new Insets(20));

        TextArea textField = new TextArea();
        textField.setPrefWidth(200);
        textField.setEditable(false);
        textField.setWrapText(true);
        textField.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: lightgrey;");
        textField.textProperty().setValue("Przebieg gry:\n");

        upper.getChildren().addAll(board, textField);

        HBox lower = new HBox(10);
        lower.setPadding(new Insets(20));
        lower.setPrefWidth(this.getWidth());
        lower.setSpacing(20);

        Button prevButton = new Button("Previous");

        prevButton.setOnAction(e -> sceneManager.switchToPreviousScene());

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> textField.textProperty().setValue(textField.getText() + "Siema\n"));

        lower.getChildren().addAll(prevButton, nextButton);

        root.getChildren().addAll(upper, lower);
    }
}
