package chess.view;

import chess.dto.ChessBoardDto;
import java.util.List;

public class OutputView {

    public void printCommandMenu() {
        System.out.println("""
                > 체스 게임을 시작합니다.
                > 게임 시작 : start
                > 게임 종료 : end
                > 게임 이동 : move source위치 target위치 - 예. move b2 b3""");
    }

    public void printChessBoard(final ChessBoardDto chessBoardDto) {
        chessBoardDto.chessBoard().forEach(this::printChessRow);
    }

    private void printChessRow(final List<Character> row) {
        row.forEach(System.out::print);
        System.out.println();
    }
}
