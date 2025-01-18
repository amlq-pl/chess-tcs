package pl.tcs.tcschess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Piece extends ImageView {
    private PieceColor color;
    private Type type;

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

    public PieceColor getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }
}
