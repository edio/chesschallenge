package dkostiuchenko.trycatch.chessproblem.permutation;

import dkostiuchenko.trycatch.chessproblem.Piece;

/**
 * Generates permutations by Heap's algorithm
 */
public class HeapPermutator implements Permutator<Piece[]> {

    public void permute(Piece[] pieces, int n, PermutationCollector<Piece[]> collector) {
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
    public void permute(Piece[] initialState, PermutationCollector<Piece[]> collector) {
        if (initialState.length <= 1) {
            collector.collect(initialState);
        } else {
            permute(initialState, initialState.length, collector);
        }
    }
}
