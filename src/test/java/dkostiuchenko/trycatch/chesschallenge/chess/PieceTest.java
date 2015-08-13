package dkostiuchenko.trycatch.chesschallenge.chess;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static dkostiuchenko.trycatch.chesschallenge.BoardTruth.assertWithBoard;

public class PieceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Board board = new Board(5, 5);

    @Test
    public void byteValue() {
        List<Byte> bytes = new ArrayList<>();
        for (Piece piece : Piece.values()) {
            bytes.add(piece.byteValue());
        }

        List<Piece> pieces = new ArrayList<>();
        for (Byte byteValue : bytes) {
            pieces.add(Piece.fromByte(byteValue));
        }

        assertThat(pieces).containsExactly(Piece.values()).inOrder();
    }

    @Test
    public void none() {
        thrown.expect(UnsupportedOperationException.class);
        Piece.NONE.coveredSquares(board, 1, 1);
    }

    @Test
    public void queen() {
        assertWithBoard(board).that(Piece.QUEEN.coveredSquares(board, 2, 2))
                .containsExactly(0, 2, 4, 6, 7, 8, 10, 11, 12, 13, 14, 16, 17, 18, 20, 22, 24);
        assertWithBoard(board).that(Piece.QUEEN.coveredSquares(board, 0, 0))
                .containsExactly(0, 1, 2, 3, 4, 5, 6, 10, 12, 15, 18, 20, 24);
    }

    @Test
    public void rook() {
        assertWithBoard(board).that(Piece.ROOK.coveredSquares(board, 2, 2))
                .containsExactly(2, 7, 10, 11, 12, 13, 14, 17, 22);
        assertWithBoard(board).that(Piece.ROOK.coveredSquares(board, 0, 0))
                .containsExactly(0, 1, 2, 3, 4, 5, 10, 15, 20);
    }

    @Test
    public void bishop() {
        assertWithBoard(board).that(Piece.BISHOP.coveredSquares(board, 2, 2))
                .containsExactly(0, 4, 6, 8, 12, 16, 18, 20, 24);
        assertWithBoard(board).that(Piece.BISHOP.coveredSquares(board, 0, 0))
                .containsExactly(0, 6, 12, 18, 24);
    }

    @Test
    public void king() {
        assertWithBoard(board).that(Piece.KING.coveredSquares(board, 2, 2))
                .containsExactly(6, 7, 8, 11, 12, 13, 16, 17, 18);
        assertWithBoard(board).that(Piece.KING.coveredSquares(board, 0, 0))
                .containsExactly(0, 1, 5, 6);
    }

    @Test
    public void knight() {
        assertWithBoard(board).that(Piece.KNIGHT.coveredSquares(board, 2, 2))
                .containsExactly(1, 3, 5, 9, 12, 15, 19, 21, 23);
        assertWithBoard(board).that(Piece.KNIGHT.coveredSquares(board, 0, 0))
                .containsExactly(0, 7, 11);
    }

}
