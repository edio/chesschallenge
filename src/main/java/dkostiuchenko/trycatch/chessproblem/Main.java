package dkostiuchenko.trycatch.chessproblem;

public class Main {
    /**
     * Is called after everything is set up
     *
     * @param permutations
     * @param checker
     * @param collector
     */
    public void execute(PermutationStrategy permutations, IndependenceChecker checker, Collector collector) {
        for (Board b : permutations) {
            collector.collect(b, checker.isIndependent(b));
        }
    }

}
