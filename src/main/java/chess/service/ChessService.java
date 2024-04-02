package chess.service;

import chess.dao.BoardDao;
import chess.dao.SquareDao;
import chess.domain.board.ChessBoard;
import chess.domain.position.Position;
import chess.dto.CommandRequest;
import chess.dto.SquareDto;
import chess.util.ChessBoardInitializer;
import java.util.List;

public class ChessService {

    private final SquareDao squareDao;
    private final BoardDao boardDao;

    public ChessService(final SquareDao squareDao, final BoardDao boardDao) {
        this.squareDao = squareDao;
        this.boardDao = boardDao;
    }

    public ChessBoard createBoard() {
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final int boardId = boardDao.insertBoard(chessBoard);
        chessBoard.setId(boardId); //TODO set 개선 필요

        chessBoard.getPieces().forEach((key, value) -> squareDao.insertSquare(chessBoard, key, value));

        return chessBoard;
    }

    public void move(final CommandRequest commandRequest, final ChessBoard chessBoard) {
        final List<String> body = commandRequest.getBody();

        final Position current = new Position(body.get(0));
        final Position destination = new Position(body.get(1));

        final SquareDto squareDto = squareDao.findBy(chessBoard, current);

        chessBoard.move(current, destination);

        squareDao.deleteSquare(chessBoard, current);
        squareDao.insertSquare(chessBoard, destination, squareDto.color(), squareDto.piece_type());
        boardDao.updateTurn(chessBoard);
    }
}
