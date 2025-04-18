package pl.tcs.tcschess.scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.tcs.tcschess.BoardPane;
import pl.tcs.tcschess.Game;
import pl.tcs.tcschess.SceneManager;

public class MainScene extends Scene {
    private final int boardSize = 640;
    private final SceneManager sceneManager;
    private BoardPane board = new BoardPane(boardSize);
    private TextArea textField = new TextArea();
    private final Game game = new Game();

    public MainScene(SceneManager sceneManager) {
        super(new VBox(10)); // Base layout and size
        this.sceneManager = sceneManager;
        initializeScene((VBox) getRoot());
    }

    private void initializeScene(VBox root) {
        root.setPadding(new Insets(20));

        HBox upper = new HBox(10);
        upper.setPadding(new Insets(20));

        textField = initTextField();
        upper.getChildren().addAll(board, textField);

        HBox lower = new HBox(10);
        lower.setPadding(new Insets(20));
        lower.setPrefWidth(this.getWidth());
        lower.setSpacing(20);

        Button prevButton = new Button("Restart");

        prevButton.setOnAction(e -> {
            upper.getChildren().removeAll(board, textField);
            board = new BoardPane(boardSize);
            textField = initTextField();
            upper.getChildren().addAll(board, textField);
        });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> textField.textProperty().setValue(textField.getText() + "Siema\n"));

        lower.getChildren().addAll(prevButton, nextButton);

        root.getChildren().addAll(upper, lower);
    }

    private TextArea initTextField() {
        TextArea textField = new TextArea();
        textField.setPrefWidth(200);
        textField.setEditable(false);
        textField.setWrapText(true);
        textField.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: lightgrey;");
        textField.setStyle("-fx-font-size: 14px;");
        textField.textProperty().bindBidirectional(board.text);

        return textField;
    }
}
