package pl.tcs.tcschess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Piece extends ImageView {
    private PieceColor color;
    private Type type;
    private boolean hasMoved = false;

    public Piece() {
        this.color = PieceColor.NONE;
        this.type = Type.NONE;
    }
    public Piece(Type type, PieceColor color) {
        super();
        this.color = color;
        this.type = type;

        String path = color.toString().toLowerCase().charAt(0) + type.toString().toLowerCase() + ".png";

        Image image = new Image(path);
        setImage(image);
        setPreserveRatio(true);
    }

    public Piece(com.github.bhlangonijr.chesslib.Piece type, PieceColor color) {
        super();
        this.color = color;

        String path = color.toString().toLowerCase().charAt(0) + type.toString().toLowerCase().substring(6) + ".png";
        System.out.println(path);

        Image image = new Image(path);
        setImage(image);
        setPreserveRatio(true);
    }

    public PieceColor getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMovedTrue() {
        hasMoved = true;
    }
}
