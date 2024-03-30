package chess.controller;

import static chess.exception.RetryHandler.retryOnException;

import chess.domain.Scores;
import chess.dto.ChessBoardResponse;
import chess.dto.CommandRequest;
import chess.domain.board.ChessBoard;
import chess.util.ChessBoardInitializer;
import chess.domain.position.Position;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.List;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printCommandMenu();
        retryOnException(this::startGame);
    }

    private void startGame() {
        final CommandRequest command = CommandRequest.fromStart(inputView.readCommand());

        if (command.isStart()) {
            final ChessBoard chessBoard = ChessBoardInitializer.init();

            printChessBoard(chessBoard);
            retryOnException(() -> playTurn(chessBoard));
        }
    }

    private void printChessBoard(final ChessBoard chessBoard) {
        final ChessBoardResponse chessBoardResponse = ChessBoardResponse.from(chessBoard.getPieces());
        outputView.printChessBoard(chessBoardResponse);
    }

    private void playTurn(final ChessBoard chessBoard) {
        while (true) {
            final CommandRequest command = CommandRequest.fromPlay(inputView.readCommand());

            if (command.isEnd()) {
                break;
            }

            if (command.isMove()) {
                move(chessBoard, command);
                printChessBoard(chessBoard);
            }

            if (command.isStatus()) {
                outputView.printScores(Scores.from(chessBoard));
            }

            if (chessBoard.isKingCaught()) {
                outputView.printWinner(chessBoard.getTurn());
                break;
            }
        }
    }

    private void move(final ChessBoard chessBoard, final CommandRequest commandRequest) {
        final List<String> body = commandRequest.getBody();

        final Position current = new Position(body.get(0));
        final Position destination = new Position(body.get(1));

        chessBoard.move(current, destination);
    }
}
