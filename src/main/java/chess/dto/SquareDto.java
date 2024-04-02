package chess.dto;

import chess.domain.piece.Color;
import chess.domain.piece.PieceType;

public record SquareDto(int boardId, int file, int rank, Color color, PieceType piece_type) {

    public static SquareDto from(int boardId, int file, int rank, String color, String piece_type) {
        return new SquareDto(boardId, file, rank, Color.from(color), PieceType.from(piece_type));
    }
}
