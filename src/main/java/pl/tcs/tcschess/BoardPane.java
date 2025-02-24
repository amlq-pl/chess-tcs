package pl.tcs.tcschess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;

public class BoardPane extends GridPane {
    Board board;
    private boolean gameOver = false;
    private final int size;
    private Tile clickedTile = null;
    private final Tile[][] tiles = new Tile[8][8];
    private final HashMap<String, Tile> TileMap = new HashMap<>();
    private final FenParser parser = new FenParser();
    private final String startingFen;
    public StringProperty text = new SimpleStringProperty("Przebieg gry:\n");
    int cnt = 0;

    public BoardPane(int size, String startingFen) {
        this.size = size;
        this.startingFen = startingFen;
        board = new Board();
        draw();
    }

    public BoardPane(int size) {
        this(size, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    private void draw() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile tile;
                if ((i + j) % 2 == 0) {
                    tile = new Tile(PieceColor.WHITE, this.size, i, j);
                } else {
                    tile = new Tile(PieceColor.BLACK, this.size, i, j);
                }

                tiles[i][j] = tile;
                tiles[i][j].setOnMouseClicked(mouseEvent -> handleTileClick(tile));
                TileMap.put(tiles[i][j].getPosition(), tile);

                this.add(tile, i, j);
            }
        }

        Piece[][] pieces = parser.parseFEN(startingFen);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j].getType() != Type.NONE) {
                    tiles[j][i].setPiece(pieces[i][j]);
                }
            }
        }
    }

    public void handleTileClick(Tile tile) {
        if (gameOver) return;
        String p = tile.getPosition();

        if (clickedTile != null) {
            if (tile == clickedTile) {
                clickedTile.resetColor();
            } else {
                String from = clickedTile.getPosition();
                List<Move> legal = board.legalMoves();
                System.out.println(legal);
                Move move = new Move(from + p, board.getSideToMove());

                if (legal.contains(move)) {
                    Piece piece = clickedTile.getPiece();
                    clickedTile.removePiece();
                    tile.removePiece();
                    tile.setPiece(piece);
                    if (board.getEnPassant() != Square.NONE && board.getEnPassant().toString().equals(p)) {
                        Square enPassant = board.getEnPassantTarget();
                        Tile t = TileMap.get(enPassant.toString());
                        t.removePiece();
                    }
                    Side side = board.getSideToMove();
                    board.doMove(move);
                    System.out.println(board.toString());
                    String note = "";
                    if (side == Side.WHITE) {
                        cnt++;
                        note += cnt + ".";
                    }
                    note += " " + board.getPiece(move.getTo()).getSanSymbol();
                    note += move.getTo().toString();
                    if (board.isMated()) note += "#";
                    else if (board.isKingAttacked()) note += "+";
                    if (side == Side.BLACK) {
                        note += "\n";
                    } else note += " ";
                    text.setValue(text.getValue() + note);
                }
            }
            for (Tile t : TileMap.values()) {
                t.resetColor();
            }
            clickedTile = null;
        } else {
            if (board.getPiece(Square.fromValue(p)) != null) {
                if (board.getPiece(Square.fromValue(p)).getPieceSide() == board.getSideToMove()) {
                    clickedTile = tile;
                    clickedTile.setColor(Color.YELLOW);

                    List<Move> legal = board.legalMoves();
                    for (Move m : legal) {
                        String tmp = m.getFrom().toString();
                        String to = m.getTo().toString();
                        if (tmp.equals(clickedTile.getPosition())) {
                            Tile t = TileMap.get(to);
                            t.setColor(Color.GREEN);
                        }
                    }
                }
            }
        }

        if (board.isMated()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Szach Mat");
            alert.show();
            gameOver = true;
        } else if (board.isDraw()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Remis");
            alert.show();
            gameOver = true;
        }
    }
}
