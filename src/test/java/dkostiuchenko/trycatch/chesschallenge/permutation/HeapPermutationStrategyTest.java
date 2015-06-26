package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;
import org.junit.Assert;
import org.junit.Test;

public class HeapPermutationStrategyTest extends BasePermutatorTest {

    @Test
    public void numberOfPermutations() throws Exception {
        HeapPermutationStrategy permutator = new HeapPermutationStrategy();
        long numberOfPermutations = countUniquePermutations(permutator, Piece.values());
        Assert.assertEquals(arrangements(6, 6), numberOfPermutations);
    }

    @Test
    public void numberOfMultiSetPermutations() throws Exception {
        HeapPermutationStrategy permutator = new HeapPermutationStrategy();
        long numberOfPermutations = countPermutations(permutator, new Piece[]{
                Piece.BISHOP,
                Piece.BISHOP,
                Piece.KING,
                Piece.KING,
                Piece.NONE,
        });

        Assert.assertEquals(arrangements(5, 5), numberOfPermutations);
    }

    @Test
    public void numberOfMultiSetUniquePermutations() throws Exception {
        HeapPermutationStrategy permutator = new HeapPermutationStrategy();
        long numberOfPermutations = countUniquePermutations(permutator, new Piece[]{
                Piece.BISHOP,
                Piece.BISHOP,
                Piece.KING,
                Piece.KING,
                Piece.NONE,
        });

        Assert.assertEquals(multisetPermutations(5, 2, 2), numberOfPermutations);
    }

}