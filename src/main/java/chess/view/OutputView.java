package chess.view;

import chess.domain.Scores;
import chess.dto.ChessBoardResponse;
import java.util.List;

public class OutputView {

    public void printCommandMenu() {
        System.out.println("""
                > 체스 게임을 시작합니다.
                > 게임 시작 : start
                > 게임 종료 : end
                > 게임 이동 : move source위치 target위치 - 예. move b2 b3""");
    }

    public void printChessBoard(final ChessBoardResponse chessBoardDto) {
        chessBoardDto.chessBoard().forEach(this::printChessRow);
    }

    public void printScores(final Scores scores) {
        final double blackScore = scores.getBlackScore();
        final double whiteScore = scores.getWhiteScore();

        System.out.println(String.format("BLACK 점수 : %.2f, WHITE 점수 : %.2f", blackScore, whiteScore));
    }

    private void printChessRow(final List<Character> row) {
        row.forEach(System.out::print);
        System.out.println();
    }
}
