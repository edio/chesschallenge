package dkostiuchenko.trycatch.chesschallenge.solver;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

/**
 * Doesn't store any solutions, prints them to stderr right on collecting. After first threshold prints every N'th
 * solution. After second threshold prints only number of solutions found.
 */
public class TransientSmartCollector implements SolutionCollector {

    private final int firstThreshold;
    private final int sparseFactor;
    private final int secondThreshold;
    private int totalSolutions;

    public TransientSmartCollector(int firstThreshold, int sparseFactor, int secondThreshold) {
        this.firstThreshold = firstThreshold;
        this.sparseFactor = sparseFactor;
        this.secondThreshold = secondThreshold;
    }

    @Override
    public void collect(Board board) {
        totalSolutions++;
        if (totalSolutions <= firstThreshold) {
            System.err.println(totalSolutions);
            System.err.println(board);
        } else if (totalSolutions % sparseFactor == 0) {
            System.err.println(totalSolutions);
            if (totalSolutions <= secondThreshold) {
                System.err.println(board);
            }
        }
    }

    public int getTotalSolutions() {
        return totalSolutions;
    }
}
