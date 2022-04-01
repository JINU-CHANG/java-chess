package chess.domain.piece.state.started;

import java.util.List;
import java.util.Map;

import chess.domain.game.state.position.Direction;
import chess.domain.game.state.position.Position;
import chess.domain.piece.Piece;
import chess.domain.piece.state.PieceState;

public class StartedKing extends NonContinuous {

    @Override
    public List<Position> findMovablePositions(Position source, Map<Position, Piece> board) {
        return findMovablePositions(Direction.all(), source, board);
    }

    @Override
    public PieceState updateState() {
        return new StartedKing();
    }
}
