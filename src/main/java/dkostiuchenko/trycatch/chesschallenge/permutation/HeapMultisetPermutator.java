package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

import java.util.Arrays;

/**
 * Generates unique permutations for multisets (i.e. some elements may occur multiple times). Based on Heap's algorithm
 */
public class HeapMultisetPermutator implements Permutator {

    @Override
    public void permute(Piece[] initialState, PermutationCollector collector) {

        int[] counts = new int[Piece.values().length];
        for (Piece p : initialState) {
            counts[p.ordinal()]++;
        }

        permute(initialState, counts, 0, initialState.length, collector);
    }


    private void permute(Piece[] pieces, int[] occurences, int multisetSplitpoint, int n,
                         PermutationCollector collector) {
        if (onlyUniquePiecesLeft(occurences)) {
            if (n == multisetSplitpoint + 1) {
                collector.collect(pieces);
            } else {
                for (int i = multisetSplitpoint; i < n - 1; i++) {
                    permute(pieces, occurences, multisetSplitpoint, n - 1, collector);
                    int j = ((n - multisetSplitpoint) % 2 == 0) ? i : multisetSplitpoint;
                    swap(pieces, j, n - 1);
                }
                permute(pieces, occurences, multisetSplitpoint, n - 1, collector);
            }
        } else {
            if (typesOfPiecesLeft(occurences) == 1) {
                collector.collect(pieces);
            } else {
                for (Piece p : Piece.values()) {
                    if (occurences[p.ordinal()] > 0) {
                        final int firstOccurence = indexOf(pieces, p, multisetSplitpoint);
                        final Piece holdPiece = pieces[firstOccurence];

                        move(pieces, holdPiece, firstOccurence, multisetSplitpoint);
                        permute(pieces, decreaseOccurence(occurences, p), multisetSplitpoint + 1, n, collector);
                        move(pieces, holdPiece, multisetSplitpoint, firstOccurence);
                    }
                }
            }
        }
    }

    private void move(Piece[] list, Piece element, int from, int to) {
        if (from == to) {
            return;
        }
        if (from > to) {
            System.arraycopy(list, to, list, to + 1, from - to);
        } else {
            System.arraycopy(list, from + 1, list, from, to - from);
        }
        list[to] = element;
    }

    private int indexOf(Piece[] list, Piece element, int startIndex) {
        for (int i = startIndex; i < list.length; i++) {
            if (element == list[i]) {
                return i;
            }
        }
        throw new IllegalStateException("Something went wrong");
    }

    private void swap(Piece[] array, int j, int i) {
        Piece t = array[i];
        array[i] = array[j];
        array[j] = t;
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
