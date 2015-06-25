package dkostiuchenko.trycatch.chessproblem.permutation;

import dkostiuchenko.trycatch.chessproblem.Piece;
import org.junit.Assert;
import org.junit.Test;

public class HeapMultisetPermutatorTest extends BasePermutatorTest<Piece[]> {

    @Test
    public void numberOfPermutations() throws Exception {
        HeapMultisetPermutator permutator = new HeapMultisetPermutator();
        long numberOfPermutations = countUniquePermutations(permutator, Piece.values());
        Assert.assertEquals(arrangements(6, 6), numberOfPermutations);
    }

    @Test
    public void numberOfMultiSetPermutations() throws Exception {
        HeapMultisetPermutator permutator = new HeapMultisetPermutator();
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
        HeapMultisetPermutator permutator = new HeapMultisetPermutator();
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