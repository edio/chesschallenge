package dkostiuchenko.trycatch.chesschallenge.chess;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static dkostiuchenko.trycatch.chesschallenge.BoardTruth.assertWithBoard;
import static dkostiuchenko.trycatch.chesschallenge.chess.Piece.BISHOP;
import static dkostiuchenko.trycatch.chesschallenge.chess.Piece.KING;
import static dkostiuchenko.trycatch.chesschallenge.chess.Piece.NONE;
import static dkostiuchenko.trycatch.chesschallenge.chess.Piece.QUEEN;

public class BoardTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void square_validBounds() {
        Board b = new Board(4, 5);
        assertWithBoard(b).that(b.square(0, 0)).isEqualTo(0);
        assertWithBoard(b).that(b.square(3, 0)).isEqualTo(15);
        assertWithBoard(b).that(b.square(0, 4)).isEqualTo(4);
        assertWithBoard(b).that(b.square(3, 4)).isEqualTo(19);
    }

    @Test
    public void square_invalidBoundsMin() {
        Board b = new Board(4, 5);
        thrown.expect(IllegalArgumentException.class);
        b.square(-1, -1);
    }

    @Test
    public void square_invalidBoundsMax() {
        Board b = new Board(4, 5);
        thrown.expect(IllegalArgumentException.class);
        b.square(4, 5);
    }

    @Test
    public void set() {
        Board b = new Board(4, 5);
        b.set(KING, 2, 3);
        b.set(BISHOP, 3, 2);
        b.set(QUEEN, 1, 2);
        b.set(NONE, 1, 2);

        assertWithBoard(b).that(b.get(2, 3)).isEqualTo(KING);
        assertWithBoard(b).that(b.get(3, 2)).isEqualTo(BISHOP);
        assertWithBoard(b).that(b.get(1, 2)).isEqualTo(NONE);
    }
}