package dkostiuchenko.trycatch.chesschallenge.chess;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IndependenceCheckerTest {

    private final IndependenceChecker checker = new IndependenceChecker();

    @Test
    public void emptyBoard() {
        Board b = new Board(8, 8);
        assertTrue(checker.isIndependent(b));
    }

    @Test
    public void onePieceOnBoard() {
        Board b = new Board(8, 8);
        b.set(Piece.KING, 5, 4);
        assertTrue(checker.isIndependent(b));
    }

    @Test
    public void kingUnderAttack() {
        Board b = new Board(8, 8);
        b.set(Piece.KING, 5, 4);
        b.set(Piece.KNIGHT, 7, 5);
        assertFalse(checker.isIndependent(b));
    }

    @Test
    public void independentRooks() {
        final int size = 8;
        Board b = new Board(size, size);
        for (int i = 0; i < size; i++) {
            b.set(Piece.ROOK, i, i);
        }
        assertTrue(checker.isIndependent(b));
    }
}