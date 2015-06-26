package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;
import org.junit.Assert;
import org.junit.Test;

public class HeapMultisetPermutationStrategyTest extends BasePermutatorTest {

    @Test
    public void numberOfPermutations() throws Exception {
        HeapMultisetPermutationStrategy permutator = new HeapMultisetPermutationStrategy();
        long numberOfPermutations = countUniquePermutations(permutator, Piece.values());
        Assert.assertEquals(arrangements(6, 6), numberOfPermutations);
    }

    @Test
    public void numberOfMultiSetPermutations() throws Exception {
        HeapMultisetPermutationStrategy permutator = new HeapMultisetPermutationStrategy();
        long numberOfPermutations = countPermutations(permutator, new Piece[]{
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
        HeapMultisetPermutationStrategy permutator = new HeapMultisetPermutationStrategy();
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
        long numberOfPermutations = countUniquePermutations(permutator, initialState);

        Assert.assertEquals(multisetPermutations(initialState.length, 2, 2, 6), numberOfPermutations);
    }

}