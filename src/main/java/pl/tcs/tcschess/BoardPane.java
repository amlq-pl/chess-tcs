package pl.tcs.tcschess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
        System.out.println(tile.row + " " + tile.col);

        if (clickedTile != null) {
            if (tile == clickedTile) {
                clickedTile.resetColor();
            } else {
                String from = clickedTile.getPosition();
                List<Move> legal = board.legalMoves();
                System.out.println(legal);
                Move move = getMove(from, p, legal);

                if (legal.contains(move)) {
                    String note = "";
                    Side side = board.getSideToMove();
                    if (side == Side.WHITE) {
                        cnt++;
                        note += cnt + ".";
                    }
                    PieceColor color = clickedTile.getPiece().getColor();
                    Piece piece = clickedTile.getPiece();
                    clickedTile.removePiece();
                    if (move.toString().equals("e1g1") || move.toString().equals("e1c1")
                            || move.toString().equals("e8c8") || move.toString().equals("e8g8")) {
                        if (move.toString().equals("e1g1")) {
                            Piece tmp = tiles[7][7].getPiece();
                            tiles[7][7].removePiece();
                            tiles[5][7].setPiece(tmp);
                            note += " O-O";
                        } else if (move.toString().equals("e1c1")) {
                            Piece tmp = tiles[0][7].getPiece();
                            tiles[0][7].removePiece();
                            tiles[3][7].setPiece(tmp);
                            note += " O-O-O";
                        } else if (move.toString().equals("e8c8")) {
                            Piece tmp = tiles[0][0].getPiece();
                            tiles[0][0].removePiece();
                            tiles[3][0].setPiece(tmp);
                            note += " O-O-O";
                        } else if (move.toString().equals("e8g8")) {
                            Piece tmp = tiles[7][0].getPiece();
                            tiles[7][0].removePiece();
                            tiles[5][0].setPiece(tmp);
                            note += " O-O";
                        }
                        tile.setPiece(piece);
                        board.doMove(move);
                    } else {
                        tile.removePiece();
                        if (move.getPromotion() != com.github.bhlangonijr.chesslib.Piece.NONE) {
                            com.github.bhlangonijr.chesslib.Piece promotion = move.getPromotion();
                            tile.setPiece(new Piece(promotion, color));
                        } else tile.setPiece(piece);
                        if (board.getEnPassant() != Square.NONE && board.getEnPassant().toString().equals(p)) {
                            Square enPassant = board.getEnPassantTarget();
                            Tile t = TileMap.get(enPassant.toString());
                            t.removePiece();
                        }

                        board.doMove(move);
                        System.out.println(board.toString());

                        note += " " + board.getPiece(move.getTo()).getSanSymbol();
                        note += move.getTo().toString();
                        if (board.isMated()) note += "#";
                        else if (board.isKingAttacked()) note += "+";
                    }
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

    private Move getMove(String from, String p, List<Move> legal) {
        Move move = new Move(from + p, board.getSideToMove());

        // promotion
        String promotionChoice = "";
        if (legal.contains(new Move(from + p + 'q', board.getSideToMove()))) {
            Stage stage = new Stage();
            PromotionPopUp popUp = new PromotionPopUp(board.getSideToMove(), stage, promotionChoice);
            Scene scene = new Scene(popUp);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setResizable(false);
            stage.showAndWait();

            move = new Move(from + p + popUp.getChoice(), board.getSideToMove());
        }
        System.out.println(move);
        return move;
    }

    static class PromotionPopUp extends HBox {
        private final PieceColor color;
        private final Stage stage;
        private String choice;
        public PromotionPopUp(Side side, Stage stage, String choice) {
            super();
            this.color = (side == Side.WHITE) ? PieceColor.WHITE : PieceColor.BLACK;
            this.stage = stage;
            this.choice = choice;
            setFillHeight(false);
            init();
        }
        
        private void init() {
            Tile knight = new Tile(this.color, 40, 0, 0);
            Tile bishop = new Tile(this.color, 40, 0, 0);
            Tile rook = new Tile(this.color, 40, 0, 0);
            Tile queen = new Tile(this.color, 40, 0, 0);
            
            knight.setPiece(new Piece(Type.KNIGHT, this.color));
            bishop.setPiece(new Piece(Type.BISHOP, this.color));
            rook.setPiece(new Piece(Type.ROOK, this.color));
            queen.setPiece(new Piece(Type.QUEEN, this.color));
            
            knight.setOnMouseClicked(mouseEvent -> {
                choice = "n";
                stage.close();
            });
            bishop.setOnMouseClicked(mouseEvent -> {
                choice = "b";
                stage.close();
            });
            rook.setOnMouseClicked(mouseEvent -> {
                choice = "r";
                stage.close();
            });
            queen.setOnMouseClicked(mouseEvent -> {
                choice = "q";
                stage.close();
            });
            
            getChildren().addAll(knight, bishop, rook, queen);
        }

        public String getChoice() {
            return choice;
        }
    }
}
