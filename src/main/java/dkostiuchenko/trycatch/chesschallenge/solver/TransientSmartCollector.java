package dkostiuchenko.trycatch.chesschallenge.solver;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;

/**
 * Doesn't store any solutions, prints them to stdout right on collecting. After first threshold prints every N'th
 * solution. After second threshold prints only number of solutions found to stderr.
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
            System.out.println(totalSolutions);
            System.out.println(board);
        } else if (totalSolutions % sparseFactor == 0) {
            if (totalSolutions <= secondThreshold) {
                System.out.println(totalSolutions);
                System.out.println(board);
            } else {
                System.err.println(totalSolutions);
            }
        }
    }

    public int getTotalSolutions() {
        return totalSolutions;
    }
}
