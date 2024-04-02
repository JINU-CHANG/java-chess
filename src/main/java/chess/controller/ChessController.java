package chess.controller;

import static chess.exception.RetryHandler.retryOnException;

import chess.domain.Scores;
import chess.dto.ChessBoardResponse;
import chess.dto.CommandRequest;
import chess.domain.board.ChessBoard;
import chess.service.ChessService;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ChessService chessService;

    public ChessController(final InputView inputView, final OutputView outputView, final ChessService chessService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.chessService = chessService;
    }

    public void run() {
        outputView.printCommandMenu();
        retryOnException(this::startGame);
    }

    private void startGame() {
        final CommandRequest command = CommandRequest.fromStart(inputView.readCommand());

        if (command.isStart()) {
            final ChessBoard chessBoard = chessService.createBoard();

            outputView.printChessBoard(ChessBoardResponse.from(chessBoard.getPieces()));
            retryOnException(() -> playTurn(chessBoard));
        }
    }

    private void playTurn(final ChessBoard chessBoard) {
        while (true) {
            final CommandRequest command = CommandRequest.fromPlay(inputView.readCommand());

            if (command.isMove()) {
                chessService.move(command, chessBoard);
                outputView.printChessBoard(ChessBoardResponse.from(chessBoard.getPieces()));
            }

            if (command.isStatus()) {
                outputView.printScores(Scores.from(chessBoard));
            }

            if (chessBoard.isKingCaught()) {
                outputView.printWinner(chessBoard.getTurn());
            }

            if (command.isEnd() || chessBoard.isKingCaught()) {
                break;
            }
        }
    }
}
