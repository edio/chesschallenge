package dkostiuchenko.trycatch.chesschallenge.chess;

import dkostiuchenko.trycatch.chesschallenge.permutation.Permutation;
import dkostiuchenko.trycatch.chesschallenge.processing.IndependenceChecker;
import org.junit.Test;

import static dkostiuchenko.trycatch.chesschallenge.TestUtils.failureMsg;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IndependenceCheckerTest {

    private final IndependenceChecker checker = new IndependenceChecker();

    @Test
    public void emptyBoard() {
        Board b = new Board(8, 8);
        assertTrue(checker.isIndependent(b, new Permutation(b.getSquares()).getIndex()));
    }

    @Test
    public void onePieceOnBoard() {
        Board b = new Board(8, 8);
        b.set(Piece.KING, 5, 4);
        assertTrue(checker.isIndependent(b, new Permutation(b.getSquares()).getIndex()));
    }

    @Test
    public void kingUnderAttack() {
        Board b = new Board(8, 8);
        b.set(Piece.KING, 5, 4);
        b.set(Piece.KNIGHT, 7, 5);
        assertFalse(checker.isIndependent(b, new Permutation(b.getSquares()).getIndex()));
    }

    @Test
    public void independentRooks() {
        final int size = 8;
        Board b = new Board(size, size);
        for (int i = 0; i < size; i++) {
            b.set(Piece.ROOK, i, i);
        }
        assertTrue(checker.isIndependent(b, new Permutation(b.getSquares()).getIndex()));
    }

    @Test
    public void independentFromTask() {
        final int size = 4;

        Board b = new Board(size, size);
        b.set(Piece.ROOK, 1, 3);
        b.set(Piece.ROOK, 0, 2);
        b.set(Piece.KNIGHT, 3, 3);
        b.set(Piece.KNIGHT, 3, 1);
        b.set(Piece.KNIGHT, 3, 0);
        b.set(Piece.KNIGHT, 2, 0);
        assertFalse(failureMsg(b), checker.isIndependent(b, new Permutation(b.getSquares()).getIndex()));

        b = new Board(size, size);
        b.set(Piece.ROOK, 0, 3);
        b.set(Piece.ROOK, 2, 1);
        b.set(Piece.KNIGHT, 1, 0);
        b.set(Piece.KNIGHT, 1, 2);
        b.set(Piece.KNIGHT, 3, 0);
        b.set(Piece.KNIGHT, 3, 2);
        assertTrue(failureMsg(b), checker.isIndependent(b, new Permutation(b.getSquares()).getIndex()));

    }
}