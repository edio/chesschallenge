package dkostiuchenko.trycatch.chesschallenge.solver;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

/**
 * Collects solutions from solver
 */
public interface SolutionCollector {
    /**
     * Called by solver when new solution is ready. Implementation must take care of defencive copies, etc.
     *
     * @param board board instance. May be mutated by further solution progress
     */
    void collect(Board board);
}
