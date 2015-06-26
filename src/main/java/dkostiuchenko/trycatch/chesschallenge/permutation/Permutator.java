package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

/**
 * Generates permutations from specific set of pieces and board configuration
 */
public interface Permutator {

    interface PermutationCollector {
        /**
         * Collect resulting permutation
         *
         * @param permutation generated permutation. There are no guarantees on immutability. It may be mutated later
         *                    by Permutator
         */
        void collect(Piece[] permutation);
    }

    /**
     * Permute some entity and push results to collector
     *
     * @param initialState initial state for the permutation
     * @param collector    collector of generated permutations
     */
    void permute(Piece[] initialState, PermutationCollector collector);

}
