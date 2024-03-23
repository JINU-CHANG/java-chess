package chess.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import chess.domain.Movement;
import chess.domain.piece.File;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RouteCalculatorTest {

    @DisplayName("수직으로 사이에 있는 위치들을 반환한다.")
    @Test
    void getVerticalMiddlePositions() {
        // given
        final Movement movement = new Movement(new Position(File.F, Rank.EIGHT), new Position(File.F, Rank.ONE));

        // when
        final Set<Position> positions = RouteCalculator.getVerticalMiddlePositions(movement);

        // then
        assertThat(positions).containsExactlyInAnyOrder(
                new Position(File.F, Rank.SEVEN),
                new Position(File.F, Rank.SIX),
                new Position(File.F, Rank.FIVE),
                new Position(File.F, Rank.FOUR),
                new Position(File.F, Rank.THREE),
                new Position(File.F, Rank.TWO)
        );
    }

    @DisplayName("수평으로 사이에 있는 위치들을 반환한다.")
    @Test
    void getHorizontalMiddlePositions() {
        // given
        final Movement movement = new Movement(new Position(File.H, Rank.FOUR), new Position(File.D, Rank.FOUR));

        // when
        final Set<Position> positions = RouteCalculator.getHorizontalMiddlePositions(movement);

        // then
        assertThat(positions).containsExactlyInAnyOrder(
                new Position(File.G, Rank.FOUR),
                new Position(File.F, Rank.FOUR),
                new Position(File.E, Rank.FOUR)
        );
    }

    @DisplayName("오른쪽으로 사이에 있는 위치들을 반환한다.")
    @Test
    void getRightDiagonalMiddlePositions() {
        // given
        final Movement movement = new Movement(new Position(File.C, Rank.THREE), new Position(File.F, Rank.SIX));

        // when
        final Set<Position> positions = RouteCalculator.getRightDiagonalMiddlePositions(movement);

        // then
        assertThat(positions).containsExactlyInAnyOrder(
                new Position(File.D, Rank.FOUR),
                new Position(File.E, Rank.FIVE)
        );
    }

    @DisplayName("왼쪽으로 사이에 있는 위치들을 반환한다.")
    @Test
    void getLeftDiagonalMiddlePositions() {
        // given
        final Movement movement = new Movement(new Position(File.B, Rank.SIX), new Position(File.E, Rank.THREE));

        // when
        final Set<Position> positions = RouteCalculator.getLeftDiagonalMiddlePositions(movement);

        // then
        assertThat(positions).containsExactlyInAnyOrder(
                new Position(File.C, Rank.FIVE),
                new Position(File.D, Rank.FOUR)
        );
    }
}
