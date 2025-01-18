package pl.tcs.tcschess;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class Board extends GridPane {
    private final int size;
    private final boolean[][] clicked = new boolean[8][8];
    private final HashMap<Piece, Tile> pieceMap = new HashMap<>();
    private Tile clickedTile = null;
    private final Tile[][] tiles = new Tile[8][8];
    private Piece[][] pieces = new Piece[8][8];
    private final FenParser parser = new FenParser();
    private final String startingFen;

    public Board(int size, String startingFen) {
        this.size = size;
        this.startingFen = startingFen;
        draw();
    }

    public Board(int size) {
        this.size = size;
        this.startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        draw();
    }

    private void draw() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile tile;
                if ((i + j) % 2 == 0) {
                    tile = new Tile(PieceColor.WHITE, this.size);
                } else {
                    tile = new Tile(PieceColor.BLACK, this.size);
                }

                tiles[i][j] = tile;

                int finalI = i;
                int finalJ = j;
                tiles[i][j].setOnMouseClicked(mouseEvent -> handleTileClick(tile, finalI, finalJ));

                this.add(tile, i, j);
            }
        }

        pieces = parser.parseFEN(startingFen);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j].getType() != Type.NONE) {
                    tiles[j][i].setPiece(pieces[i][j]);
                }
            }
        }
    }

    public void handleTileClick(Tile tile, int i, int j) {
        System.out.println(tile.getPiece());
        if (clickedTile != null) {
            if (tile == clickedTile) {
                clickedTile.resetColor();
            } else {
                Piece piece = clickedTile.getPiece();
                tile.removePiece();
                tile.setPiece(piece);
                clickedTile.removePiece();
            }
            clickedTile.resetColor();
            clickedTile = null;
        } else {
            if (tile.getPiece() != null) {
                clickedTile = tile;
                clickedTile.setColor(Color.YELLOW);
            }
        }
    }

    public void move() {

    }
}
