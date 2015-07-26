package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;

public class PermutationTest {

    private Permutation p;

    @After
    public void checkInvariants() {
        for (Piece piece : Piece.values()) {
            if(piece == Piece.NONE) {
                continue;
            }

            final int[] indices = p.indices(piece);
            if (indices != null) {
                for (int index : indices) {
                    Assert.assertEquals("No expected piece on indexed location", piece, p.get(index));
                }
            }
        }
    }

    @Test
    public void index() throws Exception {
        Map<Piece, int[]> expectedIndex = getTestIndex();

        p = new Permutation(getTestElements());
        assertIndex(expectedIndex, p);
    }

    @Test
    public void move_back() throws Exception {
        Piece[] expectedElements = new Piece[]{
                Piece.NONE,
                Piece.NONE,
                Piece.BISHOP,
                Piece.KING,
                Piece.NONE,
                Piece.BISHOP,
                Piece.NONE,
                Piece.BISHOP,
                Piece.QUEEN,
        };

        Map<Piece, int[]> expectedIndex = new HashMap<>();
        expectedIndex.put(Piece.KING, new int[]{3});
        expectedIndex.put(Piece.BISHOP, new int[]{5, 7, 2});
        expectedIndex.put(Piece.QUEEN, new int[]{8});

        p = new Permutation(getTestElements());
        p.move(7, 2);

        assertArrayEquals(expectedElements, p.getElements());
        assertIndex(expectedIndex, p);
    }

    @Test
    public void move_forward() throws Exception {
        Piece[] expectedElements = new Piece[]{
                Piece.NONE,
                Piece.NONE,
                Piece.BISHOP,
                Piece.NONE,
                Piece.BISHOP,
                Piece.BISHOP,
                Piece.NONE,
                Piece.KING,
                Piece.QUEEN,
        };

        Map<Piece, int[]> expectedIndex = new HashMap<>();
        expectedIndex.put(Piece.KING, new int[]{7});
        expectedIndex.put(Piece.BISHOP, new int[]{2, 4, 5});
        expectedIndex.put(Piece.QUEEN, new int[]{8});

        p = new Permutation(getTestElements());
        p.move(0, 7);
        p.move(1, 7);

        assertArrayEquals(expectedElements, p.getElements());
        assertIndex(expectedIndex, p);
    }

    @Test
    public void move_inPlace() throws Exception {
        p = new Permutation(getTestElements());
        p.move(8, 8);
        p.move(0, 0);
        p.move(4, 4);

        assertArrayEquals(getTestElements(), p.getElements());
        assertIndex(getTestIndex(), p);
    }

    @Test
    public void swap() throws Exception {
        Piece[] expectedElements = new Piece[]{
                Piece.NONE,
                Piece.NONE,
                Piece.QUEEN,
                Piece.NONE,
                Piece.BISHOP,
                Piece.NONE,
                Piece.BISHOP,
                Piece.BISHOP,
                Piece.KING,
        };

        Map<Piece, int[]> expectedIndex = new HashMap<>();
        expectedIndex.put(Piece.KING, new int[]{8});
        expectedIndex.put(Piece.BISHOP, new int[]{4, 6, 7});
        expectedIndex.put(Piece.QUEEN, new int[]{2});

        p = new Permutation(getTestElements());
        p.swap(2, 8);

        assertArrayEquals(expectedElements, p.getElements());
        assertIndex(expectedIndex, p);
    }

    private static void assertIndex(Map<Piece, int[]> expectedIndex, Permutation p) {
        for (Map.Entry<Piece, int[]> expectedEntry : expectedIndex.entrySet()) {
            final Piece piece = expectedEntry.getKey();
            final Set<Integer> expectedCoordinates = Arrays.stream(expectedEntry.getValue()).boxed().collect
                    (Collectors.<Integer>toSet());
            final Set<Integer> actualCoordinates = Arrays.stream(p.indices(piece)).boxed().collect
                    (Collectors.<Integer>toSet());
            Assert.assertEquals("Coordinates for " + piece + " are wrong", expectedCoordinates, actualCoordinates);
        }
    }

    private static Map<Piece, int[]> getTestIndex() {
        Map<Piece, int[]> expectedIndex = new HashMap<>();
        expectedIndex.put(Piece.KING, new int[]{2});
        expectedIndex.put(Piece.BISHOP, new int[]{4, 6, 7});
        expectedIndex.put(Piece.QUEEN, new int[]{8});
        return expectedIndex;
    }

    private static Piece[] getTestElements() {
        return new Piece[]{
                Piece.NONE,
                Piece.NONE,
                Piece.KING,
                Piece.NONE,
                Piece.BISHOP,
                Piece.NONE,
                Piece.BISHOP,
                Piece.BISHOP,
                Piece.QUEEN,
        };
    }

}