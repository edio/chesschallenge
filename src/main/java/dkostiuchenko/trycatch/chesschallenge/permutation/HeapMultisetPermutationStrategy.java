package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

import java.util.Arrays;

/**
 * Generates unique permutations for multisets (i.e. some elements may occur multiple times). Based on Heap's algorithm
 */
public class HeapMultisetPermutationStrategy implements PermutationStrategy {

    @Override
    public void permute(Piece[] initialState, PermutationCollector collector) {
        int[] counts = new int[Piece.values().length];
        for (Piece p : initialState) {
            counts[p.ordinal()]++;
        }

        Permutation initialPermutaiton = new Permutation(initialState);

        permute(initialPermutaiton, counts, 0, initialState.length, collector);
    }


    private void permute(Permutation permutation, int[] occurences, int multisetSplitpoint, int n,
                         PermutationCollector collector) {
        if (onlyUniquePiecesLeft(occurences)) {
            if (n == multisetSplitpoint + 1) {
                collector.collect(permutation);
            } else {
                for (int i = multisetSplitpoint; i < n - 1; i++) {
                    permute(permutation, occurences, multisetSplitpoint, n - 1, collector);
                    int j = ((n - multisetSplitpoint) % 2 == 0) ? i : multisetSplitpoint;
                    permutation.swap(j, n - 1);
                }
                permute(permutation, occurences, multisetSplitpoint, n - 1, collector);
            }
        } else {
            if (typesOfPiecesLeft(occurences) == 1) {
                collector.collect(permutation);
            } else {
                for (Piece p : Piece.values()) {
                    if (occurences[p.ordinal()] > 0) {
                        final int firstOccurence = permutation.indexOf(p, multisetSplitpoint);

                        permutation.move(firstOccurence, multisetSplitpoint);
                        permute(permutation, decreaseOccurence(occurences, p), multisetSplitpoint + 1, n, collector);
                        permutation.move(multisetSplitpoint, firstOccurence);
                    }
                }
            }
        }
    }

    private int typesOfPiecesLeft(int[] occurences) {
        int result = 0;
        for (int c : occurences) {
            if (c > 0) {
                result++;
            }
        }
        return result;
    }

    private boolean onlyUniquePiecesLeft(int[] occurences) {
        for (int count : occurences) {
            if (count > 1) {
                return false;
            }
        }
        return true;
    }

    private int[] decreaseOccurence(int[] occurences, Piece symbol) {
        int[] result = Arrays.copyOf(occurences, occurences.length);
        result[symbol.ordinal()]--;
        return result;
    }

}
