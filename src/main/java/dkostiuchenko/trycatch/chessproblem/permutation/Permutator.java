package dkostiuchenko.trycatch.chessproblem.permutation;

/**
 * Generates permutations from specific set of pieces and board configuration
 */
public interface Permutator<P> {

    interface PermutationCollector<P> {
        /**
         * Collect resulting permutation
         *
         * @param permutation generated permutation. There are no guarantees on immutability. It may be mutated later
         *                    by Permutator
         */
        void collect(P permutation);
    }

    /**
     * Permute some entity and push results to collector
     *
     * @param initialState initial state for the permutation
     * @param collector    collector of generated permutations
     */
    void permute(P initialState, PermutationCollector<P> collector);
}
