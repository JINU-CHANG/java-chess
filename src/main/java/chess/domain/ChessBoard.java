package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.type.Bishop;
import chess.domain.piece.type.King;
import chess.domain.piece.type.Night;
import chess.domain.piece.type.Pawn;
import chess.domain.piece.type.Queen;
import chess.domain.piece.type.Rook;
import java.util.HashSet;
import java.util.Set;

public class ChessBoard {

    private final Set<Piece> pieces;

    public ChessBoard(final Set<Piece> pieces) {
        this.pieces = pieces;
    }

    public static ChessBoard init() {
        final Set<Piece> pieces = new HashSet<>();

        pieces.addAll((createPieceWithoutPawn(Color.WHITE, 8)));
        pieces.addAll((createPawn(Color.WHITE, 7)));
        pieces.addAll((createPawn(Color.BLACK, 2)));
        pieces.addAll((createPieceWithoutPawn(Color.BLACK, 1)));

        return new ChessBoard(pieces);
    }

    private static Set<Piece> createPawn(final Color color, final int rank) {
        return Set.of(new Pawn(color, new Position('a', rank)),
                new Pawn(color, new Position('b', rank)),
                new Pawn(color, new Position('c', rank)),
                new Pawn(color, new Position('d', rank)),
                new Pawn(color, new Position('e', rank)),
                new Pawn(color, new Position('f', rank)),
                new Pawn(color, new Position('g', rank)),
                new Pawn(color, new Position('h', rank)));
    }

    private static Set<Piece> createPieceWithoutPawn(final Color color, final int rank) {
        return Set.of(new Rook(color, new Position('a', rank)),
                new Night(color, new Position('b', rank)),
                new Bishop(color, new Position('c', rank)),
                new Queen(color, new Position('d', rank)),
                new King(color, new Position('e', rank)),
                new Bishop(color, new Position('f', rank)),
                new Night(color, new Position('g', rank)),
                new Rook(color, new Position('h', rank)));
    }

    public Set<Piece> getPieces() {
        return pieces;
    }
}
