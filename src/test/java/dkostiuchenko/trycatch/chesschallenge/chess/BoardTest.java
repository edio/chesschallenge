package dkostiuchenko.trycatch.chesschallenge.chess;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static dkostiuchenko.trycatch.chesschallenge.TestUtils.failureMsg;
import static dkostiuchenko.trycatch.chesschallenge.chess.Piece.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoardTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Mock
    Board.BoardVisitor visitor;

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
        b.set(NONE, 1, 2);

        assertEquals(failureMsg(b), KING, b.get(2, 3));
        assertEquals(failureMsg(b), BISHOP, b.get(3, 2));
        assertEquals(failureMsg(b), NONE, b.get(1, 2));
    }

    @Test
    public void countFile() {
        Board b = new Board(4, 5);

        b.set(KING, 2, 3);
        b.set(KING, 2, 4);
        assertEquals(failureMsg(b), 2, b.countFile(2));

        b.set(KING, 0, 0);
        b.set(KING, 0, 1);
        b.set(NONE, 0, 1);
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
        b.set(NONE, 1, 0);
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
        b.countForwardDiagonal(6, 6);
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
        b.countBackDiagonal(6, 6);
    }

    @Test
    public void getSquareCoordinates() {
        Board b = new Board(7, 5);

        int[] s0 = b.getSquareCoordinates(0);
        Assert.assertArrayEquals(failureMsg(b, Arrays.toString(s0)), new int[]{0, 0}, s0);

        int[] s34 = b.getSquareCoordinates(34);
        Assert.assertArrayEquals(failureMsg(b, Arrays.toString(s34)), new int[]{6, 4}, s34);

        int[] s20 = b.getSquareCoordinates(20);
        Assert.assertArrayEquals(failureMsg(b, Arrays.toString(s20)), new int[]{4, 0}, s20);
    }

    @Test
    public void visit() {
        Board b = new Board(4, 5);

        b.set(BISHOP, 0, 0);
        b.set(KNIGHT, 1, 2);
        b.set(QUEEN, 2, 2);
        b.set(QUEEN, 2, 3);
        b.set(ROOK, 3, 3);
        b.set(KING, 2, 4);
        b.set(KING, 3, 4);

        when(visitor.onPiece(Mockito.<Board>any(), Mockito.<Piece>any(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(false);

        boolean result = b.visit(visitor, Piece.KING, Piece.KNIGHT);

        Assert.assertFalse(result);
        verify(visitor).onPiece(b, KING, 2, 4);
        verify(visitor).onPiece(b, KING, 3, 4);
        verify(visitor).onPiece(b, KNIGHT, 1, 2);
        verifyNoMoreInteractions(visitor);
    }

    @Test
    public void visit_true() {
        Board b = new Board(4, 5);

        b.set(BISHOP, 0, 0);
        b.set(KNIGHT, 1, 2);
        b.set(QUEEN, 2, 2);

        when(visitor.onPiece(Mockito.<Board>any(), Mockito.eq(KNIGHT), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(true);
        when(visitor.onPiece(Mockito.<Board>any(), Mockito.eq(BISHOP), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(false);
        when(visitor.onPiece(Mockito.<Board>any(), Mockito.eq(QUEEN), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(false);

        boolean result = b.visit(visitor, Piece.BISHOP, Piece.KNIGHT, Piece.QUEEN);

        Assert.assertTrue(result);
        verify(visitor).onPiece(b, BISHOP, 0, 0);
        verify(visitor).onPiece(b, KNIGHT, 1, 2);
        verifyNoMoreInteractions(visitor);
    }
}