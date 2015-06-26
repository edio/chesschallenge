package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

/**
 * Generates permutations by Heap's algorithm
 */
public class HeapPermutationStrategy implements PermutationStrategy {

    public void permute(Piece[] pieces, int n, PermutationCollector collector) {
        if (n == 1) {
            collector.collect(pieces);
        } else {
            for (int i = 0; i < n - 1; i++) {
                permute(pieces, n - 1, collector);
                int j = (n % 2 == 0) ? i : 0;
                swap(pieces, j, n - 1);
            }
            permute(pieces, n - 1, collector);
        }
    }

    private static void swap(Piece[] array, int j, int i) {
        Piece t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    @Override
    public void permute(Piece[] initialState, PermutationCollector collector) {
        if (initialState.length <= 1) {
            collector.collect(initialState);
        } else {
            permute(initialState, initialState.length, collector);
        }
    }
}
