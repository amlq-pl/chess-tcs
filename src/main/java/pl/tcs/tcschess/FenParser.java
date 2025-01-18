package pl.tcs.tcschess;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FenParser {
    public Piece[][] parseFEN(String fenString) {
        return Arrays.stream(fenString.split("/"))
                .map(expandRank)
                .toArray(Piece[][]::new);
    }

    private Piece fenParseCharacter(char c) {
        PieceColor pieceCol = (Character.isUpperCase(c)) ? PieceColor.WHITE : PieceColor.BLACK;
        switch(Character.toLowerCase(c)) {
            case 'k' -> {
                return new Piece(Type.KING, pieceCol);
            }
            case 'q' -> {
                return new Piece(Type.QUEEN, pieceCol);
            }
            case 'r' -> {
                return new Piece(Type.ROOK, pieceCol);
            }
            case 'b' -> {
                return new Piece(Type.BISHOP, pieceCol);
            }
            case 'n' -> {
                return new Piece(Type.KNIGHT, pieceCol);
            }
            case 'p' -> {
                return new Piece(Type.PAWN, pieceCol);
            }
        }

        return new Piece();
    }

    private final Function<String, Piece[]> expandRank = rank ->
            rank.chars()
                    .mapToObj(c -> Character.isDigit(c)
                            ? Stream.generate(Piece::new)
                            .limit(Character.getNumericValue(c))
                            .collect(Collectors.toList())
                            : List.of(fenParseCharacter((char) c)))
                    .flatMap(List::stream)
                    .toArray(Piece[]::new);
}

