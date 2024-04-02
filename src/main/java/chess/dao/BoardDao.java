package chess.dao;

import chess.DBConnection;
import chess.domain.board.ChessBoard;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BoardDao {

    public int insertBoard(final ChessBoard chessBoard) {
        final String query = "INSERT INTO boards VALUES(?, ?)";

        try (final var connection = DBConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, 0);
            preparedStatement.setString(2, chessBoard.getTurn().name());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to get generated keys, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(final ChessBoard chessBoard) {
        final String query = "UPDATE boards SET current_turn = (?) WHERE board_id = (?)";

        try (final var connection = DBConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, chessBoard.getTurn().name());
            preparedStatement.setInt(2, chessBoard.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
