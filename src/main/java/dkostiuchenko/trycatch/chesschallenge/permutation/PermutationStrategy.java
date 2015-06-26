package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

/**
 * Generates permutations from specific set of pieces and board configuration
 */
public interface PermutationStrategy {

    /**
     * Permute some entity and push results to processing
     *
     * @param initialState initial state for the permutation
     * @param collector    processing of generated permutations
     */
    void permute(Piece[] initialState, PermutationCollector collector);

}
