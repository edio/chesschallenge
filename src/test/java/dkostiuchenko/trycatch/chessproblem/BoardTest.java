package dkostiuchenko.trycatch.chessproblem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static dkostiuchenko.trycatch.chessproblem.TestUtils.failureMsg;
import static dkostiuchenko.trycatch.chessproblem.piece.Pieces.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BoardTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void getIndex_validBounds() {
        Board b = new Board(4, 5);
        assertEquals(failureMsg(b), 0, b.getIndex(0, 0));
        assertEquals(failureMsg(b), 15, b.getIndex(3, 0));
        assertEquals(failureMsg(b), 4, b.getIndex(0, 4));
        assertEquals(failureMsg(b), 19, b.getIndex(3, 4));
    }

    @Test
    public void getIndex_invalidBoundsMin() {
        Board b = new Board(4, 5);
        thrown.expect(IllegalArgumentException.class);
        b.getIndex(-1, -1);
    }

    @Test
    public void getIndex_invalidBoundsMax() {
        Board b = new Board(4, 5);
        thrown.expect(IllegalArgumentException.class);
        b.getIndex(4, 5);
    }

    @Test
    public void set() {
        Board b = new Board(4, 5);
        b.set(KING, 2, 3);
        b.set(BISHOP, 3, 2);
        b.set(QUEEN, 1, 2);
        b.set(null, 1, 2);

        assertEquals(failureMsg(b), KING, b.get(2, 3));
        assertEquals(failureMsg(b), BISHOP, b.get(3, 2));
        assertNull(failureMsg(b), b.get(1, 2));
    }

    @Test
    public void countFile() {
        Board b = new Board(4, 5);

        b.set(KING, 2, 3);
        b.set(KING, 2, 4);
        assertEquals(failureMsg(b), 2, b.countFile(2));

        b.set(KING, 0, 0);
        b.set(KING, 0, 1);
        b.set(null, 0, 1);
        assertEquals(failureMsg(b), 1, b.countFile(0));
    }

    @Test
    public void countFile_iae() {
        Board b = new Board(4, 5);
        thrown.expect(IllegalArgumentException.class);
        b.countFile(4);
    }

    @Test
    public void countRank() {
        Board b = new Board(4, 5);

        b.set(KING, 2, 4);
        b.set(KING, 3, 4);
        assertEquals(failureMsg(b), 2, b.countRank(4));

        b.set(KING, 0, 0);
        b.set(KING, 1, 0);
        b.set(null, 1, 0);
        assertEquals(failureMsg(b), 1, b.countRank(0));
    }

    @Test
    public void countRank_iae() {
        Board b = new Board(4, 5);
        thrown.expect(IllegalArgumentException.class);
        b.countRank(5);
    }

    @Test
    public void countForwardDiagonal() {
        Board b = new Board(7, 5);

        b.set(BISHOP, 0, 0);
        b.set(BISHOP, 2, 2);
        b.set(BISHOP, 3, 1);

        assertEquals(failureMsg(b), 2, b.countForwardDiagonal(0, 0));
        assertEquals(failureMsg(b), 2, b.countForwardDiagonal(1, 1));
        assertEquals(failureMsg(b), 2, b.countForwardDiagonal(2, 2));
        assertEquals(failureMsg(b), 2, b.countForwardDiagonal(3, 3));
    }

    @Test
    public void countForwardDiagonal_corner() {
        Board b = new Board(7, 5);

        b.set(BISHOP, 0, 4);
        b.set(BISHOP, 6, 0);

        b.set(BISHOP, 0, 0);
        b.set(BISHOP, 6, 4);

        assertEquals(failureMsg(b), 1, b.countForwardDiagonal(0, 4));
        assertEquals(failureMsg(b), 1, b.countForwardDiagonal(6, 0));
    }

    @Test
    public void countForwardDiagonal_iae() {
        Board b = new Board(7, 5);
        thrown.expect(IllegalArgumentException.class);
        b.countForwardDiagonal(6,6);
    }

    @Test
    public void countBackDiagonal() {
        Board b = new Board(4, 5);

        b.set(BISHOP, 0, 0);
        b.set(BISHOP, 2, 2);
        b.set(BISHOP, 3, 1);

        assertEquals(failureMsg(b), 2, b.countBackDiagonal(0, 4));
        assertEquals(failureMsg(b), 2, b.countBackDiagonal(1, 3));
        assertEquals(failureMsg(b), 2, b.countBackDiagonal(2, 2));
        assertEquals(failureMsg(b), 2, b.countBackDiagonal(3, 1));
    }

    @Test
    public void countBackDiagonal_corner() {
        Board b = new Board(4, 5);

        b.set(BISHOP, 0, 0);
        b.set(BISHOP, 0, 4);
        b.set(BISHOP, 3, 0);
        b.set(BISHOP, 3, 4);

        assertEquals(failureMsg(b), 1, b.countBackDiagonal(3, 4));
        assertEquals(failureMsg(b), 1, b.countBackDiagonal(0, 0));
    }

    @Test
    public void countBackDiagonal_iae() {
        Board b = new Board(7, 5);
        thrown.expect(IllegalArgumentException.class);
        b.countBackDiagonal(6,6);
    }

}