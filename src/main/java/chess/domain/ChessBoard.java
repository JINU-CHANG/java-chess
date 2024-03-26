package chess.domain;

import chess.domain.piece.Piece;
import chess.domain.position.Movement;
import chess.domain.position.Position;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class ChessBoard {

    private final Map<Position, Piece> pieces;

    public ChessBoard(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public void move(final Position current, final Position destination) {
        final Piece currentPiece = findPieceBy(current);
        final Movement movement = new Movement(current, destination);

        validateStrategy(currentPiece, movement);
        validatePieceInRoute(getRoute(currentPiece, movement));

        movePiece(currentPiece, movement);
    }

    public Piece findPieceBy(final Position position) {
        if (!isPieceExist(position)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
        }

        return pieces.get(position);
    }

    public Set<Position> getRoute(final Piece currentPiece, final Movement movement) {
        if (isPieceExist(movement.getDestination())) {
            validateOpponent(currentPiece, movement);
            return currentPiece.getCatchRoute(movement);
        }

        return currentPiece.getRoute(movement);
    }

    private void validateStrategy(final Piece currentPiece, final Movement movement) {
        if (!currentPiece.canMove(movement)) {
            throw new IllegalArgumentException("[ERROR] 전략상 이동할 수 없는 위치입니다.");
        }
    }

    private void validatePieceInRoute(final Set<Position> route) {
        boolean existPiece = pieces.keySet().stream().anyMatch(route::contains);

        if (existPiece) {
            throw new IllegalArgumentException("[ERROR] 경로상 기물이 존재합니다.");
        }
    }

    private void validateOpponent(final Piece currentPiece, final Movement movement) {
        final Piece targetPiece = findPieceBy(movement.getDestination());
        if (currentPiece.isOpponent(targetPiece)) {
            return;
        }

        throw new IllegalArgumentException("[ERROR] 잡을 수 없는 기물입니다.");
    }

    private boolean isPieceExist(final Position position) {
        return pieces.containsKey(position);
    }

    private void movePiece(final Piece currentPiece, final Movement movement) {
        pieces.remove(movement.getCurrent());
        pieces.put(movement.getDestination(), currentPiece);
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
