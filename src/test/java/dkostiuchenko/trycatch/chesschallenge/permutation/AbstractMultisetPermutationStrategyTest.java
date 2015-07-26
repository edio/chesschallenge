package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractMultisetPermutationStrategyTest extends BasePermutationStrategyTest {
    protected abstract PermutationStrategy buildStrategy();

    @Test
    public void numberOfPermutations() throws Exception {
        PermutationStrategy strategy = buildStrategy();
        long numberOfPermutations = countUniquePermutations(strategy, Piece.values());
        Assert.assertEquals(arrangements(6, 6), numberOfPermutations);
    }

    @Test
    public void numberOfMultiSetPermutations() throws Exception {
        PermutationStrategy strategy = buildStrategy();
        long numberOfPermutations = countPermutations(strategy, new Piece[]{
                Piece.BISHOP,
                Piece.BISHOP,
                Piece.KING,
                Piece.KING,
                Piece.NONE,
        });

        Assert.assertEquals(multisetPermutations(5, 2, 2), numberOfPermutations);
    }

    @Test
    public void numberOfMultiSetUniquePermutations() throws Exception {
        PermutationStrategy strategy = buildStrategy();
        final Piece[] initialState = {
                Piece.QUEEN,
                Piece.QUEEN,
                Piece.BISHOP,
                Piece.BISHOP,
                Piece.KING,

                Piece.KNIGHT,
                Piece.NONE,
                Piece.NONE,
                Piece.NONE,
                Piece.NONE,

                Piece.NONE,
                Piece.NONE,
        };
        long numberOfPermutations = countUniquePermutations(strategy, initialState);

        Assert.assertEquals(multisetPermutations(initialState.length, 2, 2, 6), numberOfPermutations);
    }

}
