package chess.controller;

import static chess.exception.RetryHandler.retryOnException;

import chess.domain.Command;
import chess.dto.ChessBoardResponse;
import chess.dto.CommandRequest;
import chess.domain.ChessBoard;
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
        printCommandMenu();
        retryOnException(this::startGame);
    }

    private void startGame() {
        final Command command = Command.fromStart(inputView.readCommand());

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
            final CommandRequest commandRequest = CommandRequest.fromPlay(inputView.readCommand());

            if (commandRequest.isEnd()) {
                break;
            }

            if (commandRequest.isMove()) {
                move(chessBoard, commandRequest.getBody());
                printChessBoard(chessBoard);
            }
        }
    }

    private void printCommandMenu() {
        outputView.printCommandMenu();
    }

    private void move(final ChessBoard chessBoard, final List<String> command) {
        final Position current = new Position(command.get(0));
        final Position destination = new Position(command.get(1));

        chessBoard.move(current, destination);
    }
}
