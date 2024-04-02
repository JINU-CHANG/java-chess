package chess.dao;

import chess.DBConnection;
import chess.domain.board.ChessBoard;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;
import chess.dto.SquareDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SquareDao {

    public void insertSquare(final ChessBoard chessBoard, final Position position, final Piece piece) {
        final String query = "INSERT INTO squares VALUES(?, ?, ?, ?, ?)";

        try (final var connection = DBConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, chessBoard.getId());
            preparedStatement.setInt(2, position.getFileIndex());
            preparedStatement.setInt(3, position.getRankIndex());
            preparedStatement.setString(4, piece.getColor().name());
            preparedStatement.setString(5, piece.getPieceType().name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertSquare(final ChessBoard chessBoard, final Position position,  final Color color, final PieceType pieceType) {
        final String query = "INSERT INTO squares VALUES(?, ?, ?, ?, ?)";

        try (final var connection = DBConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, chessBoard.getId());
            preparedStatement.setInt(2, position.getFileIndex());
            preparedStatement.setInt(3, position.getRankIndex());
            preparedStatement.setString(4, color.name());
            preparedStatement.setString(5, pieceType.name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSquare(final ChessBoard chessBoard, final Position position) {
        final String query = "DELETE FROM Squares WHERE board_id = (?) AND square_file = (?) AND square_rank = (?)";

        try (final var connection = DBConnection.getConnection();
            final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chessBoard.getId());
            preparedStatement.setInt(2, position.getFileIndex());
            preparedStatement.setInt(3, position.getRankIndex());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SquareDto findBy(final ChessBoard chessBoard, final Position position) {
        final String query = "SELECT * FROM Squares WHERE board_id = (?) AND square_file = (?) AND square_rank = (?)";

        try (final var connection = DBConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chessBoard.getId());
            preparedStatement.setInt(2, position.getFileIndex());
            preparedStatement.setInt(3, position.getRankIndex());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int boardId = resultSet.getInt("board_id");
                    int file = resultSet.getInt("square_file");
                    int rank = resultSet.getInt("square_rank");
                    String color = resultSet.getString("color");
                    String piece = resultSet.getString("piece_type");

                    return SquareDto.from(boardId, file, rank, color, piece);
                } else {
                    throw new RuntimeException("[ERROR] 기물의 위치를 조회하는 과정에서 에러가 발생했습니다.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
