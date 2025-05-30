package pl.tcs.tcschess;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
    private final Rectangle rectangle;
    private Piece piece = null;
    private final PieceColor pieceColor;
    private final int size;
    public int row;
    public int col;

    public Tile(PieceColor pieceColor, int size, int row, int col) {
        super();
        this.row = row;
        this.col = col;
        this.size = size;
        this.pieceColor = pieceColor;
        this.rectangle = new Rectangle((double) this.size /8, (double) this.size /8);
        if (pieceColor == PieceColor.BLACK) {
            this.rectangle.setFill(Color.SADDLEBROWN);
        } else {
            this.rectangle.setFill(Color.SANDYBROWN);
        }

        this.maxHeight((double) this.size / 8);
        this.maxWidth((double) this.size / 8);

        this.getChildren().add(this.rectangle);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        piece.fitHeightProperty().bind(this.heightProperty());
        piece.fitWidthProperty().bind(this.widthProperty());
        this.getChildren().add(this.piece);
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void removePiece() {
        if (this.piece != null) {
            this.getChildren().remove(this.piece);
        }
    }

    public PieceColor getPieceColor() {
        return this.pieceColor;
    }

    public void setColor(Color color) {
        this.rectangle.setFill(color);
    }

    public void resetColor() {
        if (this.pieceColor == PieceColor.BLACK) {
            this.rectangle.setFill(Color.SADDLEBROWN);
        } else {
            this.rectangle.setFill(Color.SANDYBROWN);
        }
    }

    public String getPosition() {
        String letters = "ABCDEFGH";
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8};
        StringBuilder position = new StringBuilder();
        position.append(letters.charAt(row));
        position.append(nums[7-col]);
        return position.toString();
    }
}
