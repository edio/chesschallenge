package dkostiuchenko.trycatch.chessproblem;

/**
 * Collects results of checking.
 * Interface may be implemented to report about independent permutation or by, for example, {@link PermutationStrategy},
 * as a feedback to optimize permutations generation (not yet sure it may be used)
 */
public interface Collector {
    void collect(Board board, boolean isIndependent);
}
