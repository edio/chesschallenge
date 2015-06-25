package dkostiuchenko.trycatch.chessproblem.permutation;

import dkostiuchenko.trycatch.chessproblem.Piece;
import org.junit.Assert;
import org.junit.Test;

public class HeapPermutatorTest extends BasePermutatorTest<Piece[]> {

    @Test
    public void numberOfPermutations() throws Exception {
        HeapPermutator permutator = new HeapPermutator();
        long numberOfPermutations = countUniquePermutations(permutator, Piece.values());
        Assert.assertEquals(arrangements(6, 6), numberOfPermutations);
    }

    @Test
    public void numberOfMultiSetPermutations() throws Exception {
        HeapPermutator permutator = new HeapPermutator();
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
        HeapPermutator permutator = new HeapPermutator();
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