package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

/**
 * Collects permutation results from {@link PermutationStrategy}
 */
public interface PermutationCollector {
    /**
     * Called by {@link PermutationStrategy} when another permutation is generated
     *
     * @param permutation generated permutation. There are no guarantees on immutability. It may be mutated later
     *                    by {@link PermutationStrategy}
     */
    void collect(Piece[] permutation);
}
