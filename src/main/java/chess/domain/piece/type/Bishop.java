package chess.domain.piece.type;

import chess.domain.position.Movement;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public class Bishop extends Piece {

    private static final double BISHOP_SCORE = 3.0;

    public Bishop(final Color color) {
        super(color);
    }

    @Override
    public boolean canMove(final Movement movement) {
        return movement.isDiagonal();
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public double getScore() {
        return BISHOP_SCORE;
    }
}
