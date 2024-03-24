package chess.domain.piece.type;

import chess.domain.position.Movement;
import chess.util.RouteCalculator;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.Set;

public class Bishop extends Piece {

    public Bishop(final Color color) {
        super(color);
    }

    @Override
    public Set<Position> getRoute(final Movement movement) {
        if (movement.isDiagonal()) {
            return RouteCalculator.getDiagonalPositions(movement);
        }

        throw new IllegalArgumentException("[ERROR] 전략상 이동할 수 없는 위치입니다.");
    }
}
