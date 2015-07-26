package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

import java.util.Arrays;

/**
 * Permutation generation algorithm implemented after github.com/stazz/permutations-java.
 * <p/>
 * It is a complete improved and simplified re-implementation, only idea is borrowed.
 */
public class IterativeMultisetPermutationStrategy implements PermutationStrategy {

    @Override
    public void permute(Piece[] initialState, PermutationCollector collector) {
        Arrays.sort(initialState);
        Permutation permutation = new Permutation(initialState);

        // emit first permutation at once
        collector.collect(permutation);

        PermutationIterator iterator = new PermutationIterator(permutation);
        while ((permutation = iterator.next()) != null) {
            collector.collect(permutation);
        }
    }

    private static class PermutationIterator {
        private final Permutation permutation;

        PermutationIterator(Permutation permutation) {
            this.permutation = permutation;
        }

        public Permutation next() {
            // Find largest index j with a[j] < a[j+1]
            int j = permutation.length() - 2;
            while (permutation.compare(j, j + 1) >= 0) {
                j--;
                if (j < 0) {
                    return null;
                }
            }

            // Find index k such that a[k] is smallest integer
            // greater than a[j] to the right of a[j]
            int k = permutation.length() - 1;
            while (permutation.compare(j, k) >= 0) {
                k--;
            }

            permutation.swap(k, j);

            // Put tail end of permutation after jth position in increasing order
            int r = permutation.length() - 1;
            int s = j + 1;

            while (r > s) {
                permutation.swap(r, s);
                r--;
                s++;
            }

            return permutation;
        }

    }
}
